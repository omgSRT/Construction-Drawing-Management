package com.GSU24SE43.ConstructionDrawingManagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
public class Access {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Date dateTime ;

    @ManyToOne
    @JoinColumn(name = "staffId")
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "permissionId")
    private Permission permission;

    @ManyToOne
    @JoinColumn(name = "versionId")
    private Version version;
}
