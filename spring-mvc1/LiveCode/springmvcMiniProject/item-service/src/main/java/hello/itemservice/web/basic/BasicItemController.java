package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {

        return "basic/addForm";
    }

    //@PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                            @RequestParam int price,
                            @RequestParam Integer quantity,
                            Model model) {
        //해당 api 호출이 발생하면 item 객체를 생성하고 save()를 통해서 저장소에 저장
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);
        return "basic/item";
    }

    //@PostMapping("/add") -> 이정도가 Best Practice라고 생각하면 좋을 것 같다.
    public String addItemV2(@ModelAttribute("item") Item item) {
        /*Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);*/

        itemRepository.save(item);
        //model.addAttribute("item", item); -> 자동 추가하기 때문에 생략이 가능한 부분이다.
        //view template에서 접근하는 key이름 => AttributeName

        return "basic/item";
    }

    //@PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item, Model model) {
        //클래스명을 소문자로 바꿔서 ModelAttribue의 이름으로 자동으로 담기게 된다. Item -> item
        itemRepository.save(item);

        return "basic/item";
    }

    //@PostMapping("/add")
    public String addItemV4(Item item) {
        //String과 같은 단순 타입 => @RequestParam이 자동으로 적용된다.
        //사용자가 직접 만든 Item과 같은 클래스 객체는 @ModelAttribute가 작동한다.
        itemRepository.save(item);

        return "basic/item";
        //서버 내부적으로 View 탬플릿을 이동시킨다.
    }

    //@PostMapping("/add")
    public String addItemV5(Item item) {
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
        //사실 위와 같은 방식으로 redirect를 하면 인코딩이 전혀 되어있지 않기 때문에 위험하다.
        //RedirectAttribute를 사용하면 위에서 언급한 문제를 해결할 수 있다.
    }

    @PostMapping("/add")
    //Redirect를 할 때 parameter를 붙여서 보내보자.
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";

    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        //상품 수정을 완료하면 redirect 방식으로 페이지를 이동시킨다. -> 상태코드 302 (Http request message에 Location 존재)
        return "redirect:/basic/items/{itemId}";
    }


    /**
     * 상품을 등록할때는 왜 논리 view 주소를 return하고
     * 상품을 수정하면 redirect를 사용하는 것일까?
     * [PRG Pattern]의 등장
     */


    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
