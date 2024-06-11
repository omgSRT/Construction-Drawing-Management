package com.GSU24SE43.ConstructionDrawingManagement.service;

import com.GSU24SE43.ConstructionDrawingManagement.mapper.SubfolderMapper;
import com.GSU24SE43.ConstructionDrawingManagement.repository.ProjectRepository;
import com.GSU24SE43.ConstructionDrawingManagement.repository.SubfolderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@Service
public class SubfolderService {
    final SubfolderRepository subfolderRepository;
    final SubfolderMapper subfolderMapper;
    final ProjectRepository projectRepository;


}
