package com.joyful.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joyful.entity.Variant;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;
import java.util.Map;

@Converter
public class VariantMapConverter implements AttributeConverter<Map<String, List<Variant>>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, List<Variant>> variantsMap) {
        try {
            return objectMapper.writeValueAsString(variantsMap);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to serialize variantsMap to JSON", e);
        }
    }

    @Override
    public Map<String, List<Variant>> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<>() {});
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to deserialize JSON to variantsMap", e);
        }
    }
}
