package com.GSU24SE43.ConstructionDrawingManagement.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    INVALID_ERROR_MESSAGE_KEY(10666, "Error Message Does Not Match Any Defined Code", HttpStatus.INTERNAL_SERVER_ERROR),
    UNDEFINED_EXCEPTION(10000, "Undefined Exception", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_PASSWORD(10001, "Password Must Have At Least 8 Characters", HttpStatus.BAD_REQUEST),
    INVALID_INPUT(10002, "Invalid Input Data", HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_MATCH(10003, "New Password And Confirm Password Do Not Match", HttpStatus.BAD_REQUEST),
    LOGIN_FAILED(10004, "Invalid Email or Password", HttpStatus.BAD_REQUEST),
    CREATE_FAILED(10005, "Create Failed", HttpStatus.BAD_REQUEST),
    UPDATE_FAILED(10006, "Update Failed", HttpStatus.BAD_REQUEST),
    DELETE_FAILED(10007, "Delete Failed", HttpStatus.BAD_REQUEST),
    FIND_FAILED(10008, "Cannot Find Item", HttpStatus.NOT_FOUND),
    EMPTY_LIST(10009, "List is Empty", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(10010, "User Not Found", HttpStatus.NOT_FOUND),
    INVALID_PAGE_NUMBER(10011, "Page Number Must Be Greater Than 0", HttpStatus.BAD_REQUEST),
    INVALID_PER_PAGE_NUMBER(10012, "Per Page Number Must Be Greater Than 0", HttpStatus.BAD_REQUEST),
    EXPIRED_TOKEN(10013, "Token Expired", HttpStatus.REQUEST_TIMEOUT),
    INVALID_TOKEN(10014, "Invalid Token", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(10015, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(10016, "You Do Not Have Permission", HttpStatus.UNAUTHORIZED),
    NAME_NOT_BLANK(10017, "Name Cannot Be Blank", HttpStatus.BAD_REQUEST),
    NAME_EXISTED(10018, "Name Existed", HttpStatus.BAD_REQUEST),
    NAME_NOT_FOUND(10019, "Name Not Found", HttpStatus.BAD_REQUEST),
    URL_NOT_BLANK(10019, "URL Cannot Be Blank", HttpStatus.BAD_REQUEST),
    FOLDER_NOT_FOUND(10020, "Folder Not Found", HttpStatus.BAD_REQUEST)
    ;

    private int code;
    private String message;
    private HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode){
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
