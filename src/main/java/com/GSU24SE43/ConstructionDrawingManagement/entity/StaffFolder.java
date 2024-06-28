package com.GSU24SE43.ConstructionDrawingManagement.entity;

import com.GSU24SE43.ConstructionDrawingManagement.enums.Permission;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class StaffFolder {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    String staffFolderId;
    @ManyToOne
    @JoinColumn(name = "staffId")
    @JsonIgnoreProperties(value = { "staff_folders" }, allowSetters = true)
    Staff staff;
    List<Permission> permissions;
    @ManyToOne
    @JoinColumn(name = "folderId")
    @JsonIgnoreProperties(value = { "staff_folders" }, allowSetters = true)
    Folder folder;
}
