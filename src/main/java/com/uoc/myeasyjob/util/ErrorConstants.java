package com.uoc.myeasyjob.util;

/**
 * Constants of the error handling of the service.
 */
public final class ErrorConstants {

    /**
     * Error Codes.
     */
    public static final String INTERNAL_ERROR_CODE = "MEJ_0000";
    public static final String DELETE_ENTITY_ERROR_CODE = "MEJ_0001";
    public static final String FINDING_ENTITY_ERROR_CODE = "MEJ_0002";
    public static final String UPDATING_ENTITY_ERROR_CODE = "MEJ_0003";
    public static final String PARSING_REQUEST_ERROR_CODE = "MEJ_0004";
    public static final String REGISTER_USER_ERROR_CODE = "MEJ_0005";
    public static final String PROCESS_FILE_ERROR_CODE = "MEJ_0006";

    /**
     * Error Messages.
     */
    public static final String INTERNAL_ERROR_MESSAGE = "Internal error, Please contact the administrator.";
    public static final String DELETE_ENTITY_ERROR_MESSAGE = "Error deleting the entity";
    public static final String FINDING_ENTITY_ERROR_MESSAGE = "Entity not found";
    public static final String UPDATING_ENTITY_ERROR_MESSAGE = "Error saving the entity";
    public static final String PARSING_REQUEST_ERROR_MESSAGE = "Error parsing the object";
    public static final String REGISTER_USER_ERROR_MESSAGE = "User already exists";
    public static final String PROCESS_FILE_ERROR_MESSAGE = "Error processing the file uploaded";

    /**
     * Status code
     */
    public static final Integer NOT_FOUND = 404;
    public static final Integer INTERNAL_ERROR = 500;
    public static final Integer BAD_REQUEST = 400;
}
