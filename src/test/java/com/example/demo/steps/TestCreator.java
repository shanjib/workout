package com.example.demo.steps;

import com.example.demo.model.Exercise;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor
public class TestCreator {

    public Exercise createExercise(Map<String, String> row) {
        return buildObject(row, Exercise.class);
    }

    @SneakyThrows
    private <T> T buildObject(Map<String, String> row, Class<T> clazz) {
        T obj = (T) clazz.getConstructors()[0].newInstance();

        Field[] fields = clazz.getFields();
        List<Method> setters = Arrays.stream(clazz.getMethods()).filter(m -> m.getName().contains("set")).toList();

        for (String field : row.keySet()) {
            Optional<Method> setter = setters.stream().filter(m -> {
                String mname = m.getName().toLowerCase();
                String fname = field.toLowerCase();
                return mname.contains(fname);
            }).findAny();

            if (setter.isPresent()) {
                String value = row.get(field);
                Method set = setter.get();
                Parameter param = set.getParameters()[0];

                if (param.getType() == String.class) {
                    set.invoke(obj, value);
                } else {
                    log.error("Unable to cast {} to proper type {} for method {}", value, param.getType(), set.getName());
                }
            }
        }

        return obj;
    }
}
