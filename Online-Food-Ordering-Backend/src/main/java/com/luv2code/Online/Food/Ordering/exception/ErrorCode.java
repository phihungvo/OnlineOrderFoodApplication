package com.luv2code.Online.Food.Ordering.exception;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHORIZED(1000, "You don't have permission", HttpStatus.FORBIDDEN),
    USER_NOT_FOUND(1004, "User not found", HttpStatus.NOT_FOUND),
    CART_ITEM_NOT_FOUND(1005, "Cart item not found", HttpStatus.NOT_FOUND),
    CATEGORY_NOT_FOUND(1006, "Category not found", HttpStatus.NOT_FOUND),
    FOOD_NOT_FOUND(1007, "Food not found", HttpStatus.NOT_FOUND),
    INGREDIENT_NOT_FOUND(1008, "Ingredient not found", HttpStatus.NOT_FOUND),
    ORDER_NOT_FOUND(1009, "Order not found", HttpStatus.NOT_FOUND),
    RESTAURANT_NOT_FOUND(1010, "Restaurant not found", HttpStatus.NOT_FOUND),
    UPDATE_ORDER_EXCEPTION(1011, "Please select a valid order status", HttpStatus.BAD_REQUEST),

    ;

    int code;
    String message;
    HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
