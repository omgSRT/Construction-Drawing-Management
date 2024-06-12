package com.GSU24SE43.ConstructionDrawingManagement.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID commentId;
    private String content;
    private Date createDate;

    @ManyToOne
    @JoinColumn(name = "staffId")
    Staff staff;

    @ManyToOne
    @JoinColumn(name = "taskId")
    Task task;
}
