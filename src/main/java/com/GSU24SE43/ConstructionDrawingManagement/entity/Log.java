package com.GSU24SE43.ConstructionDrawingManagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private Date accessDateTime ;
    private String descriptionLog;


    @ManyToOne
    @JoinColumn(name = "detailTaskId")
    @JsonIgnoreProperties(value = { "logs" }, allowSetters = true)
    DetailTask detailTask;

    @ManyToOne
    @JoinColumn(name = "versionId")
    @JsonIgnoreProperties(value = { "logs" }, allowSetters = true)
    private Version version;
}
