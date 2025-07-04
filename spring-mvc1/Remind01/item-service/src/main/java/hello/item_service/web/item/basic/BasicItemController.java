package hello.item_service.web.item.basic;

import hello.item_service.domain.item.Item;
import hello.item_service.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/basic/item")
@RequiredArgsConstructor
//final이 붙은 멤버 변수를 찾아서 자동으로 생성자를 만들어준다.
//의존관계 주입 -> Singleton Pattern 다시 공부해야함 -> SpringBasic
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String Item(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }




    @PostConstruct
    //해당 Controller Bean의 의존관계가 전부 주입된 후에 초기화 용도로 호출
    public void init() {
        itemRepository.save(new Item("testA", 10000, 10));
        itemRepository.save(new Item("testB", 20000, 30));
    }
}
