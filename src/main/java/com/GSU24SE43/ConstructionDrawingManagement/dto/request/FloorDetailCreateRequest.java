package com.GSU24SE43.ConstructionDrawingManagement.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FloorDetailCreateRequest {
    @Min(1)
    int floorNumber;
    @DecimalMin(value = "25", message = "Diện tích tối thiểu là 25 m2")
    double floorArea;
    @DecimalMin(value = "2.1", message = "Độ cao tối thiểu 1 tầng phải là 2.1 mét")
    double height;
    @Min(value = 1, message = "Số lượng phòng phải ít nhất là 1")
    int numberOfRooms;
    @DecimalMin(value = "1", message = "Tỉ lệ sử dụng phải ít nhất 1%")
    @DecimalMax(value = "100", message = "Tỉ lệ sử dụng tối đa là 100%")
    double occupancyRate;
    UUID projectId;
}
