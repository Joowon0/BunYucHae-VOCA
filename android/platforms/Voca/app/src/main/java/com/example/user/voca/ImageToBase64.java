package com.example.user.voca;

import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
/**
 * Created by rnwhd on 2017-06-21.
 */

public class ImageToBase64 {
        public static String change(File file){
            String encodedfile = null;
            try {
                FileInputStream fileInputStreamReader = new FileInputStream(file);
                byte[] bytes = new byte[(int)file.length()];
                fileInputStreamReader.read(bytes);
                encodedfile = new String(Base64.encode(bytes,12345), "UTF-8");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return encodedfile;
        }

        public static String change(String path){
            File file =  new File(path);
            String encodedfile = null;
            try {
                FileInputStream fileInputStreamReader = new FileInputStream(file);
                byte[] bytes = new byte[(int)file.length()];
                fileInputStreamReader.read(bytes);
                encodedfile = new String(Base64.encode(bytes,12345), "UTF-8");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return encodedfile;
        }
}

