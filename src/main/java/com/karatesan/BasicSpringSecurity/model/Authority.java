package com.karatesan.BasicSpringSecurity.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "authorities")
public class Authority {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "customer_id") //ktora kolumna laczy te dwa obiekty
    private Customer customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Authority{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", customer=" + customer +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authority authority = (Authority) o;
        return Objects.equals(id, authority.id) && Objects.equals(name, authority.name) && Objects.equals(customer, authority.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, customer);
    }
}
