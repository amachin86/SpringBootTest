/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruduser.demo.service;

import com.cruduser.demo.dto.response.DocumentResponse;
import com.cruduser.demo.entity.Document;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 *
 * @author amach
 */

public interface IDocumentService {
     Optional<Document> findByUser_Id(Long userId);
     DocumentResponse findById(Long docId);
     Document deleteById(Long id);
}
