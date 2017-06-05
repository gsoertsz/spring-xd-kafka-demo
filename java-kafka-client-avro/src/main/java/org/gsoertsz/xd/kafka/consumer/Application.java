package org.gsoertsz.xd.kafka.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ConsumerConfiguration.class)
public class Application {

    @Autowired
    Consumer consumer;

    /**
     * @param args
     */
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner trigger() {
        return (String... args) -> {
            /*
            replaced start listening by annotation in Consumer ..
             */
        };
    }
}
