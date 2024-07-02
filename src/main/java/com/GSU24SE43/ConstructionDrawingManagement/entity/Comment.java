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
    UUID commentId;
    String content;
    Date createDate;
    String status;

    @ManyToOne
    @JoinColumn(name = "staffId")
    Staff staff;

    @ManyToOne
    @JoinColumn(name = "taskId")
    Task task;
}
