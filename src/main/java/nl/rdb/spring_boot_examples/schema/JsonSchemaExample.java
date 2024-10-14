package nl.rdb.spring_boot_examples.schema;

import static nl.rdb.spring_boot_examples.schema.validator.JsonSchemaValidator.validate;

import java.util.List;
import java.util.Map;

import dev.harrel.jsonschema.Validator;
import lombok.extern.slf4j.Slf4j;
import nl.rdb.spring_boot_examples.config.example.Example;
import nl.rdb.spring_boot_examples.schema.entity.Pet;
import nl.rdb.spring_boot_examples.schema.entity.PetType;
import nl.rdb.spring_boot_examples.schema.entity.User;
import nl.rdb.spring_boot_examples.schema.generator.JsonSchemaGenerator;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;

@Slf4j
@Component
public class JsonSchemaExample implements Example {

    @Override
    public void run() {
        Gson gson = new Gson();
        User user = new User("John", 21, List.of(new Pet("Banana", PetType.BIRD)));
        String json = gson.toJson(user);

        JsonSchemaGenerator schemaGenerator = new JsonSchemaGenerator();
        Map<Class<?>, JsonNode> schemas = schemaGenerator.createSchemas();

        boolean valid = validate(schemas.get(User.class).toString(), json).isValid();
        log.info("Json is {}", valid ? "valid" : "invalid");

        Validator.Result result = validate(schemas.get(Pet.class).toString(), json);
        boolean invalid = result.isValid();
        log.info("Json is {}", invalid ? "valid" : "invalid");
        result.getErrors().forEach(e -> log.info("Json is {} -> {}", e.getEvaluationPath(), e.getError()));
    }
}
