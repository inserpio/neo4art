package it.inserpio.neo4art.boot;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author mh
 * @since 07.09.14
 */
@Configuration
@EnableNeo4jRepositories(basePackages = "it.inserpio.neo4art.repository")
@EnableTransactionManagement
public class MyNeo4jConfiguration extends Neo4jConfiguration {
    public static final String PATH = System.getProperty("user.home") + "/neo4art/graph.db";

    public MyNeo4jConfiguration() {
        setBasePackage("it.inserpio.neo4art.domain");
    }

    @Bean
    public GraphDatabaseService graphDatabaseService() {
        return new GraphDatabaseFactory().newEmbeddedDatabase(PATH);
    }

}
