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
    public void postPersist(Object person) {
        BeanUtil.getBean(AuditBean.class).postPersist(person);
    }

    @PostUpdate
    public void postUpdate(Object person) {
        BeanUtil.getBean(AuditBean.class).postUpdate(person);
    }

    @PostRemove
    public void postRemove(Object person) {
        BeanUtil.getBean(AuditBean.class).postRemove(person);
    }

}
