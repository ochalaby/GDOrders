package com.chalabysolutions.gdorders.ui.views.mapping.components;

import com.chalabysolutions.gdorders.model.mapping.AddressMapping;
import com.chalabysolutions.gdorders.ui.views.mapping.logic.MappingPresenter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.provider.ListDataProvider;

public class MappingGrid extends Grid<AddressMapping> {

    private final ListDataProvider<AddressMapping> provider;

    public MappingGrid(MappingPresenter presenter) {
        super(AddressMapping.class, false);
        setHeight("200px");
        addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        provider = new ListDataProvider<>(presenter.getAddressMapping());
        setDataProvider(provider);

        addColumn(AddressMapping::getAccountName).setHeader("Account naam").setWidth("150px").setFlexGrow(0).setResizable(true);
        addColumn(AddressMapping::getAccountCode).setHeader("Interne account code").setWidth("180px").setFlexGrow(0).setResizable(true);
        addColumn(AddressMapping::getDeliveryAddress).setHeader("Aflever adres").setWidth("380px").setFlexGrow(0).setResizable(true);
        addColumn(AddressMapping::getInternalAddressId).setHeader("Interne adres ID").setWidth("350px").setFlexGrow(0).setResizable(true);
        addColumn(AddressMapping::getAccountAddressId).setHeader("Sales order adres ID").setWidth("350px").setFlexGrow(0).setResizable(true);

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
