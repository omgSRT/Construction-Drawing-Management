package com.GSU24SE43.ConstructionDrawingManagement.entity;

import com.GSU24SE43.ConstructionDrawingManagement.enums.Permission;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
public class DetailTask {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID detailTaskId;

//    @Enumerated(EnumType.STRING)
    Permission permissions;

    @ManyToOne
    @JoinColumn(name = "taskId")
    @JsonIgnoreProperties(value = {"detailTasks"}, allowSetters = true)
    Task task;

    @ManyToOne
    @JoinColumn(name = "staffId")
    @JsonIgnoreProperties(value = {"detailTasks"}, allowSetters = true)
    Staff staff;

    @OneToMany(mappedBy = "detailTask", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"detailTask"}, allowSetters = true)
    List<Log> logs;

    public DetailTask(Permission permissions, Task task, Staff staff) {
        this.permissions = permissions;
        this.task = task;
        this.staff = staff;
    }
}
