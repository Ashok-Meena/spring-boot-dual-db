package com.ashokjeph.dualdb.config.entity.secondary;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Map;
@Slf4j
public class EntityMapper {
    public static void mapModel(Object object, Map<String, Object> objectModel) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                if (objectModel.get(field.getName()) != null)
                    field.set(object, objectModel.get(field.getName()));
            } catch (Exception e) {
                System.out.println(field.getName() + ":" + e.getClass().getSimpleName() + ":" + e.getStackTrace()[0].toString());
            }
        }
    }
}
