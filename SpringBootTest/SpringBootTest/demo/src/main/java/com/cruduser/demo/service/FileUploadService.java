/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruduser.demo.service;

import java.io.InputStream;

public interface FileUploadService {
  void uploadFile(InputStream inputStream);
}
