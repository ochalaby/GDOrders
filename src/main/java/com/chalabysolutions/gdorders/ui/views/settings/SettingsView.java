package com.chalabysolutions.gdorders.ui.views.settings;

import com.chalabysolutions.gdorders.service.SettingsService;
import com.chalabysolutions.gdorders.ui.layout.MainLayout;
import com.chalabysolutions.gdorders.ui.views.StatusLevel;
import com.chalabysolutions.gdorders.ui.views.settings.components.StatusBar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;

@Route(value = "settings", layout = MainLayout.class)
@PageTitle("Settings")
@PreserveOnRefresh
public class SettingsView extends VerticalLayout {

    public SettingsView(SettingsService settings) {
        setSizeFull();
        setPadding(true);

        TextField exportDir = new TextField("Export directory");
        exportDir.setWidthFull();
        exportDir.setValue(settings.getExportDir());

        TextField accountsFile = new TextField("Accounts XML file");
        accountsFile.setWidthFull();
        accountsFile.setValue(settings.getAccountsFile());

        TextField mappingFile = new TextField("Mapping JSON file");
        mappingFile.setWidthFull();
        mappingFile.setValue(settings.getMappingFile());

        TextField warehouseCode = new TextField("Warehouse code");
        warehouseCode.setWidthFull();
        warehouseCode.setValue(settings.getWarehouseCode());

        StatusBar status = new StatusBar();
        settings.setStatusBar(status);

        Button save = new Button("Opslaan", e -> {
            settings.setExportDir(exportDir.getValue());
            settings.setAccountsFile(accountsFile.getValue());
            settings.setMappingFile(mappingFile.getValue());
            settings.setWarehouseCode(warehouseCode.getValue());
            settings.save();
            status.show(StatusLevel.INFO, "Instellingen opgeslagen");
        });

        // 👉 Spacer die alle overgebleven ruimte opvult
        var spacer = new com.vaadin.flow.component.html.Div();
        spacer.setHeightFull();

        // Spacer krijgt de flex-grow, de rest blijft normaal
        setFlexGrow(1, spacer);

        add(exportDir, accountsFile, mappingFile, warehouseCode, save, spacer, status);
    }
}
