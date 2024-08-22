package com.luv2code.Online.Food.Ordering.service.impl;

import com.luv2code.Online.Food.Ordering.config.JwtProvider;
import com.luv2code.Online.Food.Ordering.model.USER_ROLE;
import com.luv2code.Online.Food.Ordering.model.User;
import com.luv2code.Online.Food.Ordering.repository.UserRepository;
import com.luv2code.Online.Food.Ordering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public User findUserByJwtToken(String token) throws Exception {

        String email = jwtProvider.getEmailFromJwtToken(token);

        User user = findUserByEmail(email);

        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);

        if(user == null){
            throw new Exception("User not found");
        }

        return user;
    }
}
