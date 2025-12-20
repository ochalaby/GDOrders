package com.chalabysolutions.gdorders.ui.views.mapping.components;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@SessionScope
@Component
@Data
public class MappingFormState {

    private String selectedAccountId;
    private String selectedAddressId;
    private String customerAddressId;

    public void clear() {
        selectedAccountId = null;
        selectedAddressId = null;
        customerAddressId = null;
    }
}
