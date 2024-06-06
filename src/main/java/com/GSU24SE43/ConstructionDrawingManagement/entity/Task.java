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
public class Task {
    // con thiếu  nối nhiều nhiều giữa staff và task
    // và nối task vòng lại
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private String description;
    private String status;
    private Date createDate;
    private UUID createBy;
    private Date beginDay;
    @ManyToOne
    @JoinColumn(name = "staffId")
    Staff staff;
}
