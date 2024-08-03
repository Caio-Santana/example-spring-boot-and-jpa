package com.samsoftware.example;

import com.github.javafaker.Faker;
import com.samsoftware.example.models.Author;
import com.samsoftware.example.models.Video;
import com.samsoftware.example.repositories.AuthorRepository;
import com.samsoftware.example.repositories.VideoRepository;
import com.samsoftware.example.specification.AuthorSpecification;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            AuthorRepository authorRepository,
            VideoRepository videoRepository
    ) {
        return args -> {
            insertAuthors(authorRepository);
            insertVideo(videoRepository);

//            Specification<Author> spec = Specification
//                    .where(AuthorSpecification.hasAge(36))
//                    .and(AuthorSpecification.firstnameLike("Il"));
//
//            authorRepository.findAll(spec).forEach(System.out::println);
        };
    }

    private void insertVideo(VideoRepository repository) {
        if (repository.count() <= 0) {

            var video = Video.builder()
                    .name("Java Tutorial")
                    .size(5)
                    .url("http://javatutorial.com")
                    .length(20)
                    .build();

            repository.save(video);
        }
    }

    public void insertAuthors(AuthorRepository repository) {
        if (repository.count() > 0) return;

        for (int i = 0; i < 50; i++) {

            Faker faker = new Faker();

            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = (firstName + "." + lastName + "_" + i + "@mail.com").toLowerCase();

            repository.save(
                    Author.builder()
                            .firstName(firstName)
                            .lastName(lastName)
                            .age(faker.number().numberBetween(20, 60))
                            .email(email)
                            .createdAt(LocalDateTime.now())
                            .createdBy(faker.name().username())
                            .build()
            );
        }

    }
}
