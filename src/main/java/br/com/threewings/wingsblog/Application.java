package br.com.threewings.wingsblog;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default server url")})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
