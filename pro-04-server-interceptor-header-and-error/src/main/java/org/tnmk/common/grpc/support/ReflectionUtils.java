package org.tnmk.common.grpc.support;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;


public class ReflectionUtils {

    /**
     * @param bean
     * @param excludeSuperClass
     * @return Get all values of properties inside the bean.
     * key: the field name.
     * value: the field value.
     *
     * The value will be get via getter methods. So if that field doesn't have getter, that value will not be in the result.
     * The result includes fields from super class, but will include classes from @param stopSuperClass.
     */
    public static Map<String, Object> getPropertyValuesOfBean(Object bean, Class excludeSuperClass) {
        try {
            Map<String, Object> propertyValues = new HashMap<>();

            Class<?> beanClass = bean.getClass();
            BeanInfo beanInfo = Introspector.getBeanInfo(beanClass, excludeSuperClass);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                Object fieldValue = propertyDescriptor.getReadMethod().invoke(bean);
                propertyValues.put(propertyDescriptor.getName(), fieldValue);
            }
            return propertyValues;
        }catch (IntrospectionException | InvocationTargetException | IllegalAccessException ex){
            throw new ReflectionGetPropertyException("Unexpected exception when getting property values from the ban "+bean +" with stopSuperClass "+excludeSuperClass, ex);
        }
    }
}
