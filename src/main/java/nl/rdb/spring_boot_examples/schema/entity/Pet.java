package nl.rdb.spring_boot_examples.schema.entity;

import nl.rdb.spring_boot_examples.schema.annotation.JsonSchema;
import nl.rdb.spring_boot_examples.schema.annotation.JsonSchemaDefinition;
import nl.rdb.spring_boot_examples.schema.annotation.JsonSchemaOptional;

@JsonSchema
@JsonSchemaDefinition(title = "Pet")
public record Pet(String name, @JsonSchemaOptional PetType type) {}
