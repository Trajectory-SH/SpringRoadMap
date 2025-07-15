package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/validation/v1/items")
@RequiredArgsConstructor
public class ValidationItemControllerV1 {

    private final ItemRepository itemRepository;
    private final View error;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v1/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        ModelAndView mv =new ModelAndView();
        return "validation/v1/item";
    }

    private Item getItemRepositoryById(long itemId) {
        return itemRepository.findById(itemId);
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v1/addForm";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes, Model model) {
        /**
         * 일반적으로 어떤 로직을 수행할 때 먼저 검증 로직을 거친 다음에
         * 정상 흐름으로 프로그램의 흐름을 설계하는 것이 바람직하다.
         */


        //검증 오류 결과를 보관 -> 최종적으로 Model을 사용해서 view로 넘겨줘야함
        Map<String, String> errors = new HashMap<>();

        //검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
            errors.put("itemName", "상품 이름은 필수입니다.");
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            errors.put("price", "가격은 1,000 ~ 1,000,000 의 값으로 설정해주세요");
        }

        if (item.getQuantity() == null || item.getQuantity() > 9999) {
            errors.put("quantity", "수량은 최대 9,999 까지 허용됩니다.");
        }

        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                errors.put("globalError", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice);
            }
        }

        //검증 로직에서 실패하면 다시 입력 form으로...!
        if (!errors.isEmpty()) {//-> 2중 부정 errors가 비어있지 않다면 == Error가 존재한다면
            model.addAttribute("errors", errors);//Map<String, Object> model -> Attribute name, 실제 객체
            return "validation/v1/addform";//내부적인 view 랜더링 -> 논리이름의 view 이름 return -> returnValueResolver가 작동
        }


        //성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v1/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v1/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v1/items/{itemId}";
        //Model or RedirectAttribute에 존재하는 itemId 값으로 치환된다.\
        //Redirect(302) 요청시 쿼리 파라미터를 추가하고 싶을 때 redirectAttribute 속성들을 추가하면 된다.
    }
}

