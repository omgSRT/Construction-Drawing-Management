package com.GSU24SE43.ConstructionDrawingManagement.entity;

import jakarta.persistence.*;
import lombok.*;

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
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private String description;
    private String status;
    private Date createDate;
    private Date beginDate;
    private Date endDate;

    //tạo mối quan hệ tự tham chiếu của task từ parentTaskId
    @ManyToOne
    @JoinColumn(name = "parentTaskId")
    private Task parentTask;


    @ManyToOne
    @JoinColumn(name ="departmentId")
    Department department;

    @ManyToOne
    @JoinColumn(name ="drawingId")
    Drawing drawing;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Comment> commentList;

    @ManyToOne
    @JoinColumn(name ="projectId")
    Project project;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    List<DetailTask> detailTasks;


}
