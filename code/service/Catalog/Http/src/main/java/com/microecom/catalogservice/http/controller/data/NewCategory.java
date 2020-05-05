package com.microecom.catalogservice.http.controller.data;

import com.microecom.catalogservice.model.data.CategoryInfo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * User input for a new category.
 */
public class NewCategory implements CategoryInfo {
    private final String name;

    public NewCategory() {
        name = null;
    }

    public NewCategory(String name) {
        this.name = name;
    }

    @Override
    public @Size(min = 5, max = 255) @NotBlank String getName() {
        return name;
    }
}
