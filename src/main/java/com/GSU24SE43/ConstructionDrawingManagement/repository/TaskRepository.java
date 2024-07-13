package com.GSU24SE43.ConstructionDrawingManagement.repository;

import com.GSU24SE43.ConstructionDrawingManagement.entity.Account;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Department;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findByParentTaskIsNull();
    List<Task> findByAccountAndParentTaskIsNull(Account account);
    List<Task> findByDepartmentAndParentTaskIsNull(Department department);

    Task findByPriorityAndParentTaskId(int priority, UUID parentTaskId);

}
