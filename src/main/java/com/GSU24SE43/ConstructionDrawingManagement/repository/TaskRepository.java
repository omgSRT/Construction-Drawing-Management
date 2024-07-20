package com.GSU24SE43.ConstructionDrawingManagement.repository;

import com.GSU24SE43.ConstructionDrawingManagement.entity.Account;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Department;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findByParentTaskIsNull();
    List<Task> findByAccountAndParentTaskIsNull(Account account);//admin
    List<Task> findByAccount_AccountIdAndParentTaskIsNotNull(UUID account);//admin
    List<Task> findByDepartmentAndParentTaskIsNull(Department department);//head
    List<Task> findByDepartmentAndParentTaskIsNotNull(Department department);//head

    @Query("SELECT t FROM Task t WHERE t.parentTask IS NOT NULL AND t.account.accountId = :accountId")
    List<Task> findTasksWithParentByAccountId(@Param("accountId") UUID accountId);

    List<Task> findByTitleContainingIgnoreCase(String title);
    List<Task> findByStatusContainingIgnoreCase(String status);
    List<Task> findByBeginDate(Date beginDate);
    List<Task> findByEndDate(Date beginDate);

    Task findByPriorityAndParentTaskId(int priority, UUID parentTaskId);

}
