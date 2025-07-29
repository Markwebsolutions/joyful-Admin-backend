package com.joyful.converter;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joyful.entity.Variant;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class VariantMapConverter implements AttributeConverter<Map<String, List<Variant>>, String> {

	private final ObjectMapper mapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(Map<String, List<Variant>> attribute) {
		try {
			return mapper.writeValueAsString(attribute);
		} catch (Exception e) {
			throw new IllegalArgumentException("Could not convert variants map to JSON", e);
		}
	}

	@Override
	public Map<String, List<Variant>> convertToEntityAttribute(String dbData) {
		try {
			TypeReference<Map<String, List<Variant>>> typeRef = new TypeReference<>() {
			};
			return mapper.readValue(dbData, typeRef);
		} catch (Exception e) {
			throw new IllegalArgumentException("Could not read variants map from JSON", e);
		}
	}
}
