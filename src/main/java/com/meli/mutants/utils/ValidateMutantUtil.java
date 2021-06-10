package com.meli.mutants.utils;

import com.meli.mutants.businessExceptions.InvalidAdnLetterException;
import com.meli.mutants.businessExceptions.InvalidSizeException;
import com.meli.mutants.businessExceptions.NoMutantException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ValidateMutantUtil {


    private static final Logger logger = LoggerFactory.getLogger(ValidateMutantUtil.class);
    private static final List<Character> validLetters = Arrays.asList('A', 'T', 'C', 'G');

    private ValidateMutantUtil() {
    }

    public static void validateLetter(List<Character> adnChar) {
        if (!validLetters.containsAll(adnChar)) {
            logger.error("Invalid letter");
            throw new InvalidAdnLetterException("The ADN has a invalid letter");
        }
    }

    public static void validateSize(List<String> adn) throws InvalidSizeException {
        if (Objects.isNull(adn) || adn.isEmpty()) {
            logger.error("List empty or null");
            throw new InvalidSizeException("The array can't be null or empty");
        }
        adn.forEach(row -> {
            if (adn.size() != row.length()) {
                logger.error("Difference on row and columns, rows: {}, columns: {}", row.length(), adn.size());
                throw new InvalidSizeException("The rows and columns size should be the same");
            }
        });
    }

    public static void validateResult(boolean isMutant) {
        if (!isMutant) {
            logger.error("No mutant");
            throw new NoMutantException("It is not a mutant");
        }
    }
}
