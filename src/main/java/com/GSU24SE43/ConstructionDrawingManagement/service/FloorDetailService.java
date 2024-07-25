package com.GSU24SE43.ConstructionDrawingManagement.service;

import com.GSU24SE43.ConstructionDrawingManagement.Utils.PaginationUtils;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.FloorDetailCreateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.FloorDetailUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.FloorDetailResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.FloorDetail;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Project;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Staff;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Task;
import com.GSU24SE43.ConstructionDrawingManagement.enums.CommentStatus;
import com.GSU24SE43.ConstructionDrawingManagement.exception.AppException;
import com.GSU24SE43.ConstructionDrawingManagement.exception.ErrorCode;
import com.GSU24SE43.ConstructionDrawingManagement.mapper.FloorDetailMapper;
import com.GSU24SE43.ConstructionDrawingManagement.repository.FloorDetailRepository;
import com.GSU24SE43.ConstructionDrawingManagement.repository.ProjectRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@Service
public class FloorDetailService {
    final FloorDetailRepository floorDetailRepository;
    final ProjectRepository projectRepository;
    final FloorDetailMapper floorDetailMapper;
    final PaginationUtils paginationUtils = new PaginationUtils();

    @PreAuthorize("hasRole('ADMIN')")
    public FloorDetailResponse createFloorDetail(FloorDetailCreateRequest request){
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND));
        int floorNumber = 1;

        List<FloorDetail> floorDetailList = floorDetailRepository.findByProject(project);
        if(!floorDetailList.isEmpty()){
            int maxFloorNumber = floorDetailList.get(0).getFloorNumber();

            for (FloorDetail floorDetail : floorDetailList) {
                if (floorDetail.getFloorNumber() > maxFloorNumber) {
                    maxFloorNumber = floorDetail.getFloorNumber();
                }
            }

            floorNumber = maxFloorNumber + 1;
        }

        FloorDetail newFloorDetail = floorDetailMapper.toFloorDetail(request);
        newFloorDetail.setProject(project);
        newFloorDetail.setFloorNumber(floorNumber);

        boolean check = checkPreviousFloorHasEnoughSpace(project, newFloorDetail);
        if(!check){
            throw new AppException(ErrorCode.EXCEED_PREVIOUS_FLOOR_AREA);
        }

        return floorDetailMapper.toFloorDetailResponse(floorDetailRepository.save(newFloorDetail));
    }

    private boolean checkPreviousFloorHasEnoughSpace(Project project, FloorDetail floorDetail){
        int floorNumber = floorDetail.getFloorNumber() - 1;
        if(floorNumber == 0){
            return floorDetail.getAvailableSpace() <= project.getPlotArea();
        }
        var previousFloorDetail = checkExistedFloorDetail(project, floorNumber);

        double currentFloorAvailableSpace = floorDetail.getAvailableSpace();
        double previousFloorAvailableSpace = previousFloorDetail.getAvailableSpace();

        return currentFloorAvailableSpace <= previousFloorAvailableSpace;
    }

    private FloorDetail checkExistedFloorDetail(Project project, int floorNumber){
        return floorDetailRepository.findByProjectAndFloorNumber(project, floorNumber)
                .orElseThrow(() -> new AppException(ErrorCode.FLOOR_DETAIL_NOT_FOUND));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<FloorDetailResponse> getAllFloorDetails(int page, int perPage){
        List<FloorDetailResponse> floorDetailResponseList = floorDetailRepository.findAll().stream()
                .map(floorDetailMapper::toFloorDetailResponse).toList();
        floorDetailResponseList = paginationUtils.convertListToPage(page, perPage, floorDetailResponseList);
        return floorDetailResponseList;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT') or hasRole('DESIGNER') or hasRole('COMMANDER')")
    public List<FloorDetailResponse> getAllFloorDetailsByProjectId(int page, int perPage, UUID projectId){
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND));

        List<FloorDetailResponse> floorDetailResponseList = floorDetailRepository.findByProject(project).stream()
                .map(floorDetailMapper::toFloorDetailResponse).toList();
        floorDetailResponseList = paginationUtils.convertListToPage(page, perPage, floorDetailResponseList);
        return floorDetailResponseList;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public FloorDetailResponse getFloorDetailById(UUID id){
        return floorDetailMapper.toFloorDetailResponse(floorDetailRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FLOOR_DETAIL_NOT_FOUND)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public FloorDetailResponse deleteFloorDetailById(UUID id){
        var floorDetail = floorDetailRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FLOOR_DETAIL_NOT_FOUND));
        floorDetailRepository.delete(floorDetail);

        EditLaterFloorNumber(floorDetail);

        return floorDetailMapper.toFloorDetailResponse(floorDetail);
    }

    private void EditLaterFloorNumber(FloorDetail floorDetail){
        List<FloorDetail> floorDetailList = floorDetailRepository.findByProjectAndFloorNumberGreaterThan(floorDetail.getProject(), floorDetail.getFloorNumber());
        if(floorDetailList.isEmpty()){
            return;
        }
        for(FloorDetail floorDetailData : floorDetailList){
            int editedFloorNumber = floorDetailData.getFloorNumber() - 1;
            floorDetailData.setFloorNumber(editedFloorNumber);
            floorDetailRepository.save(floorDetailData);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public FloorDetailResponse updateFloorDetailById(UUID floorDetailId, FloorDetailUpdateRequest request){
        var floorDetail = floorDetailRepository.findById(floorDetailId)
                .orElseThrow(() -> new AppException(ErrorCode.FLOOR_DETAIL_NOT_FOUND));

        floorDetailMapper.updateFloorDetail(floorDetail, request);

        return floorDetailMapper.toFloorDetailResponse(floorDetailRepository.save(floorDetail));
    }
}
