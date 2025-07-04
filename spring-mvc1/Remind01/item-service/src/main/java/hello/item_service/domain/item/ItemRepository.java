package hello.item_service.domain.item;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>();
    private static long sequence = 0L;//메서드 영역에서 공동으로 관리되기 위해서 static으로 선언했다.

    public Item save(Item item) {
        item.setId(++sequence);//prefix 증감 연산자 -> sequence 값을 할당하고 1 증가
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }


    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setQuantity(updateParam.getQuantity());
        findItem.setPrice(updateParam.getPrice());
    }

    public void clearStore() {
        store.clear();
    }




}
