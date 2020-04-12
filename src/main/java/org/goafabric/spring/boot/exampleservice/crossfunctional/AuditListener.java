package org.goafabric.spring.boot.exampleservice.crossfunctional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

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
    public void afterInsert(Object person) {
        BeanUtil.getBean(AuditBean.class).afterInsert(person);
    }

    @PostUpdate
    public void afterUpdate(Object person) {
        BeanUtil.getBean(AuditBean.class).afterUpdate(person);
    }

    @PostRemove
    public void afterDelete(Object person) {
        BeanUtil.getBean(AuditBean.class).afterDelete(person);
    }

}
