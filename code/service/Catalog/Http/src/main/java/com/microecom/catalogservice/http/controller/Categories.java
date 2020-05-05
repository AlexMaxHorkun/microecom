package com.microecom.catalogservice.http.controller;

import com.microecom.catalogservice.http.controller.data.CategoryRead;
import com.microecom.catalogservice.http.controller.data.NewCategory;
import com.microecom.catalogservice.http.controller.data.PatchCategoryUpdate;
import com.microecom.catalogservice.model.CategoryManager;
import com.microecom.catalogservice.model.data.ExistingCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/rest/V1/category")
public class Categories {
    private final CategoryManager categories;

    public Categories(@Autowired CategoryManager categories) {
        this.categories = categories;
    }

    @PostMapping
    public ResponseEntity<CategoryRead> create(@RequestBody @NotNull @Valid NewCategory category) {
        return new ResponseEntity<>(CategoryRead.of(categories.create(category)), HttpStatus.CREATED);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<CategoryRead> update(@RequestBody @NotNull @Valid PatchCategoryUpdate update, @NotNull @PathVariable String id) {
        update.setId(id);
        return new ResponseEntity<>(CategoryRead.of(categories.update(update)), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> delete(@NotNull @PathVariable String id) {
        categories.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CategoryRead>> list(@RequestParam(required = false, defaultValue = "false") Boolean onlyWithProducts) {
        var list = categories.findList(onlyWithProducts);
        var result = new ArrayList<CategoryRead>();
        for (ExistingCategory cat : list) {
            result.add(CategoryRead.of(cat));
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
