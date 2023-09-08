package com.example.manageequipment;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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

}
