/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruduser.demo.service;

import com.cruduser.demo.entity.Operation;
import com.cruduser.demo.repository.OperationRepository;
import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor //injection dependencies for constructor
@Slf4j
public class OperationServiceImpl implements IOperationService{
     
    @Autowired
    private final OperationRepository operationRepository;
    
    @Override
    public Optional<Operation> findByDateBetween(Date startDate, Date endDate) {
       return operationRepository.findByDateBetween(startDate, endDate);
    }

    @Override
    public Optional<Operation> findByUser_Id(Long userId) {
        return operationRepository.findByUser_Id(userId);
    }

    @Override
    public Optional<Operation> findByCompany_Id(Long companyId) {
        return operationRepository.findByCompany_Id(companyId);
    }
    
}
