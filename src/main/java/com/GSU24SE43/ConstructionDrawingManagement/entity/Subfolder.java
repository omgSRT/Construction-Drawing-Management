package com.GSU24SE43.ConstructionDrawingManagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
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
public class Subfolder {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private Date createDate;

    @JsonIgnore
    @OneToMany(mappedBy = "subfolder", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Drawing> drawings = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "projectId")
    Project project;
}
