package com.luv2code.Online.Food.Ordering.controller;

import com.luv2code.Online.Food.Ordering.model.Food;
import com.luv2code.Online.Food.Ordering.model.Restaurant;
import com.luv2code.Online.Food.Ordering.model.User;
import com.luv2code.Online.Food.Ordering.request.CreateFoodRequest;
import com.luv2code.Online.Food.Ordering.service.FoodService;
import com.luv2code.Online.Food.Ordering.service.RestaurantService;
import com.luv2code.Online.Food.Ordering.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FoodController {

    FoodService foodService;

    UserService userService;

    RestaurantService restaurantService;

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String keyword,
                                           @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        List<Food> foods = foodService.searchFood(keyword);

        return new ResponseEntity<>(foods, HttpStatus.OK);

    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFood(
            @RequestParam boolean vegetarian,
            @RequestParam boolean seasonal,
            @RequestParam boolean nonveg,
            @RequestParam(required = false) String food_category,
            @PathVariable Long restaurantId,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        List<Food> foods = foodService.getRestaurantsFood(restaurantId, vegetarian, nonveg, seasonal, food_category);

        return new ResponseEntity<>(foods, HttpStatus.OK);

    }

}
