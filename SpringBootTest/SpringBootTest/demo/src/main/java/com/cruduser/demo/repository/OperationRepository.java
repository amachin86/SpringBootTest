/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruduser.demo.repository;


import com.cruduser.demo.entity.Operation;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@EnableTransactionManagement
@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {
    
     //Optional<Operation> findByDateBetweenAndUser_IdAndCompany_Id(Date startDate, Date endDate, Long userId, Long companyId);
     Optional<Operation> findByDateBetween(Date startDate, Date endDate);
     Optional<Operation> findByUser_Id(Long userId);
     Optional<Operation> findByCompany_Id(Long companyId);
}
