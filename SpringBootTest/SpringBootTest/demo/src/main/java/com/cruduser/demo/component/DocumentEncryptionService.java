/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruduser.demo.component;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.crypto.SecretKey;

@Component
public class DocumentEncryptionService {
  private static final String ALGORITHM = "AES";
  private static final String TRANSFORMATION = "AES";

  public void encryptDocument(File document, SecretKey secretKey) throws Exception {
    Cipher cipher = Cipher.getInstance(TRANSFORMATION);
    cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(secretKey.getEncoded(), ALGORITHM));    
    

    FileInputStream fileInputStream = new FileInputStream(document);
    FileOutputStream fileOutputStream = new FileOutputStream("file/" + document + ".encrypted");

    byte[] buffer = new byte[1024];
    int bytesRead;
    while ((bytesRead = fileInputStream.read(buffer)) != -1) {
      byte[] encryptedBytes = cipher.update(buffer, 0, bytesRead);
      fileOutputStream.write(encryptedBytes);
    }

    fileInputStream.close();
    fileOutputStream.close();
  }

  public void decryptDocument(File encryptedDocument, SecretKey secretKey) throws Exception {
    Cipher cipher = Cipher.getInstance(TRANSFORMATION);
    cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(secretKey.getEncoded(), ALGORITHM));

    FileInputStream fileInputStream = new FileInputStream(encryptedDocument);
    FileOutputStream fileOutputStream = new FileOutputStream("file/" + encryptedDocument + ".decrypted");

    byte[] buffer = new byte[1024];
    int bytesRead;
    while ((bytesRead = fileInputStream.read(buffer)) != -1) {
      byte[] decryptedBytes = cipher.update(buffer, 0, bytesRead);
      fileOutputStream.write(decryptedBytes);
    }

    fileInputStream.close();
    fileOutputStream.close();
  }
}
