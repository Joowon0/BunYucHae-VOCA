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
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bun_yuchae_voca;

import com.bun_yuchae_voca.MSTranslatorAPI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Language - an enum of all language codes supported by the Microsoft Translator API
 * 
 * Uses the AJAX Interface V2 - see: http://msdn.microsoft.com/en-us/library/ff512399.aspx
 * 
 * @author Jonathan Griggs <jonathan.griggs at gmail.com>
 */
public enum MSLanguage {
    AUTO_DETECT(""),
    ARABIC("ar"),
    BULGARIAN("bg"),
    CATALAN("ca"),
    CHINESE_SIMPLIFIED("zh-CHS"),
    CHINESE_TRADITIONAL("zh-CHT"),
    CZECH("cs"),
    DANISH("da"),
    DUTCH("nl"),
    ENGLISH("en"),
    ESTONIAN("et"),
    FINNISH("fi"),
    FRENCH("fr"),
    GERMAN("de"),
    GREEK("el"),
    HAITIAN_CREOLE("ht"),
    HEBREW("he"),
    HINDI("hi"),
    HMONG_DAW("mww"),
    HUNGARIAN("hu"),
    INDONESIAN("id"),
    ITALIAN("it"),
    JAPANESE("ja"),
    KOREAN("ko"),
    LATVIAN("lv"),
    LITHUANIAN("lt"),
    MALAY("ms"),
    NORWEGIAN("no"),
    PERSIAN("fa"),
    POLISH("pl"),
    PORTUGUESE("pt"),
    ROMANIAN("ro"),
    RUSSIAN("ru"),
    SLOVAK("sk"),
    SLOVENIAN("sl"),
    SPANISH("es"),
    SWEDISH("sv"),
    THAI("th"),
    TURKISH("tr"),
    UKRAINIAN("uk"),
    URDU("ur"),
    VIETNAMESE("vi");
	
    /**
     * Microsoft's String representation of this language.
     */
    private final String language;
        
    /**
     * Internal Localized Name Cache
     */
    private Map<MSLanguage,String> localizedCache = new ConcurrentHashMap<MSLanguage,String>();
	
	/**
	 * Enum constructor.
	 * @param pLanguage The language identifier.
	 */
	private MSLanguage(final String pLanguage) {
		language = pLanguage;
	}
	
	public static MSLanguage fromString(final String pLanguage) {
		for (MSLanguage l : values()) {
			if (l.toString().equals(pLanguage)) {
				return l;
			}
		}
		return null;
	}
	
	/**
	 * Returns the String representation of this language.
	 * @return The String representation of this language.
	 */
	@Override
	public String toString() {
		return language;
	}
        
        public static void setKey(String pKey) {
            LanguageService.setKey(pKey);
        }
        
        public static void setClientId(String pId) {
            LanguageService.setClientId(pId);
        }
        public static void setClientSecret(String pSecret) {
            LanguageService.setClientSecret(pSecret);
        }
        
		/**
		 * getName()
		 * 
		 * Returns the name for this language in the tongue of the specified locale
		 * 
		 * If the name is not cached, then it retrieves the name of ALL languages in this locale.
		 * This is not bad behavior for 2 reasons:
		 * 
		 *      1) We can make a reasonable assumption that the client will request the
		 *         name of another language in the same locale 
		 *      2) The GetLanguageNames service call expects an array and therefore we can 
		 *         retrieve ALL names in the same, single call anyway.
		 * 
		 * @return The String representation of this language's localized Name.
		 */
        public String getName(MSLanguage locale) throws Exception {
            String localizedName = null;
            if(this.localizedCache.containsKey(locale)) {
                localizedName = this.localizedCache.get(locale);
            } else {
                if(this==MSLanguage.AUTO_DETECT||locale==MSLanguage.AUTO_DETECT) {
                    localizedName = "Auto Detect";
                } else {
                    //If not in the cache, pre-load all the Language names for this locale
                    String[] names = LanguageService.execute(MSLanguage.values(), locale);
                    int i = 0;
                    for(MSLanguage lang : MSLanguage.values()) {
                        if(lang!=MSLanguage.AUTO_DETECT) {   
                            lang.localizedCache.put(locale,names[i]);
                            i++;
                        }
                    }
                    localizedName = this.localizedCache.get(locale);
                }
            }
            return localizedName;
        }
        
     public static List<String> getLanguageCodesForTranslation() throws Exception {
            String[] codes = GetLanguagesForTranslateService.execute();
            return Arrays.asList(codes);
        }
        
     /**
     * values(Language locale)
     * 
	 * Returns a map of all languages, keyed and sorted by 
     * the localized name in the tongue of the specified locale
     * 
     * It returns a map, sorted alphanumerically by the keys()
     * 
     * Key: The localized language name
     * Value: The Language instance
     *
     * @param locale The Language we should localize the Language names with
	 * @return A Map of all supported languages stored by their localized name.
	 */
        public static Map<String,MSLanguage> values(MSLanguage locale) throws Exception {
            Map<String,MSLanguage>localizedMap = new TreeMap<String,MSLanguage>(); 
            for(MSLanguage lang : MSLanguage.values()) {
                if(lang==MSLanguage.AUTO_DETECT)
                    localizedMap.put(MSLanguage.AUTO_DETECT.name(), lang);
                else
                    localizedMap.put(lang.getName(locale), lang);
            }
            return localizedMap;
        }
        
        // Flushes the localized name cache for this language
        private void flushCache() {
            this.localizedCache.clear();
        }
        
        // Flushes the localized name cache for all languages
        public static void flushNameCache() {
            for(MSLanguage lang : MSLanguage.values())
                lang.flushCache();
        }
        
      private final static class LanguageService extends MSTranslatorAPI {
            private static final String SERVICE_URL = "http://api.microsofttranslator.com/V2/Ajax.svc/GetLanguageNames?";
            
        /**
         * Detects the language of a supplied String.
         * 
         * @param text The String to detect the language of.
         * @return A DetectResult object containing the language, confidence and reliability.
         * @throws Exception on error.
         */
        public static String[] execute(final MSLanguage[] targets, final MSLanguage locale) throws Exception {
                //Run the basic service validations first
                validateServiceState(); 
                String[] localizedNames = new String[0];
                if(locale==MSLanguage.AUTO_DETECT) {
                    return localizedNames;
                }
                
                final String targetString = buildStringArrayParam(MSLanguage.values());
                
                final URL url = new URL(SERVICE_URL 
                        +(apiKey != null ? PARAM_APP_ID + URLEncoder.encode(apiKey,ENCODING) : "") 
                        +PARAM_LOCALE+URLEncoder.encode(locale.toString(), ENCODING)
                        +PARAM_LANGUAGE_CODES + URLEncoder.encode(targetString, ENCODING));
                localizedNames = retrieveStringArr(url);
                return localizedNames;
        }

    }
        
    private final static class GetLanguagesForTranslateService extends MSTranslatorAPI {
            private static final String SERVICE_URL = "http://api.microsofttranslator.com/V2/Ajax.svc/GetLanguagesForTranslate?";
            
        /**
         * Detects the language of a supplied String.
         * 
         * @param text The String to detect the language of.
         * @return A DetectResult object containing the language, confidence and reliability.
         * @throws Exception on error.
         */
        public static String[] execute() throws Exception {
                //Run the basic service validations first
                validateServiceState(); 
                String[] codes = new String[0];
                
                final URL url = new URL(SERVICE_URL +(apiKey != null ? PARAM_APP_ID + URLEncoder.encode(apiKey,ENCODING) : ""));
                codes = retrieveStringArr(url);
                return codes;
        }

    }
}
