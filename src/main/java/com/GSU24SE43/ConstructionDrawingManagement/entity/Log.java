package com.GSU24SE43.ConstructionDrawingManagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;
import java.util.UUID;
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Date dateTime ;
    private String URLLong;


    @ManyToOne
    @JoinColumn(name = "detailTaskId")
    DetailTask detailTask;

    @ManyToOne
    @JoinColumn(name = "versionId")
    private Version version;
}
