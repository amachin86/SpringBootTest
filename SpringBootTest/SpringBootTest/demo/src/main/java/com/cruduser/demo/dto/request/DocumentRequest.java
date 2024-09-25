/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruduser.demo.dto.request;

import com.cruduser.demo.entity.User;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DocumentRequest {    
    private String name;
    private byte[] content; // encrypted document content
    
    @NotNull(message = "Password is required")
    @NotBlank(message = "Password is required")
    private String passwordHash; // hashed password using bcrypt
   
}
