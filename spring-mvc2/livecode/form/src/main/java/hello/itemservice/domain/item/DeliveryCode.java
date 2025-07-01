package hello.itemservice.domain.item;

import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * FAST : 빠른 배송
 * NORMAL : 일반 배송
 * SLOW : 느린 배송
 */

@Data
@AllArgsConstructor
public class DeliveryCode {

    private String code;//FAST와 같이 시스템에서 전달하는 값이다.
    private String displayName;//빠른 배송과 같이 고객에게 보여주는 값이다.

}
