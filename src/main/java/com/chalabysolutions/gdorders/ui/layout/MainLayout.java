package com.chalabysolutions.gdorders.ui.layout;

import com.chalabysolutions.gdorders.ui.views.accounts.AccountsView;
import com.chalabysolutions.gdorders.ui.views.mapping.MappingView;
import com.chalabysolutions.gdorders.ui.views.orders.OrdersView;
import com.chalabysolutions.gdorders.ui.views.settings.SettingsView;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/* ===================== AppLayout with top menu ===================== */
public class MainLayout extends AppLayout implements AfterNavigationObserver {

    private final Div routerContent = new Div();
    private final Tabs menu = new Tabs();
    private final Tab ordersTab = new Tab(new RouterLink("Orders", OrdersView.class));
    private final Tab accountsTab = new Tab(new RouterLink("Accounts", AccountsView.class));
    private final Tab mappingTab = new Tab(new RouterLink("Mapping", MappingView.class));
    private final Tab settingsTab = new Tab(new RouterLink("Settings", SettingsView.class));

    @Autowired
    public MainLayout(@Value("${app.version}") String version) {
        menu.add(ordersTab, accountsTab, mappingTab, settingsTab);
        menu.setSelectedTab(ordersTab);
        setPrimarySection(Section.DRAWER);
        addToNavbar(menu);

        VerticalLayout main = new VerticalLayout();
        main.setSizeFull();
        main.setPadding(false);
        main.setSpacing(false);

        routerContent.setSizeFull();
        routerContent.getStyle().set("flex", "1");

        HorizontalLayout footerLayout = new HorizontalLayout();
        footerLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        footerLayout.setPadding(true);
        footerLayout.add(new Span("Versie " + version));

        Footer footer = new Footer();
        footer.setWidthFull();
        footer.getStyle()
                .set("border-top", "1px solid var(--lumo-contrast-10pct)")
                .set("font-size", "var(--lumo-font-size-xs)")
                .set("color", "var(--lumo-secondary-text-color)");
        footer.add(footerLayout);

        main.add(routerContent, footer);
        setContent(main);
    }

    @Override
    public void showRouterLayoutContent(HasElement content) {
        routerContent.removeAll();
        routerContent.getElement().appendChild(content.getElement());
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        String route = event.getLocation().getFirstSegment();

        switch (route) {
            case "accounts":
                menu.setSelectedTab(accountsTab);
                break;
            case "mapping":
                menu.setSelectedTab(mappingTab);
                break;
            case "settings":
                menu.setSelectedTab(settingsTab);
                break;
            default: // root route "" → Orders
                menu.setSelectedTab(ordersTab);
                break;
        }
    }

}
