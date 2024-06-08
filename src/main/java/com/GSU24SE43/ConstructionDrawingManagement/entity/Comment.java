package com.GSU24SE43.ConstructionDrawingManagement.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID contentId;
    private String content;
    private Date createDate;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Staff> staffList;
    @ManyToOne
    @JoinColumn(name = "taskId")
    Task task;
}
