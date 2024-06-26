package com.GSU24SE43.ConstructionDrawingManagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    boolean isRead;
    String title;
    String url;
    String message;
    Date createDate;

    //che account khi lấy list
    @ManyToOne
    @JsonIgnoreProperties(value = { "notifications" }, allowSetters = true)
    @JoinColumn(name = "accountId")
    Account account;

    @ManyToOne
    @JsonIgnoreProperties(value = {"notifications"}, allowSetters = true)
    @JoinColumn(name = "taskId")
    Task task;

}
