/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruduser.demo.service;

import com.cruduser.demo.entity.User;
import org.springframework.stereotype.Service;

/**
 *
 * @author amach
 */

public interface IUserService {
    User getUserById(Long user_id);
    
}
