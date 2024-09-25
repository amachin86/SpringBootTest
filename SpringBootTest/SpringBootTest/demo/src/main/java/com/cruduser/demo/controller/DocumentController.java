/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruduser.demo.controller;


import com.cruduser.demo.component.DocumentEncryptionService;
import com.cruduser.demo.component.KeyGeneratorUtil;
import com.cruduser.demo.dto.request.DocumentRequest;
import com.cruduser.demo.dto.response.DocumentResponse;
import com.cruduser.demo.entity.Document;
import com.cruduser.demo.entity.Operation;
import com.cruduser.demo.entity.User;
import com.cruduser.demo.service.DocumentService;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cruduser.demo.service.IDocumentService;
import com.cruduser.demo.service.IOperationService;
import com.cruduser.demo.service.IUserService;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;



@RestController
@RequestMapping("/api/documents")
@Slf4j
//@AllArgsConstructor
@RequiredArgsConstructor
public class DocumentController {

    @Autowired
    private IDocumentService documentService;  
    @Autowired
    private IOperationService operationService;
    @Autowired
    private DocumentEncryptionService documentEncryptionService;
    @Autowired
    DocumentService documentServiceEncode;
     @Autowired
    IUserService userService;
    
    
  
    @PostMapping("/upload")
    public ResponseEntity<String> uploadDocument(@RequestParam("userId") Long userId, @RequestParam("document") MultipartFile document, @RequestParam("password") String password) {
        try {           
           
            
            // encrypt document using password
            // store document in database
            // log operation      
            byte[] content = document.getBytes();
            DocumentRequest documentRequest = DocumentRequest.builder()
                    .name(document.getOriginalFilename())
                    .content(content)
                    .passwordHash(password)                    
                     .build();
            documentServiceEncode.saveDocument(userId, documentRequest, password);
            SecretKey secretKey;
            try {
                secretKey = KeyGeneratorUtil.generateSecretKey();
            } catch (Exception ex) {
                throw new RuntimeException("Exception occurred while generating password");
            }
            File file = new File(document.getOriginalFilename());
            try {
                documentEncryptionService.encryptDocument(file, secretKey);
            } catch (Exception ex) {
               throw new RuntimeException("Exception occurred while generating encryptDocument");
            }
            
        } catch (IOException ex) {
            log.error("Exception occurred while get bytes document, Exception message {}", ex.getMessage());
            throw new RuntimeException("Exception occurred while get bytes document");
        }
        return ResponseEntity.ok("Document uploaded successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDocument(@PathVariable Long id, @RequestParam("userId") Long userId) {
        // delete document from database
        // log operation
        Document doc = documentService.deleteById(id);
        
         User user = userService.getUserById(userId);
        
        Operation save = Operation.builder()
                .date(new Date())
                .document(doc)
                .type("delete")
                .user(user)
                .build();
        
        return ResponseEntity.ok("Document deleted successfully");
    }

    @GetMapping("/list")
    public ResponseEntity<Optional<Document>> listDocuments(@RequestParam("userId") Long userId) {
        // return list of documents belonging to user
        return ResponseEntity.ok(documentService.findByUser_Id(userId));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<String> downloadDocument(@RequestParam("userId") Long userId, @PathVariable Long id, @RequestParam("password") String password) {
        // verify password using stored hash
        // return decrypted document content
        DocumentResponse doc = documentServiceEncode.getDocument(userId, id, password);
        
        return ResponseEntity.ok("Document download successfully");
    }

    @GetMapping("/operations")
    public ResponseEntity<Optional<Operation>> consultOperations(@RequestParam("startDate") Date startDate, @RequestParam("endDate") Date endDate) {
        // return list of operations filtered by date range, user, or company
        return ResponseEntity.ok(operationService.findByDateBetween(startDate, endDate));
                //findByDateBetweenAndUser_IdAndCompany_Id(startDate, endDate, userId, companyId));
                //@RequestParam("userId") Long userId, @RequestParam("companyId") Long companyId
    }
    
    @GetMapping("/operations")
    public ResponseEntity<Optional<Operation>> consultOperationsUser(@RequestParam("userId") Long userId) {
        // return list of operations filtered by date range, user, or company
        return ResponseEntity.ok(operationService.findByUser_Id(userId));
                //findByDateBetweenAndUser_IdAndCompany_Id(startDate, endDate, userId, companyId));
                //@RequestParam("userId") Long userId, @RequestParam("companyId") Long companyId
    }
    
    @GetMapping("/operations")
    public ResponseEntity<Optional<Operation>> consultOperationsCompany(@RequestParam("companyId") Long companyId) {
        // return list of operations filtered by date range, user, or company
        return ResponseEntity.ok(operationService.findByCompany_Id(companyId));
                
    }

}
