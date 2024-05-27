package org.sparta.schedule.common.utils.mapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Dto와 Entity 필드 간 이름이 일치하지 않을 때
 * 해당 애노테이션을 이용하여 name을 지정해주면
 * 그 name으로 필드를 매핑
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MapperField {
    public String name() default "";
}
