package com.luv2code.Online.Food.Ordering.request;

import lombok.Data;

@Data
public class IngredientCategoryRequest {

    private String name;

    private Long restaurantId;

}
