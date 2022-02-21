package com.wang.wiki.util;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 复制工具类
 *
 * @author Wang
 */
public class CopyUtil {

    /**
     * 单体复制
     *
     * @param source 原对象
     * @param clazz  目标类型
     * @return 目标对象
     */
    public static <T> T copy(Object source, Class<T> clazz) {
        if (source == null) {
            return null;
        }
        T obj;
        try {
            obj = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BeanUtils.copyProperties(source, obj);
        return obj;
    }

    /**
     * 列表复制
     *
     * @param source 原对象list
     * @param clazz  目标类型
     * @return 目标对象list
     */
    public static <T> List<T> copyList(List<?> source, Class<T> clazz) {
        List<T> target = new ArrayList<>();
        if (!CollectionUtils.isEmpty(source)) {
            for (Object c : source) {
                T obj = copy(c, clazz);
                target.add(obj);
            }
        }
        return target;
    }

    public static <T> List<T> copyListMap(List<Map<String, Object>> source, Class<T> clazz) {
        List<T> target = new ArrayList<>();
        if (!CollectionUtils.isEmpty(source)) {
            for (Map<String, Object> stringObjectMap : source) {
                T obj;
                try {
                    obj = clazz.newInstance();
                    Field[] fields = clazz.getDeclaredFields();
                    for (Field f : fields) {
                        PropertyDescriptor pd = new PropertyDescriptor(f.getName(), clazz);
                        Object value = stringObjectMap.get(pd.getName());
                        if (value != null) {
                            String type = pd.getPropertyType().getName();
                            Method wM = pd.getWriteMethod();
                            switch (type) {
                                case "java.lang.String":
                                    wM.invoke(obj, String.valueOf(value));
                                    break;
                                case "java.util.Date":
                                    wM.invoke(obj, DateUtil.parse(String.valueOf(value)));
                                    break;
                                case "java.lang.Integer":
                                    wM.invoke(obj, Integer.parseInt(String.valueOf(value)));
                                    break;
                                case "java.lang.Long":
                                    wM.invoke(obj, Long.parseLong(String.valueOf(value)));
                                    break;
                            }

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                target.add(obj);
            }
        }
        return target;
    }
}
