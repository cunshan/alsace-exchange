package com.alsace.exchange.user.service.impl;

import com.alsace.exchange.user.domain.User;
import com.alsace.exchange.user.repositories.UserRepository;
import com.alsace.exchange.user.service.UserService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;


    @Override
    public User findOne(User user) {
        Example<User> example = Example.of(user);
        return userRepository.findOne(example).orElse(null);
    }

    @Override
    public User getOneById(Long id) {
        return userRepository.getOne(id);
    }
}
