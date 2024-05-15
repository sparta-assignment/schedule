package org.sparta.schedule.utils.mapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class MapperDto {
    private Class<?> fieldType;
    private String fieldName;
    private String fieldValue;
}
