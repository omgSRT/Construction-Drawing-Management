package com.GSU24SE43.ConstructionDrawingManagement.enums;

import lombok.Getter;

@Getter
public enum Permission {
    COPY(""),
    VIEW("you can view project"),
    UPDATE("you can update project"),
    CREATE("you can create project"),

    ;

    private String message;
    private Permission(String message){this.message = message;}
}
