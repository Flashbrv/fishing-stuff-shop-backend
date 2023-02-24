package com.example.fishingstuffshopbackend.domain;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "fs_roles")
@Where(clause = "deleted='false'")
@SQLDelete(sql = "UPDATE fs_roles SET deleted = true WHERE id = ? and version = ?")
public class Role extends BaseEntity {
    @Column(name = "name", unique = true)
    private String name;

    protected Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
