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
    private String versionNumber;
    private Date uploadDate;
    private String status;

    @ManyToOne
    @JoinColumn(name = "drawingId")
    Drawing drawing;

    @OneToMany(mappedBy = "version", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Log> logs;
}
