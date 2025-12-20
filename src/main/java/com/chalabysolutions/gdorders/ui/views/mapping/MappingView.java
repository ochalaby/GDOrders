package com.chalabysolutions.gdorders.ui.views.mapping;

import com.chalabysolutions.gdorders.service.AccountDataService;
import com.chalabysolutions.gdorders.service.MappingDataService;
import com.chalabysolutions.gdorders.ui.layout.MainLayout;
import com.chalabysolutions.gdorders.ui.views.StatusBar;
import com.chalabysolutions.gdorders.ui.views.StatusLevel;
import com.chalabysolutions.gdorders.ui.views.mapping.components.MappingForm;
import com.chalabysolutions.gdorders.ui.views.mapping.components.MappingFormState;
import com.chalabysolutions.gdorders.ui.views.mapping.components.MappingGrid;
import com.chalabysolutions.gdorders.ui.views.mapping.logic.MappingPresenter;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "mapping", layout = MainLayout.class)
@PageTitle("Mapping")
@PreserveOnRefresh
public class MappingView extends VerticalLayout {

    public MappingView(MappingDataService mapping, AccountDataService accountService, MappingFormState formState) {
        setSizeFull();
        setPadding(true);

        MappingPresenter p =  new MappingPresenter(mapping, accountService);

        StatusBar status = new StatusBar();
        p.setStatusBar(status);

        H3 mappingTitle = new H3("Klant adres mapping");
        MappingForm mappingForm = new MappingForm(p, formState);
        p.setMappingForm(mappingForm);
        MappingGrid mappingGrid = new MappingGrid(p);
        p.setMappingGrid(mappingGrid);

        add(mappingTitle, mappingForm, mappingGrid, status);

        // Lazy load accounts bij attach
        addAttachListener(event -> {
            boolean loaded = accountService.ensureAccountsLoaded();
            if (loaded) {
                mappingForm.setAccounts(p.getAccounts());
            } else {
                mappingForm.setAccounts(List.of()); // lege lijst
                status.show(StatusLevel.ERROR, "Kan accounts niet laden: " + accountService.getErrorMessage().orElse("Onbekende fout"));
            }

            // Reload mappings (JSON) en refresh Grid
            p.reloadMappings();
        });
    }

}
