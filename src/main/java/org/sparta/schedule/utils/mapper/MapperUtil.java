package org.sparta.schedule.utils.mapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MapperUtil {
    public static final Class<?> MAPPER_ANNOTATION = MapperField.class;
    private static boolean foundIdField = false;

    /**
     * <pre>targetClass에 모든 필드를 생성하는 생성자 필요</pre>
     * 모든 필드의 생성자를 가진 객체를 Entity로 변환하기 위한 Util 메서드
     * @param source 변환을 원하는 객체
     * @param targetClass 변환할 객체
     * @return targetClass로 변경된 인스턴스, 실패했다면 null 반환
     * @param <T> 변경할 인스턴스 타입
     */
    public static <T> T toEntity(Object source, Class<T> targetClass) {
        return toEntity(source, targetClass, null);
    }

    /**
     * <pre>ID 필드를 idValue값으로 초기화 한다.</pre>
     * 모든 필드의 생성자를 가진 객체를 Entity로 변환하기 위한 Util 메서드
     * @param source 변환을 원하는 객체
     * @param targetClass 변환할 객체
     * @param idValue 기본키와 같은 id 필드를 초기화 할 값
     * @return targetClass로 변경된 인스턴스, 실패했다면 null 반환
     * @param <T> 변경할 인스턴스 타입
     * @param <S> id 타입 클래스
     */
    public static <T, S> T toEntity(Object source, Class<T> targetClass, S idValue) {
        // 클래스 멤버 가져오기
        Object[] targetArgs = createNewFieldValue(source, targetClass, idValue);
        // 생성자 만들기
        T target = null;
        Class<?>[] fieldsType = getFieldTypes(getAllFields(targetClass));
        try {
            Constructor<T> constructor = targetClass.getConstructor(fieldsType);
            target = constructor.newInstance(targetArgs);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return target;
    }

    /**
     * 해당 클래스의 모든 필드와 상속된 필드까지 모두 확인한다.
     * @param targetClass 모든 필드를 확인할 클래스
     * @return 해당 클래스의 상속된 필드 및 모든 필드 List
     */
    private static List<Field> getAllFields(Class<?> targetClass) {
        List<Field> fields = new ArrayList<>();
        while (targetClass != null) {
            fields.addAll(Arrays.asList(targetClass.getDeclaredFields()));
            targetClass = targetClass.getSuperclass();
        }
        return fields;
    }

    /**
     * 생성자 생성을 위한 필드 타입 확인 메서드
     * @param fields 타입 확인할 필드 List
     * @return 해당 필드의 모든 타입 class
     */
    private static Class<?>[] getFieldTypes(List<Field> fields) {
        return fields.stream()
                .map(Field::getType)
                .toList().toArray(new Class[0]);
    }

    /**
     * 필드를 확인하여 필드명이 같을 경우 값을 생성
     * @param source 변환을 원하는 객체 인스턴스
     * @param targetClass 변환할 클래스
     * @param idValue 기본키와 같은 id 필드를 초기화 할 값
     * @return 생성자에 들어갈 필드 정보들
     */
    private static <S> Object[] createNewFieldValue(Object source, Class<?> targetClass, S idValue) {
        foundIdField = false;
        List<Field> sourceFields = getAllFields(source.getClass());
        List<Field> targetFields = getAllFields(targetClass);

        List<Object> targetArgs = new ArrayList<>();

        for (Field targetField : targetFields) {
            Object sourceFieldValue = null;
            // ID 필드 초기화
            if (!foundIdField) {
                setIdField(targetField, targetArgs, idValue);
                continue;
            }
            for (int i = 0; i < sourceFields.size(); i++) {
                Field sourceField = sourceFields.get(i);
                String sourceFieldName;

                sourceField.setAccessible(true);
                Annotation sourceFieldAnnotation = getCompareAnnotation(sourceField.getAnnotations(), MAPPER_ANNOTATION);

                // 필드에 어노테이션이 있는지 확인
                if (sourceFieldAnnotation != null) {
                    MapperField mapperField = (MapperField) sourceFieldAnnotation;
                    sourceFieldName = mapperField.name();
                } else {
                    sourceFieldName = sourceField.getName();
                }

                if (Objects.equals(sourceFieldName, targetField.getName())) {
                    sourceFieldValue = getFieldValue(source, sourceField, targetField.getType());
                    // 이미 찾았으므로 삭제
                    sourceFields.remove(sourceField);
                    break;
                }
            }
            if (sourceFieldValue == null) {
                sourceFieldValue = getInitializeValueByType(targetField.getType());
            }
            targetArgs.add(sourceFieldValue);
        }
        return targetArgs.toArray();
    }

    /**
     * 해당 source필드의 타입에 맞는 데이터를 가져온다.
     * @param source 값을 가져올 Field Object
     * @param sourceField 가져올 Object의 Field 정보
     * @param targetFieldClass 생성할 필드의 타입 클래스
     * @return 해당 필드의 타입에 맞는 데이터
     * @param <T> 생성할 필드의 타입
     */
    private static <T> T getFieldValue(Object source, Field sourceField, Class<T> targetFieldClass) {
        Object sourceFieldValue;
        try {
            sourceFieldValue = sourceField.get(source);
        } catch (Exception e) {
            sourceFieldValue = getInitializeValueByType(targetFieldClass);
        }
        return targetFieldClass.cast(sourceFieldValue);
    }

    /**
     * 필드명 매칭이 안되었을 때 기본값으로 초기화
     * @param type 필드 타입 클래스
     * @return 각자 필드 타입에 맞는 기본값
     * @param <T> 필드 타입 클래스
     */
    private static <T> T getInitializeValueByType(Class<T> type) {
        if (Objects.equals(type, String.class)) {
            return type.cast("");
        }else if (Objects.equals(type, Integer.class)) {
            return type.cast(0);
        }else if (Objects.equals(type, Long.class)) {
            return type.cast(0L);
        }
        return null;
    }

    /**
     * 애노테이션 일치 확인
     * @param sources 확인할 애노테이션 배열
     * @param targetClass 비교할 애노테이션 클래스
     * @return 일치 여부
     */
    private static Annotation getCompareAnnotation(Annotation[] sources, Class<?> targetClass) {
        for (Annotation source : sources) {
            if (source.annotationType().equals(targetClass)) {
                return source;
            }
        }
        return null;
    }

    private static boolean compareAnnotation(Annotation[] sources, Class<?>[] targetClasses) {
        int cnt = 0;
        for (Class<?> targetClass : targetClasses){
            for (Annotation source : sources) {
                if (source.annotationType().equals(targetClass)) {
                    cnt++;
                    break;
                }
            }
        }
        return cnt == targetClasses.length;
    }

    private static boolean isIdField(Field targetField) {
        Class<?> ID_ANNOTATION_CLS = jakarta.persistence.Id.class;
        Class<?> GENERATED_VALUE_ANNOTATION_CLS = jakarta.persistence.GeneratedValue.class;
        Annotation[] annotations = targetField.getAnnotations();
        Class<?>[] targetAnnotations = new Class<?>[] {ID_ANNOTATION_CLS, GENERATED_VALUE_ANNOTATION_CLS};
        return compareAnnotation(annotations, targetAnnotations);
    }

    private static <T> void setIdField (Field targetField, List<Object> targetArgs, T idValue) {
        if (isIdField(targetField)) {
            if (idValue == null) {
                targetArgs.add(null);
            } else {
                targetArgs.add(idValue);
            }
            foundIdField = true;
        }
    }
}
