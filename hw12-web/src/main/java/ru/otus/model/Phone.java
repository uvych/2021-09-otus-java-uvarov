package ru.otus.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "phone")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String number;

    @ManyToOne
    private Client client;

    public Phone(Long id, String number) {
        this.id = id;
        this.number = number;
    }
}
