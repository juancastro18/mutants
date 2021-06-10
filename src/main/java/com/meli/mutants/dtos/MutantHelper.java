package com.meli.mutants.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MutantHelper {

    private Integer index;
    private Integer adnTotalSize;
    private Integer adnRowSize;
    private Integer amountSiblings;
    private Integer adnMutantSequence;
    private boolean mutant;
    private String adn;

    public MutantHelper(Integer adnTotalSize, Integer adnRowSize, Integer amountSiblings, String adn) {
        this.adnTotalSize = adnTotalSize;
        this.adnRowSize = adnRowSize;
        this.amountSiblings = amountSiblings;
        this.adnMutantSequence = 0;
        this.adn = adn;
    }

    public MutantHelper(boolean mutant) {
        this.mutant = mutant;
    }

    public void plusOneAdnMutantSequence() {
        adnMutantSequence = adnMutantSequence + 1;
    }

}
