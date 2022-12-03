package com.example.fishingstuffshopbackend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.*;

import static java.util.Objects.requireNonNull;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "fs_user")
@Where(clause = "deleted='false'")
@SQLDelete(sql = "UPDATE fs_user SET deleted = true WHERE id = ? and version = ?")
public class UserEntity extends BaseEntity {
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
    @Email
    @Size(min = 13, max = 13)
    private String phoneNumber;

    @Column(name = "password", nullable = false)
    @Size(min = 8, max = 255)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderEntity> orders = new ArrayList<>();

    private void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
    }

    public void addOrder(OrderEntity order) {
        requireNonNull(order);
        order.setUser(this);
        orders.add(order);
    }

    public void removeOrder(OrderEntity order) {
        requireNonNull(order);
        orders.remove(order);
        order.setUser(null);
    }
}
