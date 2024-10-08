package com.luv2code.Online.Food.Ordering.service.impl;

import com.luv2code.Online.Food.Ordering.config.JwtProvider;
import com.luv2code.Online.Food.Ordering.exception.ErrorCode;
import com.luv2code.Online.Food.Ordering.exception.UserException;
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

        return findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);

        if(user == null){
            throw new UserException(ErrorCode.USER_NOT_FOUND);
        }

        return user;
    }
}
