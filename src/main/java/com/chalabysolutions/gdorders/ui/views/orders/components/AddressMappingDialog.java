package com.chalabysolutions.gdorders.ui.views.orders.components;

import com.chalabysolutions.gdorders.model.accounts.AccountAddress;
import com.chalabysolutions.gdorders.model.generic.Address;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AddressMappingDialog extends Dialog {

    private final List<AddressMappingResult> results = new ArrayList<>();

    public AddressMappingDialog(
            List<MissingAddressMapping> missing,
            List<AccountAddress> internalAddresses,
            Consumer<List<AddressMappingResult>> onSave) {

        setWidth("800px");
        setCloseOnEsc(false);
        setCloseOnOutsideClick(false);

        VerticalLayout content = new VerticalLayout();
        content.add(new H3("Missende afleveradressen koppelen"));

        for (MissingAddressMapping m : missing) {

            FormLayout form = new FormLayout();
            form.getStyle().set("border-bottom", "1px solid var(--lumo-contrast-20pct)");

            TextArea external = new TextArea("Extern afleveradres");
            external.setValue(m.formattedExternalAddress());
            external.setReadOnly(true);

            ComboBox<AccountAddress> internal = new ComboBox<>("Intern afleveradres");
            internal.setItems(internalAddresses);
            internal.setItemLabelGenerator(Address::getFormattedAddress);
            internal.setRequired(true);

            internal.addValueChangeListener(e -> {
                results.removeIf(r -> r.externalAddressId().equals(m.externalAddressId()));
                if (e.getValue() != null) {
                    results.add(new AddressMappingResult(
                            m.externalAddressId(),
                            e.getValue().getId()
                    ));
                }
            });

            form.add(external, internal);
            content.add(form);
        }

        Button btnSave = new Button("Opslaan & verder", e -> {
            onSave.accept(results);
            close();
        });

        Button btnCancel = new Button("Cancel", e -> close());

        HorizontalLayout buttons = new HorizontalLayout(btnSave, btnCancel);
        buttons.setAlignItems(FlexComponent.Alignment.END);

        add(content, buttons);
    }
}

