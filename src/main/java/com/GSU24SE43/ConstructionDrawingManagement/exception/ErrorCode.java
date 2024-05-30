package com.GSU24SE43.ConstructionDrawingManagement.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INVALID_ERROR_MESSAGE_KEY(10666, "Error Message Does Not Match Any Defined Code"),
    UNDEFINED_EXCEPTION(10000, "Undefined Exception"),
    INVALID_PASSWORD(10001, "Password Must Have At Least 8 Characters"),
    INVALID_INPUT(10002, "Invalid Input Data"),
    PASSWORD_NOT_MATCH(10003, "New Password And Confirm Password Do Not Match"),
    LOGIN_FAILED(10004, "Invalid Email or Password"),
    CREATE_FAILED(10005, "Create Failed"),
    UPDATE_FAILED(10006, "Update Failed"),
    DELETE_FAILED(10007, "Delete Failed"),
    FIND_FAILED(10008, "Find Failed"),
    EMPTY_LIST(10009, "List is Empty"),
    USER_NOT_FOUND(10010, "User Not Found"),
    INVALID_PAGE_NUMBER(10011, "Page Number Must Be Greater Than 0"),
    INVALID_PER_PAGE_NUMBER(10012, "Per Page Number Must Be Greater Than 0"),
    EXPIRED_TOKEN(10013, "Token Expired"),
    INVALID_TOKEN(10014, "Invalid Token"),
    ACCESS_NOT_ALLOW(10015, "You Are Not Allowed to Access"),
    ;

    private final int code;
    private final String message;

    ErrorCode(int code, String message){
        this.code = code;
        this.message = message;
    }
}
