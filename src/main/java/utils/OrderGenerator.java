package utils;

import model.Order;

import java.util.Arrays;
import java.util.List;

public class OrderGenerator {

    public static Order createOrder() {
        List<String> ingredients = Arrays.asList("61c0c5a71d1f82001bdaaa6d");
        return new Order(ingredients);

    }

    public static Order createOrderWithoutIngredients() {
        return new Order(null);
    }


    public static Order createOrderWithWrongHash() {
        return new Order(Arrays.asList("wrong_hash"));
    }

}
