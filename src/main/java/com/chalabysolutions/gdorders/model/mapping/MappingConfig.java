package com.chalabysolutions.gdorders.model.mapping;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MappingConfig {
    private List<AddressMapping> addressMapping = new ArrayList<>();
}
