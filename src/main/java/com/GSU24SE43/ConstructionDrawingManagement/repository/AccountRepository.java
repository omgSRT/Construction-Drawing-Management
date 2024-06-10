package com.GSU24SE43.ConstructionDrawingManagement.repository;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
     Optional<Account> findByUsername(String username);
     boolean existsByUsername(String username);
     List<Account> findByUsernameContainingIgnoreCase(String username);

}
