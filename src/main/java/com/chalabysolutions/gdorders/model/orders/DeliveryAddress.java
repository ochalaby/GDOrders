package com.chalabysolutions.gdorders.model.orders;

import com.chalabysolutions.gdorders.model.generic.Address;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DeliveryAddress extends Address
{
    private Contact contact;
}
