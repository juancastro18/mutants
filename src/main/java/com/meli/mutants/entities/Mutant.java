package com.meli.mutants.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Mutant {

    @Id
    @GeneratedValue
    private Long id;
    private String adn;
    private LocalDate date;
    private boolean mutant;

    public Mutant(String adn, LocalDate date, boolean mutant) {
        this.adn = adn;
        this.date = date;
        this.mutant = mutant;
    }

}
