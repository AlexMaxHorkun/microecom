package com.microecom.catalogservice.http.controller;

import com.microecom.catalogservice.http.controller.data.NewProduct;
import com.microecom.catalogservice.http.controller.data.PatchProductUpdate;
import com.microecom.catalogservice.http.controller.data.ProductRead;
import com.microecom.catalogservice.model.ProductManager;
import com.microecom.catalogservice.model.data.ProductListCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

@RestController
@RequestMapping(path = "/rest/V1/product")
public class Products {
    private final ProductManager products;

    public Products(@Autowired ProductManager products) {
        this.products = products;
    }

    @PostMapping
    public ResponseEntity<ProductRead> create(@RequestBody @Valid @NotNull NewProduct product) {
        return new ResponseEntity<>(ProductRead.of(products.create(product)), HttpStatus.CREATED);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<ProductRead> update(@RequestBody @Valid @NotNull PatchProductUpdate update, @PathVariable @NotNull String id) {
        update.setId(id);

        return new ResponseEntity<>(ProductRead.of(products.update(update)), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable @NotNull String id) {
        products.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Iterable<ProductRead>> list(@RequestParam(required = false) String categoryId, @RequestParam(required = false) Set<String> productIds) {
        var found = products.findList(new ProductListCriteria(categoryId, null, categoryId != null ? true : null, productIds));

        return new ResponseEntity<>(ProductRead.listOf(found), HttpStatus.OK);
    }
}
