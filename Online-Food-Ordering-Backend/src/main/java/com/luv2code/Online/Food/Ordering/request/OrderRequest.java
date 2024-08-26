package com.luv2code.Online.Food.Ordering.request;

import com.luv2code.Online.Food.Ordering.model.Address;
import lombok.Data;

@Data
public class OrderRequest {

    private Long restaurantId;

    private Address deliveryAddress;
}
