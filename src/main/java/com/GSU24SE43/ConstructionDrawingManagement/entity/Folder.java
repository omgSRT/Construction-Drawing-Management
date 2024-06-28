package com.GSU24SE43.ConstructionDrawingManagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
public class Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private Date createDate;

    @JsonIgnore
    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Drawing> drawings;

    @ManyToOne
    @JoinColumn(name = "projectId")
    Project project;

    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = { "folder" }, allowSetters = true)
    List<StaffFolder> staff_folders;
}
