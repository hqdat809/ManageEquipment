package com.example.manageequipment;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.manageequipment.model.Role;
import com.example.manageequipment.model.User;
import com.example.manageequipment.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ManageEquipmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManageEquipmentApplication.class, args);
    }

    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dxnftdew6",
                "api_key", "759429928931825",
                "api_secret", "wvoc0pWqfthuGnyT1bWgCMafv8U",
                "secure", true));
        return cloudinary;
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    	@Bean
//        CommandLineRunner run(UserService userService) {
//    		return args -> {
//                userService.saveRole(Role.builder().name("ADMIN").build());
//                userService.saveRole(Role.builder().name("USER").build());
//
//                userService.saveUser(User.builder()
//    					.firstName("ha quoc")
//                        .lastName("dat")
//    					.address("648 tay son")
//    					.email("hqdat0809@gmail.com")
//    					.password("0392338494")
//    					.build());
//                userService.saveUser(User.builder()
//                        .firstName("ha quoc")
//                        .lastName("dat")
//                        .address("648 tay son")
//                        .email("hqdat08092001@gmail.com")
//                        .password("0392338494")
//                        .build());
//
//                userService.addRoleToStudent("hqdat0809@gmail.com", "ADMIN");
//                userService.addRoleToStudent("hqdat08092001@gmail.com", "USER");
//    		};
//    	}

}
