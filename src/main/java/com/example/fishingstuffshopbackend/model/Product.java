package com.example.fishingstuffshopbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "fs_products")
@Where(clause = "deleted='false'")
@SQLDelete(sql = "UPDATE fs_products SET deleted = true WHERE id = ? and version = ?")
public class Product extends BaseEntity {
    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "image")
    private String image;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @ManyToMany
    @JoinTable(
            name = "fs_products_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @Builder.Default
    private Set<Category> categories = new HashSet<>();

    private void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Category> getCategories() {
        return Collections.unmodifiableSet(categories);
    }

    public void addCategory(Category category) {
        Objects.requireNonNull(category);
        categories.add(category);
    }

    public void removeCategory(Category category) {
        categories.remove(category);
    }
}
