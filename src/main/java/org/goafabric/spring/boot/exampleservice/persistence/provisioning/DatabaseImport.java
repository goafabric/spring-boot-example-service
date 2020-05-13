package org.goafabric.spring.boot.exampleservice.persistence.provisioning;

/*
 This interface should be implemented by a specific catalog maven module
 */
interface DatabaseImport {
    void importCatalogData(String... args);
}
