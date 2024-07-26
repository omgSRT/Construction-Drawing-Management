package com.GSU24SE43.ConstructionDrawingManagement.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FloorDetailUpdateRequest {
    @DecimalMin(value = "0.01", message = "Chiều dài tối thiểu 1 tầng là 0.01 m2")
    double length;
    @DecimalMin(value = "0.01", message = "Chiều rộng tối thiểu là 0.01 m2")
    double width;
    @DecimalMin(value = "2.1", message = "Độ cao tối thiểu phải là 2.1 mét")
    double height;
    @Min(value = 1, message = "Số lượng phòng phải ít nhất là 1")
    int numberOfRooms;
    @DecimalMin(value = "1", message = "Tỉ lệ sử dụng phải ít nhất 1%")
    @DecimalMax(value = "100", message = "Tỉ lệ sử dụng tối đa là 100%")
    double occupancyRate;
}
