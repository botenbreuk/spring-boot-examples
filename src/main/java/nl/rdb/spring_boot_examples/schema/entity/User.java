package nl.rdb.spring_boot_examples.schema.entity;

import java.util.List;

import nl.rdb.spring_boot_examples.schema.annotation.JsonSchema;
import nl.rdb.spring_boot_examples.schema.annotation.JsonSchemaDefinition;

@JsonSchema
@JsonSchemaDefinition(title = "User object", description = "User object description")
public record User(String name, int age, List<Pet> pets) {}
