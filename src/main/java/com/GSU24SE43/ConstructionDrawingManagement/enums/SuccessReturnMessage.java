package com.GSU24SE43.ConstructionDrawingManagement.enums;

import lombok.Getter;

@Getter
public enum SuccessReturnMessage {
    SEARCH_SUCCESS("Search Successfully"),
    CREATE_SUCCESS("Create Successfully"),
    UPDATE_SUCCESS("Update Successfully"),
    CHANGE_SUCCESS("Change Successfully"),
    DELETE_SUCCESS("Delete Successfully"),
    CONVERT_SUCCESS("Convert Successfully"),
    UPLOAD_SUCCESS("Upload Successfully"),
    DOWNLOAD_SUCCESS("Download Successfully"),
    SEND_SUCCESS("Send Successfully")
    ;

    private String message;
    SuccessReturnMessage(String message){
        this.message = message;
    }
}
