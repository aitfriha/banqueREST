package com.example.demo.exceptions;

import java.util.HashMap;



public class ValidationConstants {
	private static HashMap<String, String> backendTranslation = new HashMap<>();
    // put the validators constants here
    public final static String AUTH_WRONG_CREDENTIALS = "User or Password Is Incorrect";
    //Customer
    public final static String USER_NAME_NOT_BLANK = "user name can't be balank";
    public final static String USER_EMAIL_NOT_BLANK = "user email can't be balank";
    public final static String USER_ID_NOT_BLANK= "user id can't be balank";
    public final static String SOURCE_BANK_ACCOUNT= "source bank account can't be balank";
    public final static String RECIPIENT_BANK_ACCOUNT= "recipient bank account can't be balank";
    public final static String AMOUNT_MUST_BE_MORE_THAN_ZERO= "Amount must be more than 0";
    public final static String AMOUNT_IS_MONDATARY= "Amount is mandatory";

    /**
     *  get key & value for translation for the standard error messages
     * @param entityType entityType
     * @param entityName entityName
     * @return HashMap<String, String>
     */
    private static HashMap<String, String> getKeyValueTranslation(EntityType entityType, String entityName) {
        HashMap<String, String> hashMap = new HashMap<>();
       /* hashMap.put(MainException.getMessageTemplate(entityType, ExceptionType.ADDED), entityName + ADDED_SENTENCE);
        hashMap.put(MainException.getMessageTemplate(entityType, ExceptionType.UPDATED), entityName + UPDATED_SENTENCE);
        hashMap.put(MainException.getMessageTemplate(entityType, ExceptionType.DELETED), entityName + DELETED_SENTENCE);
        hashMap.put(MainException.getMessageTemplate(entityType, ExceptionType.ENTITY_NOT_FOUND), entityName + ENTITY_NOT_FOUND_SENTENCE);
        hashMap.put(MainException.getMessageTemplate(entityType, ExceptionType.DUPLICATE_ENTITY), entityName + DUPLICATE_ENTITY_SENTENCE);
        if(entityType == EntityType.Customer) {
            hashMap.put(MainException.getMessageTemplate(entityType, ExceptionType.ENTITY_NOT_FOUND),""+ DEPARTMENT_CODE_NOT_BLANK + "");
        }
         */
        return hashMap;
    }
    /**
     * Concat one word of the model attribute and "cannot be blank"
     *
     * @param attribute attribute of a model
     * @return String
     */
    private static String getSentence1NotBlankValue(String attribute) {
        return attribute.split("\\.")[1] + " cannot be blank";
    }
    /**
     * Concat the model attribute and "cannot be blank"
     *
     * @param attribute attribute of a model
     * @return String
     */
    private static String getNotBlankValue(String attribute) {
        return attribute.split("\\.")[1] + " cannot be blank";
    }

    /**
     * Concat the model attribute and "should be more than 0"
     *
     * @param attribute attribute of a model
     * @return String
     */
    private static String getMinValue(String attribute) {
        return attribute.split("\\.")[1] + " should be more than 0";
    }
    /**
     * Concat the model attribute and "cannot be null"
     *
     * @param attribute attribute of a model
     * @return String
     */
    private static String getNotNullValue(String attribute) {
        return attribute.split("\\.")[1] + " cannot be null";
    }

    // put the key values of the messages

    /**
     * Collect all backend validation messages for translation purposes
     *
     * @return HashMap<String, String>
     */
    public static HashMap<String, String> getValidationConstants() {
        // Auth
       backendTranslation.put(AUTH_WRONG_CREDENTIALS, "wrong email or password");
       /*
        backendTranslation.put(DEFAULTSENTENCE_CODE_NOT_BLANK, getNotBlankValue(DEFAULTSENTENCE_CODE_NOT_BLANK));
        backendTranslation.put(DEFAULTSENTENCE_VALUE_NOT_BLANK, getNotBlankValue(DEFAULTSENTENCE_VALUE_NOT_BLANK));

        backendTranslation.put(TRANSLATESENTENCE_COUNTRY_LANGUAGE_CODE_NOT_BLANK, getNotBlankValue(TRANSLATESENTENCE_COUNTRY_LANGUAGE_CODE_NOT_BLANK));
        backendTranslation.put(TRANSLATESENTENCE_DEFAULT_SENTENCE_CODE_NOT_BLANK, getNotBlankValue(TRANSLATESENTENCE_DEFAULT_SENTENCE_CODE_NOT_BLANK));
        backendTranslation.put(TRANSLATESENTENCE_TRANSLATION_NOT_BLANK, getNotBlankValue(TRANSLATESENTENCE_TRANSLATION_NOT_BLANK));
*/

        // commercialProject
        return backendTranslation;
    }

    /**
     * Clear the hash map used (optimization purposes)
     *
     */
    public static void clearTheHashMap(){
        backendTranslation.clear();
    }

}
