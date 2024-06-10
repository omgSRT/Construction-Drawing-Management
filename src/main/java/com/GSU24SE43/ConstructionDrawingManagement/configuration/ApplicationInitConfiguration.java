package com.GSU24SE43.ConstructionDrawingManagement.configuration;


import com.GSU24SE43.ConstructionDrawingManagement.entity.Account;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Staff;
import com.GSU24SE43.ConstructionDrawingManagement.enums.Role;
import com.GSU24SE43.ConstructionDrawingManagement.repository.AccountRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;


//thông tin role phải có trong token mới có thể phân quyền
//class này sẽ tạo 1 user admin khi mình start class này lên
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfiguration {

    PasswordEncoder passwordEncoder;
    @Bean
    ApplicationRunner applicationRunner(AccountRepository userRepository){
        return args -> {

            if(userRepository.findByUsername("admin").isEmpty()) {
                var roles = new HashSet<String>();
                roles.add("ROLE_" + Role.ADMIN.name());
                Account user = Account.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roleName("ROLE_" + Role.ADMIN.name())
                        .build();
                userRepository.save(user);
                log.warn("admin user has been created with default password: admin");
            }
        };
    }
}
