package com.GSU24SE43.ConstructionDrawingManagement.dto.response;

import com.GSU24SE43.ConstructionDrawingManagement.entity.Contractor;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Department;
import com.GSU24SE43.ConstructionDrawingManagement.entity.FloorDetail;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectResponse {
    UUID id;
    String name;
    String description;
    Date creationDate;
    Date startDate;
    Date endDate;
    String status;
    double plotArea;
    String landPurpose;
    List<Department> departments;
    @JsonIgnoreProperties(value = {"projects"}, allowSetters = true)
    Set<Contractor> contractors;
}
