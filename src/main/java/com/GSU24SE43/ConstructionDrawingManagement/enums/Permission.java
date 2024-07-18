package com.GSU24SE43.ConstructionDrawingManagement.enums;

import lombok.Getter;

@Getter
public enum Permission {
    VIEW("you can view project"),
    CREATE_UPDATE_VIEW("you can create, update, view project"),//head
    UPDATE_VIEW("you can create project"),//designer
    ALL_PERMISSION("you can create project"),//admin
    CREATE("you can view project"),
    UPDATE("you can view project"),
    UPLOAD("you can view project"),
    DOWNLOAD("you can view project"),
    ;

    private String message;
    private Permission(String message){this.message = message;}
}
