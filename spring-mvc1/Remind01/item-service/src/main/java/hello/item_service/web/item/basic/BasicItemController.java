package hello.item_service.web.item.basic;

import hello.item_service.domain.item.Item;
import hello.item_service.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
//final이 붙은 멤버 변수를 찾아서 자동으로 생성자를 만들어준다.
//의존관계 주입 -> Singleton Pattern 다시 공부해야함 -> SpringBasic
public class BasicItemController {

    private final ItemRepository itemRepository;

    @PostConstruct
    //해당 Controller Bean의 의존관계가 전부 주입된 후에 초기화 용도로 호출
    public void init() {
        itemRepository.save(new Item("testA", 10000, 10));
        itemRepository.save(new Item("testB", 20000, 30));
    }


    @GetMapping
    public String Item(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    //@PathVariable 사용 ...
    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {

        return "basic/addForm";
    }

    //URI 설계 -> RequestMappingHandlerMapping에서 RequestMappingInfo에 [URL + HttpMethod.POST] -> HashMap -> key값으로 접근
    // @PostMapping("/add")
    public String addItemV1(@RequestParam("itemName") String itemName,
                            @RequestParam int price,
                            @RequestParam Integer quantity,
                            Model model) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);

        return "basic/item";
    }


    //동일한 RequestMappingInfo가 존재하면  Ambiguous mapping. Cannot map 'basicItemController' method
    //    //There is already 'basicItemController' bean method


    //@PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item, Model model) {
        //@ModelAttribute("item")의 속성값은 addAttribute("item",item)처럼 동작한다.
        //Model의 Key 값으로 동작함 -> view Rendering 과정에서 key value로 사용됨
        //해당 Key값을 지정하지 않으면 해당 객체의 Class name의 첫 글자를 소문자로 바꿔서 표현한다.

        itemRepository.save(item);
/*        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);
        이 작동한다.
        */

        return "basic/item";
    }

    //@PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item) {
        itemRepository.save(item);
        return "basic/item";
        //자동으로 model.addAttribute("item",item) 실행
    }

    /**
     * PRG - Post /Redirect / Get Pattern
     */
    //@PostMapping("/add")
    public String addItemV5(Item item) {
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
        //redirect에서 Url에 변수를 더해서 사용하는 것은 url인코딩이 안되기 때문에 위험
        // -> RedirectAttributes를 사용하자
    }

    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        //리디렉트 url에 안전하게 값 전달이 가능하다.
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    //Redirect 하지 않으면 서블릿 내부 호출만 발생하고 클라이언트 브라우저의 Url요청은 변하지 않아서
    //response 메시지를 보내 클라이언트를 리다이렉트 시켜야한다.
    @PostMapping("{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/item/{itemId}";
    }


}
