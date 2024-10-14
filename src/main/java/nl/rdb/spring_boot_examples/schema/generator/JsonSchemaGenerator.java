package nl.rdb.spring_boot_examples.schema.generator;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;

import lombok.extern.slf4j.Slf4j;
import nl.rdb.spring_boot_examples.schema.annotation.JsonSchema;
import nl.rdb.spring_boot_examples.schema.annotation.JsonSchemaDefinition;
import nl.rdb.spring_boot_examples.schema.annotation.JsonSchemaOptional;

import org.apache.commons.io.FileUtils;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.victools.jsonschema.generator.OptionPreset;
import com.github.victools.jsonschema.generator.SchemaGenerator;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfig;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfigBuilder;
import com.github.victools.jsonschema.generator.SchemaVersion;

@Slf4j
public class JsonSchemaGenerator {

    private static final String XML_LOCATION = "src/main/resources/schemas/json";
    private final Map<Class<?>, JsonNode> schemas = new HashMap<>();

    public Map<Class<?>, JsonNode> createSchemas() {
        SchemaGeneratorConfig config = getConfig();
        SchemaGenerator generator = new SchemaGenerator(config);

        Set<Class<?>> classes = findClasses();

        classes.forEach(clazz -> {
            Type schemaClass = Optional.ofNullable(clazz.getAnnotation(JsonSchema.class))
                    .map(JsonSchema::schemaClass)
                    .orElse(null);
            JsonNode schema = generator.generateSchema(schemaClass == void.class ? clazz : schemaClass);
            generateFile(clazz, schema);
            schemas.put(clazz, schema);
        });

        return schemas;
    }

    private SchemaGeneratorConfig getConfig() {
        SchemaGeneratorConfigBuilder configBuilder = new SchemaGeneratorConfigBuilder(SchemaVersion.DRAFT_2020_12, OptionPreset.PLAIN_JSON);

        configBuilder.forFields()
                .withPropertyNameOverrideResolver(field -> Optional.ofNullable(field.getAnnotationConsideringFieldAndGetter(JsonProperty.class))
                        .map(JsonProperty::value)
                        .orElse(field.getName()))
                .withTitleResolver(field -> Optional.ofNullable(field.getAnnotationConsideringFieldAndGetter(JsonSchemaDefinition.class))
                        .map(JsonSchemaDefinition::title)
                        .orElse(null))
                .withDescriptionResolver(field -> Optional.ofNullable(field.getAnnotationConsideringFieldAndGetter(JsonSchemaDefinition.class))
                        .map(JsonSchemaDefinition::description)
                        .orElse(null))
                .withRequiredCheck(field -> Optional.ofNullable(field.getAnnotationConsideringFieldAndGetter(JsonSchemaOptional.class)).isEmpty());
        configBuilder.forTypesInGeneral()
                .withTitleResolver(scope -> Optional.ofNullable(scope.getType().getErasedType().getAnnotation(JsonSchemaDefinition.class))
                        .map(JsonSchemaDefinition::title)
                        .orElse(null))
                .withDescriptionResolver(scope -> Optional.ofNullable(scope.getType().getErasedType().getAnnotation(JsonSchemaDefinition.class))
                        .map(JsonSchemaDefinition::description)
                        .orElse(null));

        return configBuilder.build();
    }

    private Set<Class<?>> findClasses() {
        Reflections reflections = new Reflections(getPackage(), Scanners.values());
        return reflections.getTypesAnnotatedWith(JsonSchema.class);
    }

    private void generateFile(Class<?> clazz, JsonNode schema) {
        String className = Optional.of(clazz.getAnnotation(JsonSchema.class))
                .filter(annotation -> !annotation.prefix().isEmpty())
                .map(JsonSchema::prefix)
                .orElse(clazz.getSimpleName().toLowerCase());

        String xmlFile = "%s.schema.json".formatted(className);
        String fullLocation = "%s/%s".formatted(XML_LOCATION, xmlFile);
        try {
            File file = new File(fullLocation);

            if (!file.exists()) {
                FileUtils.write(file, schema.toPrettyString(), UTF_8);
            } else {
                String data = FileUtils.readFileToString(file, UTF_8);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode node = objectMapper.convertValue(data, JsonNode.class);

                if (!node.equals(schema)) {
                    FileUtils.write(file, schema.toPrettyString(), UTF_8);
                }
            }
        } catch (Exception ex) {
            log.error("Could not create schema file: {}", ex.getMessage(), ex);
        }
    }

    private String getPackage() {
        StringJoiner joiner = new StringJoiner(".");
        String[] test = this.getClass().getPackage().getName().split("\\.");
        for (int i = 0; i < 3; i++) {
            joiner.add(test[i]);
        }
        return joiner.toString();
    }
}
