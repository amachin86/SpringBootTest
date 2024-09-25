/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruduser.demo.dto.response;

import com.cruduser.demo.entity.User;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DocumentResponse {
    private Long id;
    private String name;
    private byte[] content; // encrypted document content
    private String passwordHash; // hashed password using bcrypt    
}
