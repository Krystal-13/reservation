package com.zerobase.reservation.restaurant.menu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.reservation.restaurant.menu.entity.Menu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MenuListToJsonConverter implements AttributeConverter<List<Menu>, String> {

    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public String convertToDatabaseColumn(List<Menu> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Menu> convertToEntityAttribute(String dbData) {
        TypeReference<List<Menu>> typeReference = new TypeReference<List<Menu>>() {};
        try {
            return objectMapper.readValue(dbData, typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
