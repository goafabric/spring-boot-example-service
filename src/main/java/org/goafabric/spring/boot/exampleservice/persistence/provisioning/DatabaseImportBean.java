package org.goafabric.spring.boot.exampleservice.persistence.provisioning;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DatabaseImportBean implements DatabaseImport {

    @Override
    public void importCatalogData(String... args) {
        log.info("dummy implementation for catalog import");
    }

    @Override
    public void importDemoData(String... args) {
        log.info("dummy implementation for demodata import");
    }
}
