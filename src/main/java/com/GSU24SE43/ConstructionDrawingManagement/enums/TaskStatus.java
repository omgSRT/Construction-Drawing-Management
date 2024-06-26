package com.GSU24SE43.ConstructionDrawingManagement.enums;

import lombok.Getter;

@Getter
public enum TaskStatus {
    NO_RECIPIENT("No one apply this task"),
    PROCESSING("Task is processing"),
    DONE("Task is done"),
    ACTIVE("Task is active"),
    INACTIVE("Task is inactive")
    ;
    private String message;

    TaskStatus(String message) {
       this.message = message;
    }
}
