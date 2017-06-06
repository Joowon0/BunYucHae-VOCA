package bun_yuchae_voca;
/*
 * microsoft-translator-java-api
 * 
 * Copyright 2012 Jonathan Griggs <jonathan.griggs at gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific MSLanguage governing permissions and
 * limitations under the License.
 */


import bun_yuchae_voca.KeyManager.CompanyType;
import bun_yuchae_voca.MSLanguage;
import bun_yuchae_voca.MSTranslatorAPI;
import java.net.URL;
import java.net.URLEncoder;
/**
 * MSTranslator
 * 
 * Makes calls to the Microsoft Translator API /MSTranslator service
 * 
 * Uses the AJAX Interface V2 - see: http://msdn.microsoft.com/en-us/library/ff512406.aspx
 * 
 * @author Jonathan Griggs <jonathan.griggs at gmail.com>
 */
public final class MSTranslator extends MSTranslatorAPI {
    
    private static final String SERVICE_URL = "https://api.microsofttranslator.com/v2/http.svc/Translate?";
    private static final String ARRAY_SERVICE_URL = "http://api.microsofttranslator.com/V2/Ajax.svc/MSTranslatorArray?";
    private static final String ARRAY_JSON_OBJECT_PROPERTY = "MSTranslatordText";
    
    //prevent instantiation
    private MSTranslator(){};
    
    /**
     * MSTranslators text from a given MSLanguage to another given MSLanguage using Microsoft Translator.
     * 
     * @param text The String to MSTranslator.
     * @param from The MSLanguage code to MSTranslator from.
     * @param to The MSLanguage code to MSTranslator to.
     * @return The MSTranslatord String.
     * @throws Exception on error.
     */
    private static String execute(final String text, final MSLanguage from, final MSLanguage to) throws Exception {
        //Run the basic service validations first
        validateServiceState(text);               
        final String params = 
                (apiKey != null ? PARAM_APP_ID + apiKey.replaceAll(" ", "%20") : "") 
                + PARAM_TEXT_SINGLE + text.replaceAll(" ", "%20")
                + PARAM_FROM_LANG + URLEncoder.encode(from.toString(),ENCODING) 
                + PARAM_TO_LANG + URLEncoder.encode(to.toString(),ENCODING)
                + PARAM_CONTENT_TYPE + URLEncoder.encode("text/plain",ENCODING);        
        final URL url = new URL(SERVICE_URL + params);        
    	final String response = retrieveString(url);
    	return response;
    }
    
    /**
     * MSTranslators text from a given MSLanguage to another given MSLanguage using Microsoft Translator.
     * 
     * Default the from to AUTO_DETECT
     * 
     * @param text The String to MSTranslator.
     * @param to The MSLanguage code to MSTranslator to.
     * @return The MSTranslatord String.
     * @throws Exception on error.
     */
    private static String execute(final String text, final MSLanguage to) throws Exception {
        return execute(text,MSLanguage.AUTO_DETECT,to);
    }
    
    /**
     * MSTranslators an array of texts from a given MSLanguage to another given MSLanguage using Microsoft Translator's MSTranslatorArray
     * service
     * 
     * Note that the Microsoft Translator expects all source texts to be of the SAME MSLanguage. 
     * 
     * @param texts The Strings Array to MSTranslator.
     * @param from The MSLanguage code to MSTranslator from.
     * @param to The MSLanguage code to MSTranslator to.
     * @return The MSTranslatord Strings Array[].
     * @throws Exception on error.
     */
    public static String[] execute(final String[] texts, final MSLanguage from, final MSLanguage to) throws Exception {
        //Run the basic service validations first
        validateServiceState(texts); 
        final String params = 
                (apiKey != null ? PARAM_APP_ID + URLEncoder.encode(apiKey,ENCODING) : "") 
                + PARAM_FROM_LANG + URLEncoder.encode(from.toString(),ENCODING) 
                + PARAM_TO_LANG + URLEncoder.encode(to.toString(),ENCODING) 
                + PARAM_TEXT_ARRAY + URLEncoder.encode(buildStringArrayParam(texts),ENCODING);
        
        final URL url = new URL(ARRAY_SERVICE_URL + params);
    	final String[] response = retrieveStringArr(url,ARRAY_JSON_OBJECT_PROPERTY);
    	return response;
    }
    
    /**
     * MSTranslators an array of texts from an Automatically detected MSLanguage to another given MSLanguage using Microsoft Translator's MSTranslatorArray
     * service
     * 
     * Note that the Microsoft Translator expects all source texts to be of the SAME MSLanguage. 
     * 
     * This is an overloaded convenience method that passes MSLanguage.AUTO_DETECT as fromLang to
     * execute(texts[],fromLang,toLang)
     * 
     * @param texts The Strings Array to MSTranslator.
     * @param from The MSLanguage code to MSTranslator from.
     * @param to The MSLanguage code to MSTranslator to.
     * @return The MSTranslatord Strings Array[].
     * @throws Exception on error.
     */
    public static String[] execute(final String[] texts, final MSLanguage to) throws Exception {
        return execute(texts,MSLanguage.AUTO_DETECT,to);
    }
    
    private static void validateServiceState(final String[] texts) throws Exception {
        int length = 0;
        for(String text : texts) {
            length+=text.getBytes(ENCODING).length;
        }
        if(length>10240) {
            throw new RuntimeException("TEXT_TOO_LARGE - Microsoft Translator (MSTranslator) can handle up to 10,240 bytes per request");
        }
        validateServiceState();
    }
    
    
    private static void validateServiceState(final String text) throws Exception {
    	final int byteLength = text.getBytes(ENCODING).length;
        if(byteLength>10240) {
            throw new RuntimeException("TEXT_TOO_LARGE - Microsoft Translator (MSTranslator) can handle up to 10,240 bytes per request");
        }
        validateServiceState();
    }
    public static String act(final String text, final MSLanguage from, final MSLanguage to){
    	MSTranslatorAPI.setKey(KeyManager.getInstance().requestKey(CompanyType.MS_BING));
    	String retV = null;
    	try {
			retV = execute(text,from,to);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return retV;
    }
}