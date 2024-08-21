package com.luv2code.Online.Food.Ordering.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luv2code.Online.Food.Ordering.dto.RestaurantDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User customer;

    @JsonIgnore
    @ManyToOne
    private Restaurant restaurant;

    private Long totalAmount;

    private String orderStatus;

    private Date createAt;

    @ManyToOne
    private Address diliveryAddress;

    @OneToMany
    private List<OrderItem> items;

    //private Payment payment;

    private int totalItem;

    private int totalPrice;

}
