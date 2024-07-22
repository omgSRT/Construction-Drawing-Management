package com.GSU24SE43.ConstructionDrawingManagement.enums;

import lombok.Getter;

@Getter
public enum LandPurpose {
    RESIDENTIAL("Building for residences"),
    COMMERCIAL("Building for shops, restaurants, etc."),
    INDUSTRIAL("Building for plants, warehouses"),
    INSTITUTIONAL("Building for schools, healthcare facilities"),
    MIXED_USE("Building that combines residential, commercial, or industrial spaces"),
    RECREATIONAL("Building for parks, sport facilities"),
    AGRICULTURAL("Building for farms, greenhouses"),
    TRANSPORT("Building for roads, highways, and transportation hub"),
    HISTORICAL("Building for museums, historical sites"),
    OTHER("Building for other purposes");
    ;

    final String description;

    LandPurpose(String description){
        this.description = description;
    }
}
