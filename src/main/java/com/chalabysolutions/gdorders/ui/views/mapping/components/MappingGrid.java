package com.chalabysolutions.gdorders.ui.views.mapping.components;

import com.chalabysolutions.gdorders.model.mapping.AddressMapping;
import com.chalabysolutions.gdorders.ui.views.mapping.logic.MappingPresenter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;

public class MappingGrid extends Grid<AddressMapping> {

    private final ListDataProvider<AddressMapping> provider;

    public MappingGrid(MappingPresenter presenter) {
        super(AddressMapping.class, false);
        setHeight("200px");

        provider = new ListDataProvider<>(presenter.getAddressMapping());
        setDataProvider(provider);

        TextField editAccountAddressId = new TextField();
        editAccountAddressId.setWidthFull();

        Editor<AddressMapping> editor = getEditor();
        editor.setBuffered(true);

        // (double) click = open editor
        addItemDoubleClickListener(e -> {
            editor.editItem(e.getItem());
            editAccountAddressId.focus();
        });

        // BINDER - koppelt de waarde van het object aan het veld
        Binder<AddressMapping> binder = new Binder<>(AddressMapping.class);
        editor.setBinder(binder);

        // bind het tekstveld aan het accountAddressId veld
        binder.bind(
                editAccountAddressId,
                AddressMapping::getAccountAddressId,
                AddressMapping::setAccountAddressId
        );

        // Opslaan bij verlaten van veld
        editAccountAddressId.addBlurListener(e -> editor.save());

        // De actie bij het opslaan
        editor.addSaveListener(e -> {
            binder.writeBeanIfValid(e.getItem());
            presenter.onAddressMappingEdited(e.getItem());
        });

        addColumn(m -> m.accountName).setHeader("Account naam").setWidth("150px").setFlexGrow(0).setResizable(true);
        addColumn(m -> m.accountCode).setHeader("Interne account code").setWidth("180px").setFlexGrow(0).setResizable(true);
        addColumn(m -> m.deliveryAddress).setHeader("Aflever adres").setWidth("380px").setFlexGrow(0).setResizable(true);
        addColumn(m -> m.internalAddressId).setHeader("Interne adres ID").setWidth("350px").setFlexGrow(0).setResizable(true);
        addColumn(AddressMapping::getAccountAddressId).setHeader("Sales order adres ID").setWidth("350px").setFlexGrow(0).setResizable(true)
                .setEditorComponent(editAccountAddressId);

        addComponentColumn(m -> {
            Button deleteBtn = new Button(new Icon(VaadinIcon.TRASH));
            deleteBtn.addClickListener(e ->
                    presenter.onAddressMappingDeleted(m)
            );
            deleteBtn.addClassName("delete-button");
            return deleteBtn;
        }).setHeader("").setAutoWidth(true).setFlexGrow(0);

    }

    public ListDataProvider<AddressMapping> getListDataProvider() {
        return provider;
    }
}
