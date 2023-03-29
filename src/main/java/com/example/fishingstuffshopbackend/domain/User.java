package com.example.fishingstuffshopbackend.domain;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.*;

import static java.util.Objects.requireNonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "fs_users")
@Where(clause = "deleted='false'")
@SQLDelete(sql = "UPDATE fs_users SET deleted = true WHERE id = ? and version = ?")
public class User extends BaseEntity {
    @Column(name = "first_name", nullable = false)
    @Size(min = 3, max = 75)
    private String firstName;

    @Column(name = "last_name")
    @Size(min = 3, max = 75)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @Size(min = 6, max = 150)
    private String email;

    @Column(name = "phone_number", nullable = false, unique = true)
    @Size(min = 12, max = 12)
    private String phoneNumber;

    @Column(name = "password", nullable = false)
    @Size(min = 8, max = 255)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "fs_users_roles",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @Builder.Default
    private Set<Role> roles = new HashSet<>(5);

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "user")
    @Builder.Default
    private Set<Order> orders = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @Builder.Default
    private Bucket bucket = new Bucket();

    private void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order) {
        requireNonNull(order);
        order.setUser(this);
        orders.add(order);
    }

    public void removeOrder(Order order) {
        requireNonNull(order);
        orders.remove(order);
        order.setUser(null);
    }

    private void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        requireNonNull(role);
        roles.add(role);
    }

    public void removeRole(Role role) {
        requireNonNull(role);
        roles.remove(role);
    }
}
