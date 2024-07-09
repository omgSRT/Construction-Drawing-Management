package com.GSU24SE43.ConstructionDrawingManagement.mapper;


import com.GSU24SE43.ConstructionDrawingManagement.dto.request.StaffCreateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.StaffUpdateByStaffRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.StaffUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.StaffCreateResponse2;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.StaffListResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.StaffUpdateByStaffResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.StaffUpdateResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Staff;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface StaffMapper {
    Staff toStaff(StaffCreateRequest request);
    Staff updateRequestToStaff(StaffUpdateRequest request);
    StaffCreateResponse2 toStaffCreateResponse2(Staff staff);
//    @Mapping(source = "department.departmentId", target = "departmentId")
//    StaffUpdateResponse updateStaff(Staff staff);
    @Mapping(source = "department.departmentId", target = "departmentId")
    StaffUpdateResponse toStaffUpdateResponse(Staff staff);
    StaffUpdateByStaffResponse toStaffUpdateByStaffResponse(Staff staff);
    void updateStaff(@MappingTarget Staff staff, StaffUpdateRequest request);
    void updateByStaff(@MappingTarget Staff staff, StaffUpdateByStaffRequest request);

    StaffListResponse toStaffList(Staff staff);
}
