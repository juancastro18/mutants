package com.meli.mutants.services.impl;

import com.meli.mutants.businessExceptions.InvalidAdnLetterException;
import com.meli.mutants.businessExceptions.InvalidSizeException;
import com.meli.mutants.businessExceptions.NoMutantException;
import com.meli.mutants.dtos.MutantStatDTO;
import com.meli.mutants.entities.Mutant;
import com.meli.mutants.repositories.MutantRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
class MutantServiceImplTest {

    private static final Long MUTANT_AMOUNT = 40L;
    private static final Long HUMAN_AMOUNT = 100L;

    @Mock
    private MutantRepository mutantRepository;

    @InjectMocks
    private MutantServiceImpl mutantService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Mockito.when(mutantRepository.save(Mockito.any())).thenReturn(new Mutant());
    }

    @Test()
    void isMutantThrowsExceptionForInvalidLetterTest() {
        List<String> adn = mockMutantAdn();
        adn.set(0, "ATFCGA");

        Assertions.assertThrows(InvalidAdnLetterException.class, () ->
                mutantService.isMutant(adn)
        );
    }

    @Test()
    void isMutantThrowsExceptionForInvalidSizeTest() {
        List<String> adn = mockMutantAdn();
        adn.set(5, "ACGA");

        Assertions.assertThrows(InvalidSizeException.class, () ->
                mutantService.isMutant(adn)
        );
    }

    @Test()
    void isMutantThrowsExceptionForEmptyArray() {
        List<String> adn = new ArrayList<>();

        Assertions.assertThrows(InvalidSizeException.class, () ->
                mutantService.isMutant(adn)
        );
    }

    @Test()
    void isMutantThrowsExceptionForNullObject() {
        List<String> adn = null;

        Assertions.assertThrows(InvalidSizeException.class, () ->
                mutantService.isMutant(adn)
        );
    }


    @Test()
    void isMutantTrueOnlyRightDirectionTest() {
        List<String> adn = mockMutantAdn();
        adn.set(0, "AAAAAA");
        adn.set(1, "AAAAAA");

        boolean result = mutantService.isMutant(adn);

        Assertions.assertTrue(result);
    }

    @Test()
    void isMutantTrueOneRightOneButtonOneDiagonalRightTest() {
        List<String> adn = mockMutantAdn();

        boolean result = mutantService.isMutant(adn);

        Assertions.assertTrue(result);
    }

    @Test()
    void isMutantTrueLetterOnCornersTest() {
        List<String> adn = mockMutantAdnOnCorners();

        boolean result = mutantService.isMutant(adn);

        Assertions.assertTrue(result);
    }

    @Test()
    void isMutantFalseTest() {
        List<String> adn = mockNoMutantAdn();

        Assertions.assertThrows(NoMutantException.class, () -> mutantService.isMutant(adn));
    }

    @Test()
    void isMutantOneDiagonalRightOneDiagonalLeftTest() {
        boolean result = mutantService.isMutant(mockDiagonalSequence());
        Assertions.assertTrue(result);
    }

    @Test()
    void isMutantTest() {
        List<String> adn = mockDiagonalSequence();
        adn.set(2, "TTTAT");
        boolean result = mutantService.isMutant(adn);
        Assertions.assertTrue(result);
    }

    @Test()
    void isMutantFalse2Test() {
        List<String> adn = mockMutantAdnFalse();

        Assertions.assertThrows(NoMutantException.class, () -> mutantService.isMutant(adn));
    }

    @Test
    void getStatsTest() {
        Mockito.when(mutantRepository.countByMutant(true)).thenReturn(MUTANT_AMOUNT);
        Mockito.when(mutantRepository.countByMutant(false)).thenReturn(HUMAN_AMOUNT);

        MutantStatDTO mutantsStat = mutantService.getMutantsStat();

        Mockito.verify(mutantRepository, Mockito.times(2)).countByMutant(Mockito.anyBoolean());
        Assertions.assertEquals(MUTANT_AMOUNT, mutantsStat.getCountMutantDna());
        Assertions.assertEquals(HUMAN_AMOUNT, mutantsStat.getCountHumanDna());
        Assertions.assertEquals(0.4, mutantsStat.getRatio());
    }

    private List<String> mockMutantAdnFalse() {
        return Arrays.asList(
                "TGAAA",
                "AAACG",
                "TTTAT",
                "TAACT",
                "TAAAT"
        );
    }

    private List<String> mockMutantAdn() {
        return Arrays.asList(
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG");
    }

    private List<String> mockMutantAdnOnCorners() {
        return Arrays.asList(
                "ATAAAA",
                "CAGTGT",
                "TTATTG",
                "AGAAGG",
                "CCCCTG",
                "TCACTG");
    }

    private List<String> mockNoMutantAdn() {
        return Arrays.asList(
                "ATGCGA",
                "CAGTGC",
                "TTATTT",
                "AGACGG",
                "GCGTCA",
                "TCACTG");
    }

    private List<String> mockDiagonalSequence() {
        return Arrays.asList(
                "AAACA",
                "AAACA",
                "TTTAT",
                "AAACA",
                "AAACA"
        );
    }


}