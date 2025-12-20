package com.chalabysolutions.gdorders;

import com.chalabysolutions.gdorders.service.AccountDataService;
import com.chalabysolutions.gdorders.service.MappingDataService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component()
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
        boolean loaded = mappingService.loadFromSettings();
        if (loaded) {
            mappingService.refreshDisplayInfo(accountService);
        }
    }
}
