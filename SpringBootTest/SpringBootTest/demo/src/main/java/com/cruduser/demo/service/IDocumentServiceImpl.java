/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruduser.demo.service;

import com.cruduser.demo.dto.response.DocumentResponse;
import com.cruduser.demo.entity.Document;
import com.cruduser.demo.repository.DocumentRepository;
import com.cruduser.demo.repository.OperationRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor //injection dependencies for constructor
//@AllArgsConstructor
@Slf4j
public class IDocumentServiceImpl implements IDocumentService {
    
    @Autowired
    private DocumentRepository documentRepository;
   
    
    @Autowired
    private final Mapper dozerMapper;
    
    
    @Override
    public Optional<Document> findByUser_Id(Long userId) {
       // Optional<Document> response = documentRepository.findByUser_Id(userId);
                //.orElseThrow(() -> new RuntimeException("Drone with id " + userId + " not found"));

        
        return documentRepository.findByUser_Id(userId);
    }

    @Override
    public DocumentResponse findById(Long docId) {
        Document doc = documentRepository.findById(docId)
                .orElseThrow(() -> new RuntimeException("Document with id " + docId + " not found"));
        
         DocumentResponse documentResponse = dozerMapper.map(doc, DocumentResponse.class);
        
        log.info("IDocumentServiceImpl:findById execution ended.");
        return documentResponse;
    }
    
    @Override
    public Document deleteById(Long id){
           Document doc = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document with id " + id + " not found"));
        
           documentRepository.deleteById(id);
           
           return doc;
           
    }
    
}
