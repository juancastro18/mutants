package com.meli.mutants.repositories;

import com.meli.mutants.entities.Mutant;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MutantRepository extends CrudRepository<Mutant, Long> {

    Optional<Mutant> findFirstByAdn(String adn);

    Long countByMutant(Boolean mutant);

}
