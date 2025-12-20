package com.chalabysolutions.gdorders;

import com.chalabysolutions.gdorders.service.AccountDataService;
import com.chalabysolutions.gdorders.service.MappingDataService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class StartupRunner implements ApplicationRunner {
    private final AccountDataService accountService;
    private final MappingDataService mappingService;

    public StartupRunner(AccountDataService accountService,
                         MappingDataService mappingService) {
        this.accountService = accountService;
        this.mappingService = mappingService;
    }

    @Override
    public void run(ApplicationArguments args) {
        accountService.loadFromSettings();
        mappingService.loadFromSettings();
    }
}
