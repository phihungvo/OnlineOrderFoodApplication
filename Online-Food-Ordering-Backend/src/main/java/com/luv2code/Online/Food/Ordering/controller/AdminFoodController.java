package com.luv2code.Online.Food.Ordering.controller;

import com.luv2code.Online.Food.Ordering.model.Food;
import com.luv2code.Online.Food.Ordering.model.Restaurant;
import com.luv2code.Online.Food.Ordering.model.User;
import com.luv2code.Online.Food.Ordering.request.CreateFoodRequest;
import com.luv2code.Online.Food.Ordering.response.MessageResponse;
import com.luv2code.Online.Food.Ordering.service.FoodService;
import com.luv2code.Online.Food.Ordering.service.RestaurantService;
import com.luv2code.Online.Food.Ordering.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/foods")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminFoodController {

    FoodService foodService;

    UserService userService;

    RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest request,
                                           @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        Restaurant restaurant = restaurantService.findRestaurantById(request.getRestaurantId());

        Food food = foodService.createFood(request, request.getCategory(), restaurant);

        return new ResponseEntity<>(food, HttpStatus.CREATED);

    }


    @DeleteMapping("/{foodId}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long foodId,
                                                      @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        foodService.deleteFood(foodId);

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Food deleted successfully....");

        return new ResponseEntity<>(messageResponse, HttpStatus.OK);

    }


    @PutMapping("/{foodId}")
    public ResponseEntity<MessageResponse> updateFoodAvaibilityStatus(@PathVariable Long foodId,
                                                      @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        Food food = foodService.updateAvailibilityStatus(foodId);

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Food Avaibility Status updated successfully....");

        return new ResponseEntity<>(messageResponse, HttpStatus.OK);

    }

}
