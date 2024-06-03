//package com.GSU24SE43.ConstructionDrawingManagement.configuration;
//
//import com.GSU24SE43.ConstructionDrawingManagement.entity.Account;
//import com.GSU24SE43.ConstructionDrawingManagement.enums.Role;
//import com.GSU24SE43.ConstructionDrawingManagement.repository.AccountRepository;
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.HashSet;
//
//@Configuration
//@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//@Slf4j
//public class ApplicationInitConfiguration {
//    PasswordEncoder passwordEncoder;
//
//    @Bean
//    ApplicationRunner applicationRunner(AccountRepository accountRepository){
//        return args -> {
//            if(accountRepository.findByUsername("admin") == null){
//                HashSet<String> roles = new HashSet<>();
//                roles.add(Role.ADMIN.name());
//
//                Account account = Account.builder()
//                        .username("admin")
//                        .password(passwordEncoder.encode("admin"))
//                        .roles(roles)
//                        .build();
//
//                accountRepository.save(account);
//                log.warn("admin User Had Been Created With Default Password: admin, Please Change It After Login");
//            }
//        };
//    }
//}
