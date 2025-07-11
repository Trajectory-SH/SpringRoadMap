package hello.itemservice.domain.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BeanValidationTest {

    @Test
    void beanValidation() {
        //어차피 실무에서 validatorFactory를 이용해서 무언가를 할 때가 없다. 그냥 이해만
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Item item = new Item("   ", 0, 10000);

        //Set -> 중복을 허용하지 않고 순서를 보장하지 않는 자료구조
        Set<ConstraintViolation<Item>> violations = validator.validate(item);

        for (ConstraintViolation<Item> violation : violations) {
            System.out.println("violation = " + violation);
            System.out.println("violation.getMessage() = " + violation.getMessage());
        }


    }


}