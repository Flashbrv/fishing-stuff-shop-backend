package com.example.fishingstuffshopbackend.model;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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
    @Size(min = 13, max = 13)
    private String phoneNumber;

    @Column(name = "password", nullable = false)
    @Size(min = 8, max = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "user")
    @Builder.Default
    private List<Order> orders = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private Bucket bucket;

    private void setOrders(List<Order> orders) {
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
}
