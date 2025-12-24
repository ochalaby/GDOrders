package com.chalabysolutions.gdorders.ui.views.orders.components;

public record MissingAddressMapping(
        String externalAddressId,
        String formattedExternalAddress,
        String accountCode,
        String accountName
) {}

