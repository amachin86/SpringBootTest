/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruduser.demo.service;

import com.cruduser.demo.dto.request.DocumentRequest;
import com.cruduser.demo.dto.response.DocumentResponse;
import com.cruduser.demo.entity.Document;
import com.cruduser.demo.entity.Operation;
import com.cruduser.demo.entity.User;
import com.cruduser.demo.repository.DocumentRepository;
import com.cruduser.demo.repository.OperationRepository;
import java.util.Date;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

@Service
public class DocumentService {
 
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
 
    @Autowired
    private DocumentRepository documentRepository;
    
     @Autowired
    private OperationRepository operationRepositoty;
    
    @Autowired
    private Mapper dozerMapper;
    
    @Autowired
    IUserService userService;
   
 
    public String saveDocument(Long userId, DocumentRequest document, String password) {
        // Generar un hash de la contraseña
        String hashedPassword = passwordEncoder.encode(password);
 
        // Almacenar el documento cifrado en la base de datos
        document.setPasswordHash(hashedPassword);
        document.setContent(cifrarArchivo(document.getContent(), password));
        
        Document saveDoc = dozerMapper.map(document, Document.class);
        documentRepository.save(saveDoc);
        
         User user = userService.getUserById(userId);
        
        Operation save = Operation.builder()
                .date(new Date())
                .document(saveDoc)
                .type("upload")
                .user(user)
                .build();
        
        operationRepositoty.save(save);
        
        return hashedPassword;
    }
 
    public DocumentResponse getDocument(Long userId, Long id, String password) {
        // Recuperar el documento de la base de datos
        Document document = documentRepository.findById(id).orElseThrow();
        
        User user = userService.getUserById(userId);
 
        
        // Validar la contraseña proporcionada por el usuario
        if (!passwordEncoder.matches(password, document.getPasswordHash())) {
            throw new RuntimeException("Contraseña incorrecta");
        }
 
        // Descifrar el documento
        document.setContent(descifrarArchivo(document.getContent(), password));
        
        DocumentResponse documentResponse = dozerMapper.map(document, DocumentResponse.class);
        
        Operation save = Operation.builder()
                .date(new Date())
                .document(document)
                .type("download")
                .user(user)
                .build();
        
        operationRepositoty.save(save);

        return documentResponse;
    }
 
    // Método para cifrar un archivo
    private byte[] cifrarArchivo(byte[] file, String password) {
       try {
        // Crear un objeto KeySpec a partir de la contraseña
        SecretKeySpec keySpec = new SecretKeySpec(password.getBytes(), "AES");
 
        // Crear un objeto Cipher para el cifrado
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
 
        // Cifrar el archivo
        return cipher.doFinal(file);
    } catch (Exception e) {
        throw new RuntimeException("Error al cifrar el archivo", e);
    }
    }
 
    // Método para descifrar un archivo
    private byte[] descifrarArchivo(byte[] file, String password) {
        try {
        // Crear un objeto KeySpec a partir de la contraseña
        SecretKeySpec keySpec = new SecretKeySpec(password.getBytes(), "AES");
 
        // Crear un objeto Cipher para el descifrado
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
 
        // Descifrar el archivo
        return cipher.doFinal(file);
    } catch (Exception e) {
        throw new RuntimeException("Error al descifrar el archivo", e);
    }
    }
}
