package com.microecom.catalogservice.model.client;

import com.microecom.catalogservice.model.client.data.Stock;

import java.util.Collection;
import java.util.Map;

public interface InventoryClient {
    Map<String, Stock> retrieveCalculatedStock(Collection<String> forProductIds);
}
