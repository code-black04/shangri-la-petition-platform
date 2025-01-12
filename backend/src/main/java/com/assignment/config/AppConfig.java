package com.assignment.config;

import com.assignment.entity.PetitionThresholdEntity;
import com.assignment.repository.PetitionThresholdRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Autowired
    private PetitionThresholdRepository repository;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper;
    }

    @Bean
    public ApplicationRunner initializeThreshold() {
        return args -> {
            if (repository.count() == 0) { // Check if table is empty
                PetitionThresholdEntity defaultThreshold = new PetitionThresholdEntity(1, 3);
                repository.save(defaultThreshold);
                System.out.println("Initialized threshold with value 0.");
            }
        };
    }

}
