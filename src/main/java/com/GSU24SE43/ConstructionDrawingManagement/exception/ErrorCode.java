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
    FIND_FAILED(10005, "Cannot Find Item", HttpStatus.NOT_FOUND),
    EMPTY_LIST(10006, "List Doesn't Contain Any Data", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(10007, "User Not Found", HttpStatus.NOT_FOUND),
    INVALID_PAGE_NUMBER(10008, "Page Number Must Be Greater Than 0", HttpStatus.BAD_REQUEST),
    INVALID_PER_PAGE_NUMBER(10009, "Per Page Number Must Be Greater Than 0", HttpStatus.BAD_REQUEST),
    EXPIRED_TOKEN(10010, "Token Expired", HttpStatus.REQUEST_TIMEOUT),
    INVALID_TOKEN(10011, "Invalid Token", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(10012, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(10013, "You Do Not Have Permission", HttpStatus.UNAUTHORIZED),
    NAME_NOT_BLANK(10014, "Name Cannot Be Blank", HttpStatus.BAD_REQUEST),
    NAME_EXISTED(10015, "Name Existed", HttpStatus.BAD_REQUEST),
    NAME_NOT_FOUND(10016, "Name Not Found", HttpStatus.NOT_FOUND),
    URL_NOT_BLANK(10017, "URL Cannot Be Blank", HttpStatus.BAD_REQUEST),
    FOLDER_NOT_FOUND(10018, "Folder Not Found", HttpStatus.NOT_FOUND),
    PROJECT_NOT_FOUND(10019, "Project Not Found", HttpStatus.NOT_FOUND),
    DEPARTMENT_NOT_FOUND(10020, "Department Not Found", HttpStatus.NOT_FOUND),
    ACCOUNT_NOT_FOUND(10021, "Account Not Found", HttpStatus.NOT_FOUND),
    INVALID_CREATED_DATE_EARLIER_THAN_END_DATE(10022, "Created Date Must Be Earlier Than End Date", HttpStatus.BAD_REQUEST),
    INVALID_CREATED_DATE_NOT_IN_FUTURE(10023, "Created Date Must Be Equal or Later Than Current Date", HttpStatus.BAD_REQUEST),
    INVALID_END_DATE_NOT_IN_FUTURE(10024, "End Date Must Be Later Than Current Date", HttpStatus.BAD_REQUEST),
    PERMISSION_NOT_FOUND(10025, "Permission Not Found", HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_EXIST(10026,"Account not exist",  HttpStatus.NOT_FOUND),
    ACCOUNT_ARE_EXISTED(10027,"Account are existed",  HttpStatus.NOT_FOUND),
    UNDEFINE_STATUS_ACCOUNT(10028,"Account status is not define",  HttpStatus.NOT_FOUND);

    private int code;
    private String message;
    private HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode){
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
