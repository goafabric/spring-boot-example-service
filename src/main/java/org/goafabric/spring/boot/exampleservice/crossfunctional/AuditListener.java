package org.goafabric.spring.boot.exampleservice.crossfunctional;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import java.lang.reflect.Field;

/**
 * Created by andreas.mautsch on 31.01.2018.
 */

@Slf4j
public class AuditListener {

    @Service
    private static class BeanUtil implements ApplicationContextAware {
        private static ApplicationContext context;
        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            context = applicationContext;
        }
        public static <T> T getBean(Class<T> beanClass) {
            return context.getBean(beanClass);
        }
    }

    @PostPersist
    public void afterInsert(Object object) throws NoSuchFieldException, IllegalAccessException {
        getId(object);
        BeanUtil.getBean(AuditBean.class).afterInsert(getId(object), object);
    }
    
    @PostUpdate
    public void afterUpdate(Object object) {
        BeanUtil.getBean(AuditBean.class).afterUpdate(getId(object), object);
    }

    @PostRemove
    public void afterDelete(Object object) {
        BeanUtil.getBean(AuditBean.class).afterDelete(getId(object), object);
    }

    private String getId(@NonNull Object object) {
        try {
            final Field field = object.getClass().getDeclaredField("id");
            field.setAccessible(true);
            return (String) field.get(object);
        } catch (ReflectiveOperationException e) {
           throw new IllegalStateException("could not retrieve id of: " + object.toString());
        }
    }

}
