package com.GSU24SE43.ConstructionDrawingManagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private int priority;
    private Date createDate;
    private Date beginDate;
    private Date endDate;

    //tạo mối quan hệ tự tham chiếu của task từ parentTaskId
    @ManyToOne
    @JoinColumn(name = "parentTaskId")
    @JsonIgnoreProperties(value = {"tasks"}, allowSetters = true)
    private Task parentTask;

    @OneToMany(mappedBy = "parentTask", cascade = CascadeType.ALL, orphanRemoval = true )
    @JsonIgnoreProperties(value = {"parentTask"}, allowSetters = true)
    private List<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "departmentId")
    @JsonIgnoreProperties(value = {"taskList"}, allowSetters = true)
    Department department;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"task"}, allowSetters = true)
    List<Drawing> drawingList;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"task"}, allowSetters = true)
    List<Comment> commentList;

    @ManyToOne
    @JoinColumn(name = "projectId")
    @JsonIgnoreProperties(value = {"tasks"}, allowSetters = true)
    Project project;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"task"}, allowSetters = true)
    List<DetailTask> detailTasks;

    @ManyToOne
    @JoinColumn(name = "accountId")
    @JsonIgnoreProperties(value = {"tasks"}, allowSetters = true)
    Account account;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"task"}, allowSetters = true)
    List<Notification> notifications;
}
