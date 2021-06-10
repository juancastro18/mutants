package com.meli.mutants.services;

import com.meli.mutants.businessExceptions.InvalidAdnLetterException;
import com.meli.mutants.businessExceptions.InvalidSizeException;
import com.meli.mutants.businessExceptions.NoMutantException;
import com.meli.mutants.dtos.MutantStatDTO;

import java.util.List;

public interface MutantService {

    MutantStatDTO getMutantsStat();

    boolean isMutant(List<String> adn) throws InvalidSizeException, InvalidAdnLetterException, NoMutantException;

}
