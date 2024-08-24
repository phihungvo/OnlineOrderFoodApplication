package com.luv2code.Online.Food.Ordering.service.impl;

import com.luv2code.Online.Food.Ordering.model.Category;
import com.luv2code.Online.Food.Ordering.model.Food;
import com.luv2code.Online.Food.Ordering.model.Restaurant;
import com.luv2code.Online.Food.Ordering.repository.FoodRepository;
import com.luv2code.Online.Food.Ordering.request.CreateFoodRequest;
import com.luv2code.Online.Food.Ordering.service.FoodService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FoodServiceImpl implements FoodService {

    FoodRepository foodRepository;

    @Override
    public Food createFood(CreateFoodRequest request, Category category, Restaurant restaurant) {

        Food food = new Food();

        food.setCategory(category);
        food.setRestaurant(restaurant);
        food.setDescription(request.getDescription());
        food.setImages(request.getImages());
        food.setName(request.getName());
        food.setPrice(request.getPrice());
        food.setIngredients(request.getIngredients());
        food.setSeasonal(request.isSeasional());
        food.setVegetarian(request.isVegetarin());

        Food savedFood = foodRepository.save(food);
        restaurant.getFoods().add(savedFood);

        return savedFood;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        
        food.setRestaurant(null);
        
        foodRepository.save(food);
        
    }

    @Override
    public List<Food> getRestaurantsFood(Long restaurantId, boolean isVegetarian, boolean isNonVeg, boolean isSeasonal, String foodCategory) {
       
        List<Food> foods = foodRepository.findByRestaurantId(restaurantId);
       
        if(isVegetarian)
            foods = filterByVegetarian(foods, isVegetarian);
        
        if(isNonVeg)
            foods = filterByNonVeg(foods, isNonVeg);

        if(isSeasonal)
            foods = filterBySeasonal(foods, isSeasonal);

        if(foodCategory != null && !foodCategory.equals(""))
            foods = filterByCategory(foods, foodCategory);
        
        return foods;
    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(
                food -> {
                    if (food.getCategory() != null)
                        return food.getCategory().getName().equals(foodCategory);
                    return false;
                }
        ).collect(Collectors.toList());
    }

    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food -> food.isSeasonal() == isSeasonal).collect(Collectors.toList());
    }

    private List<Food> filterByNonVeg(List<Food> foods, boolean isNonVeg) {
        return foods.stream().filter(food -> !food.isVegetarian()).collect(Collectors.toList());
    }

    private List<Food> filterByVegetarian(List<Food> foods, boolean isVegetarian) {
        return foods.stream().filter(food -> food.isVegetarian() == isVegetarian).collect(Collectors.toList());
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> optionalFood = foodRepository.findById(foodId);

        if(optionalFood.isEmpty())
            throw new Exception("Food not exist.....");

        return optionalFood.get();
    }

    @Override
    public Food updateAvailibilityStatus(Long foodId) throws Exception {

        Food food = findFoodById(foodId);
        food.setAvailable(!food.isAvailable());
        return foodRepository.save(food);
    }
}
