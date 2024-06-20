package com.GSU24SE43.ConstructionDrawingManagement.entity;

import com.GSU24SE43.ConstructionDrawingManagement.enums.Permission;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;
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

    List<Permission> permissions;

    @ManyToOne
    @JoinColumn(name = "taskId")
    Task task;

    @ManyToOne
    @JoinColumn(name = "staffId")
    Staff staff;

    @OneToMany(mappedBy = "detailTask", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Log> logs;
}
