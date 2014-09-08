package it.inserpio.neo4art.boot;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * @author mh
 * @since 07.09.14
 */
@EnableAutoConfiguration
@ComponentScan(basePackages = {"it.inserpio.neo4art.service.impl","it.inserpio.neo4art.controller"})
@Import(MyNeo4jConfiguration.class)
public class ArtworkApplication {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(ArtworkApplication.class, args);
    }
}
