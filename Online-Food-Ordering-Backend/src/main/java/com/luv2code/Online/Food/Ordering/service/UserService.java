package com.luv2code.Online.Food.Ordering.service;

import com.luv2code.Online.Food.Ordering.model.User;

public interface UserService {

    public User findUserByJwtToken(String token) throws Exception;

    public User findUserByEmail(String email) throws Exception;
}
