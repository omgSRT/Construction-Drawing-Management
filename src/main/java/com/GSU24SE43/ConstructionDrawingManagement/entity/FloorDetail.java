package com.GSU24SE43.ConstructionDrawingManagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
public class FloorDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Min(1L)
    private int floorNumber;
    @DecimalMin(value = "0.01")
    private double floorArea;
    private String purpose;
    @DecimalMin(value = "2.1")
    private double height;
    private int numberOfRooms;
    @DecimalMin(value = "0.01")
    @DecimalMax(value = "10")
    private double occupancyRate;
    private String constructionMaterial;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;
}
