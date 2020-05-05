package com.microecom.catalogservice.model.storage.category.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "categories")
public class CategoryRow {
    @Id
    private UUID id;

    private String name;

    public CategoryRow(String name) {
        this.name = name;
        this.id = UUID.randomUUID();
    }

    protected CategoryRow() {}

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
