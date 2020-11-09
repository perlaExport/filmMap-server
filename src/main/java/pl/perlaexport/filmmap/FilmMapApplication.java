package pl.perlaexport.filmmap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import pl.perlaexport.filmmap.security.config.AuthProperties;


@SpringBootApplication
@EnableConfigurationProperties(AuthProperties.class)
public class FilmMapApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilmMapApplication.class, args);
    }

}
