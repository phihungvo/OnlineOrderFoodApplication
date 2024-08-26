package com.luv2code.Online.Food.Ordering.repository;

import com.luv2code.Online.Food.Ordering.model.Cart;
import com.luv2code.Online.Food.Ordering.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    public Cart findByCustomerId(Long userId);

}
