/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruduser.demo.component;

import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.SecureRandom;

public class KeyGeneratorUtil {
  public static SecretKey generateSecretKey() throws Exception {
    KeyGenerator keyGen = KeyGenerator.getInstance("AES");
    SecureRandom random = new SecureRandom();
    keyGen.init(128, random); // 128-bit key
    return keyGen.generateKey();
  }
}
