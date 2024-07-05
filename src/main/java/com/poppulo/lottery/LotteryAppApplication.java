package com.poppulo.lottery;

import com.poppulo.lottery.exception.GlobalExceptionHandler;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(GlobalExceptionHandler.class)
public class LotteryAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(LotteryAppApplication.class, args);
    }

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

}
