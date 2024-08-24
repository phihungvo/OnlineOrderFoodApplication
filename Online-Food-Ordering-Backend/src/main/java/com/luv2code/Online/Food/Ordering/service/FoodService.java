package com.luv2code.Online.Food.Ordering.service;

import com.luv2code.Online.Food.Ordering.model.Category;
import com.luv2code.Online.Food.Ordering.model.Food;
import com.luv2code.Online.Food.Ordering.model.Restaurant;
import com.luv2code.Online.Food.Ordering.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {

    public Food createFood(CreateFoodRequest request, Category category, Restaurant restaurant);

    void deleteFood(Long foodId) throws Exception;

    public List<Food> getRestaurantsFood(Long restaurantId, boolean isVegetarian,
                                         boolean isNonVeg, boolean isSeasonal, String foodCategory);

    public List<Food> searchFood(String keyword);

    public Food findFoodById(Long foodId) throws Exception;

    public Food updateAvailibilityStatus(Long foodId) throws Exception;


}
