package com.luv2code.Online.Food.Ordering.controller;

import com.luv2code.Online.Food.Ordering.model.Restaurant;
import com.luv2code.Online.Food.Ordering.model.User;
import com.luv2code.Online.Food.Ordering.request.CreateRestaurantRequest;
import com.luv2code.Online.Food.Ordering.response.MessageResponse;
import com.luv2code.Online.Food.Ordering.service.RestaurantService;
import com.luv2code.Online.Food.Ordering.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurants")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminRestaurantController {

    RestaurantService restaurantService;

    UserService userService;

    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(
            @RequestBody CreateRestaurantRequest request,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        Restaurant restaurant = restaurantService.createRestaurant(request, user);

        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> updateRestaurant(
            @RequestBody CreateRestaurantRequest request,
            @PathVariable Long restaurantId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        userService.findUserByJwtToken(jwt);

        Restaurant restaurant = restaurantService.updateRestaurant(restaurantId, request);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }


    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<MessageResponse> deleteRestaurant(
            //@RequestBody CreateRestaurantRequest request,
            @PathVariable Long restaurantId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        userService.findUserByJwtToken(jwt);

        restaurantService.deleteRestaurant(restaurantId);

        MessageResponse response = new MessageResponse();
        response.setMessage("Restaurant deleted successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("/{restaurantId}/status")
    public ResponseEntity<Restaurant> updateRestaurantStatus(
            @PathVariable Long restaurantId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        userService.findUserByJwtToken(jwt);

        Restaurant restaurant = restaurantService.updateRestaurantStatus(restaurantId);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }


    @GetMapping("/user")
    public ResponseEntity<Restaurant> findRestaurantByUserId(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        Restaurant restaurant = restaurantService.findRestaurantById(user.getId());

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }


}
