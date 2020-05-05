package com.microecom.catalogservice.http.controller.data;

import com.microecom.catalogservice.model.data.CategoryUpdate;

import javax.validation.constraints.Size;
import java.util.Optional;

/**
 * Input for a category update.
 */
public class PatchCategoryUpdate implements CategoryUpdate {
    private String id;

    private final String name;

    public PatchCategoryUpdate(String name) {
        this.name = name;
    }

    public PatchCategoryUpdate() {
        name = null;
    }

    @Override
    public String getForId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Optional<@Size(min = 5, max = 255) String> getName() {
        return Optional.ofNullable(name);
    }
}
