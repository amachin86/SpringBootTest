/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruduser.demo.service;

import com.cruduser.demo.dto.response.DocumentResponse;
import com.cruduser.demo.entity.Document;
import com.cruduser.demo.entity.User;
import com.cruduser.demo.repository.DocumentRepository;
import com.cruduser.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor //injection dependencies for constructor
//@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService {
    
     @Autowired
    private UserRepository userRepository;
   
    
    @Autowired
    private final Mapper dozerMapper;
    
    
    @Override
    public User getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User with id " + userId + " not found"));
        
         
        
        log.info("UserServiceImpl:findById execution ended.");
        return user;
    }
    
}
