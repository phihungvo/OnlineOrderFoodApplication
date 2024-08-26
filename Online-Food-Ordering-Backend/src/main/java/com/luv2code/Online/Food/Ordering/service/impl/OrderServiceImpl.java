package com.luv2code.Online.Food.Ordering.service.impl;

import com.luv2code.Online.Food.Ordering.enums.OrderStatus;
import com.luv2code.Online.Food.Ordering.exception.AppException;
import com.luv2code.Online.Food.Ordering.exception.ErrorCode;
import com.luv2code.Online.Food.Ordering.model.*;
import com.luv2code.Online.Food.Ordering.repository.*;
import com.luv2code.Online.Food.Ordering.request.OrderRequest;
import com.luv2code.Online.Food.Ordering.service.CartService;
import com.luv2code.Online.Food.Ordering.service.OrderService;
import com.luv2code.Online.Food.Ordering.service.RestaurantService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;

    OrderItemRepository orderItemRepository;

    AddressRepository addressRepository;

    UserRepository userRepository;

    RestaurantService restaurantService;

    CartService cartService;


    @Override
    public Order createOrder(OrderRequest request, User user) throws Exception {

        Address shipAddress = request.getDeliveryAddress();

        Address savedAddress = addressRepository.save(shipAddress);

        if(!user.getAddresses().contains(savedAddress)) {
            user.getAddresses().add(savedAddress);
            userRepository.save(user);
        }
        Restaurant restaurant = restaurantService.findRestaurantById(request.getRestaurantId());

        Order createdOrder = new Order();
        createdOrder.setCustomer(user);
        createdOrder.setCreateAt(new Date());
        createdOrder.setOrderStatus(OrderStatus.PENDING.name());
        createdOrder.setDiliveryAddress(savedAddress);
        createdOrder.setRestaurant(restaurant);

        Cart cart = cartService.findCartByUserId(user.getId());

        List<OrderItem> orderItems = new ArrayList<>();

        for(CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);
        }

        Long totalPrice = cartService.calculateCartTotals(cart);

        createdOrder.setItems(orderItems);
        createdOrder.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(createdOrder);

        restaurant.getOrders().add(savedOrder);

        return createdOrder;
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {

        Order order = findOrderById(orderId);
        if(orderStatus.equals(
                OrderStatus.OUT_FOR_DELIVERY.name())
                || orderStatus.equals(OrderStatus.DELIVERED.name())
                || orderStatus.equals(OrderStatus.COMPLETED.name())
                ||orderStatus.equals(OrderStatus.PENDING.name())){

            order.setOrderStatus(orderStatus);
            return orderRepository.save(order);
        }
        throw new AppException(ErrorCode.UPDATE_ORDER_EXCEPTION);

    }

    @Override
    public void calcelOrder(Long orderId) throws Exception {
        Order order = findOrderById(orderId);
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<Order> getUsersOrder(Long userId) throws Exception {
        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantsOrder(Long restaurantId, String orderStatus) throws Exception {
        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);

        if(orderStatus != null)
            orders = orders.stream().filter(order -> order.getOrderStatus()
                    .equals(orderStatus)).collect(Collectors.toList());

        return orders;
    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {

        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if(optionalOrder.isEmpty())
            throw new AppException(ErrorCode.ORDER_NOT_FOUND);

        return optionalOrder.get();
    }
}
