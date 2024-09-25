/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruduser.demo.service;

import com.cruduser.demo.entity.Operation;
import java.util.Date;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 *
 * @author amach
 */

public interface IOperationService {
    Optional<Operation> findByDateBetween(Date startDate, Date endDate);
    Optional<Operation> findByUser_Id(Long userId);
    Optional<Operation> findByCompany_Id(Long companyId);
    
}
