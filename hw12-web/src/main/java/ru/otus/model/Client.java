package ru.otus.model;


import com.google.gson.annotations.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "clients")
public class Client implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.PERSIST)
    @SerializedName(value = "adress")
    private Address address;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.PERSIST, mappedBy = "client")
    @SerializedName(value = "phone")
    private List<Phone> phones;

    public Client() {
    }

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
        addClient();
    }

    private void addClient() {
        phones.forEach(phone -> phone.setClient(this));
    }

        @Override
    public Client clone() {
        return new Client(this.id, this.name);
    }

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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Phone> getPhone() {
        return phones;
    }

    public void setPhone(List<Phone> phone) {
        this.phones = phone;
    }

    @Override
    public String toString() {
        return "Client{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", address=" + address +
            ", phones=" + phones +
            '}';
    }
}
