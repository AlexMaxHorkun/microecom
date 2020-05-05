package com.microecom.catalogservice.model.data;

import java.util.Optional;

public interface ExistingProduct extends ProductInfo {
    String getId();

    Optional<Boolean> isAvailable();
}
