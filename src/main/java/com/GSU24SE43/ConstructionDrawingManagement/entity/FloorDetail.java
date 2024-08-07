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
    @Min(1)
    private int floorNumber;
    @DecimalMin(value = "0.01", message = "Chiều dài tối thiểu 1 tầng là 0.01 m2")
    private double length;
    @DecimalMin(value = "0.01", message = "Chiều rộng tối thiểu là 0.01 m2")
    private double width;
    @DecimalMin(value = "10", message = "Diện tích tối thiểu 1 tầng là 10 m2")
    //tổng diện tích sàn
    private double floorArea;
    @DecimalMin(value = "2.1", message = "Độ cao tối thiểu phải là 2.1 mét")
    private double height;
    @Min(value = 1, message = "Số lượng phòng phải ít nhất là 1")
    private int numberOfRooms;
    @DecimalMin(value = "1", message = "Tỉ lệ sử dụng phải ít nhất 1%")
    @DecimalMax(value = "100", message = "Tỉ lệ sử dụng tối đa là 100%")
    private double occupancyRate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;

    //Calculate available Space
    public double getAvailableSpace() {
        return floorArea * (occupancyRate / 100);
    }
}
