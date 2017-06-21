package bun_yuchae_voca;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

public class ImgToBase64 {
	public static String change(File file){		
        String encodedfile = null;                	
        try {
        	FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStreamReader.read(bytes);
            encodedfile = new String(Base64.encodeBase64(bytes), "UTF-8");            
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
            encodedfile = new String(Base64.encodeBase64(bytes), "UTF-8");            
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        }
        return encodedfile;
	}
}
