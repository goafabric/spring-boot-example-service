package org.goafabric.spring.boot.exampleservice.persistence.multitenancy;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass()
@FilterDef(
        name = TenantAware.TENANT_FILTER_NAME,
        parameters = @ParamDef(name = TenantAware.TENANT_FILTER_ARGUMENT_NAME, type = "string"),
        defaultCondition = TenantAware.TENANT_ID_PROPERTY_NAME + "= :" + TenantAware.TENANT_FILTER_ARGUMENT_NAME)
@Filter(name = TenantAware.TENANT_FILTER_NAME)
@Getter
@Setter
public class TenantAware {
    public static final String TENANT_FILTER_NAME = "tenantFilter";
    public static final String TENANT_ID_PROPERTY_NAME = "tenantid";
    public static final String TENANT_FILTER_ARGUMENT_NAME = TENANT_ID_PROPERTY_NAME;

    @Column(name = TENANT_ID_PROPERTY_NAME)
    private String tenantid;

}


/*
    https://medium.com/@vivareddy/muti-tenant-with-discriminator-column-hibernate-implementation-a363f03b1d10
    https://medium.com/@Integral_io/elegant-multi-tenancy-for-microservices-part-ii-solutioning-d0d9b6cd6f0f
    https://bytefish.de/blog/spring_boot_multitenancy/
 */