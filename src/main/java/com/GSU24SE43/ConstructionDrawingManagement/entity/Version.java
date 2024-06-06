package com.GSU24SE43.ConstructionDrawingManagement.entity;

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
public class Version {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String description; ;
    private Date date;
    private Integer number;
    private Date uploadDate;

    @ManyToOne
    @JoinColumn(name = "drawingId")
    Drawing drawing;

    @ManyToOne
    @JoinColumn(name = "folderId")
    Folder folder;

    @OneToMany(mappedBy = "version", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Access> accesses = new ArrayList<>();
}
