package com.chalabysolutions.gdorders.model.orders;

import lombok.Data;

@Data
public class Contact
{
    private String id;
    private String lastName;
    private String middleName;
    private String firstName;
    private String initials;
    private String fullName;
    private String email;
}
