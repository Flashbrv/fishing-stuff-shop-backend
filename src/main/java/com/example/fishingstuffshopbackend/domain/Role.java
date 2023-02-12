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

    Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    private Role(Roles role) {
        this(role.name());
    }

    public String getName() {
        return name;
    }

    public static Role getUserRole() {
        return new Role(Roles.ROLE_USER);
    }

    private enum Roles{
        ROLE_USER, ROLE_MANAGER, ROLE_ADMIN
    }
}
