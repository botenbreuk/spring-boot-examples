package nl.rdb.spring_boot_examples.schema.validator;

import dev.harrel.jsonschema.Validator;
import dev.harrel.jsonschema.ValidatorFactory;

public class JsonSchemaValidator {

    private static final ValidatorFactory VALIDATOR = new ValidatorFactory();

    private JsonSchemaValidator() {}

    public static Validator.Result validate(String schema, String json) {
        return VALIDATOR.validate(schema, json);
    }
}
