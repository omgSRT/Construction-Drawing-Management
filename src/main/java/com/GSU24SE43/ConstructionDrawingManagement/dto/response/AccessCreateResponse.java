package com.GSU24SE43.ConstructionDrawingManagement.dto.response;


import com.GSU24SE43.ConstructionDrawingManagement.entity.Permission;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Staff;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Version;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Date;
import java.util.UUID;

public class AccessCreateResponse  {
    private UUID id;
    private Date dateTime ;
    private String URLLong;
    private Staff staff;
    private Permission permission;
    private Version version;
}
