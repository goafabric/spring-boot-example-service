package org.goafabric.spring.boot.exampleservice.persistence.provisioning;

interface DatabaseImport {
    void importCatalogData(String... args);
    void importDemoData(String... args);
}
