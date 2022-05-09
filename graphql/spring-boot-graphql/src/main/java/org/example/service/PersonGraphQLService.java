package org.example.service;

import graphql.GraphQL;
import graphql.scalars.ExtendedScalars;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.example.entity.Person;
import org.example.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class PersonGraphQLService {

    @Value("classpath:person.graphql")
    private Resource resource;

    private GraphQL graphQL;

    private final PersonService personService;

    public PersonGraphQLService(PersonService personService) {
        this.personService = personService;
    }

    @PostConstruct
    private void init() throws IOException {
        setupDB();
        TypeDefinitionRegistry registry = new SchemaParser().parse(resource.getFile());
        RuntimeWiring runtimeWiring = getRuntimeWiring();
        GraphQLSchema graphQLSchema = new SchemaGenerator().makeExecutableSchema(registry, runtimeWiring);
        graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private void setupDB() {
        personService.save(Arrays.asList(
                new Person(1L, "Veenay", "M", 30L, new String[]{"IT"}),
                new Person(2L, "Ravi", "M", 43L, new String[]{"Teacher", "NGO Worker"}),
                new Person(3L, "Shankar", "M", 26L, new String[]{"Tailor", "Artist"}),
                new Person(4L, "Vasanth", "F", 54L, new String[]{"Actor", "Singer", "Dancer"}),
                new Person(5L, "Anitha", "F", 21L, new String[]{"Footballer", "Pianist"})
        ));
    }

    private RuntimeWiring getRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", builder -> builder
                        .dataFetcher("person", (DataFetcher<Person>) dataFetchingEnvironment -> {
                            Long id = dataFetchingEnvironment.getArgument("id");
                            return personService.findById(id).orElseThrow(() -> new RecordNotFoundException("no person found for id : " + id));
                        })
                        .dataFetcher("allPersons", (DataFetcher<List<Person>>) dataFetchingEnvironment -> personService.findAll()))
                .scalar(ExtendedScalars.GraphQLLong)
                .build();
    }

    public GraphQL getGraphQL() {
        return graphQL;
    }
}
