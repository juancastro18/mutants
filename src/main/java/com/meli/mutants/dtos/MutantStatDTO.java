package com.meli.mutants.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MutantStatDTO {

    @JsonProperty("count_mutant_dna")
    private final Long countMutantDna;
    @JsonProperty("count_human_dna")
    private final Long countHumanDna;
    private final Double ratio;

}
