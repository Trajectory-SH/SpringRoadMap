package hello.itemservice.web.validation.form;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
//DB에 새로운 Item을 등록할 때 사용하는 Form
public class ItemSaveForm {

    @NotBlank
    private String itemName;

    @NotNull
    @Range(min = 1000,max = 1000000)
    private Integer price;


    @NotNull
    @Max(value = 9999)
    private Integer quantity;
}
