package com.GSU24SE43.ConstructionDrawingManagement.entity;

import com.GSU24SE43.ConstructionDrawingManagement.enums.Permission;
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
public class Staff_Folder {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    String staff_folder_id;
    @ManyToOne
    @JoinColumn(name = "staffId")
    Staff staff;
    List<Permission> permissions;
    @ManyToOne
    @JoinColumn(name = "folderId")
    Folder folder;
}
