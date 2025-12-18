package com.chalabysolutions.gdorders.model.accounts;

import com.chalabysolutions.gdorders.model.generic.Address;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AccountAddress extends Address
{
    private String phone;
    private String fax;
    private String type;
    private String _default;
}
