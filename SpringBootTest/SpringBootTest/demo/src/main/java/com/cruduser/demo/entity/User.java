/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruduser.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Changed @Data annotation with the following Lombok annotations
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password; // hashed password using bcrypt
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Document> documents = new HashSet<>();
    
    
     public void addDocument(Document addDocument){
        documents.add(addDocument);        
    }

    public void deleteDocument(Long idDocument){
        Iterator<Document> docIterator = documents.iterator();
        while (docIterator.hasNext()){
            Document doc = docIterator.next();
            if (doc.getId() == idDocument) {
                docIterator.remove();
                break;
            }
        }
    }
}
