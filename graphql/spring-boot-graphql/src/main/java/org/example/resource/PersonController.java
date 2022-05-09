package org.example.resource;

import graphql.ExecutionResult;
import org.example.service.PersonGraphQLService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/graphql")
public class PersonController {

    private final PersonGraphQLService personGraphQLService;

    public PersonController(PersonGraphQLService personGraphQLService) {
        this.personGraphQLService = personGraphQLService;
    }


    @GetMapping(value = "/persons", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExecutionResult> String(@RequestBody String query) {
        ExecutionResult result = personGraphQLService.getGraphQL().execute(query);
        return ResponseEntity.ok(result);
    }
}
