package com.meli.mutants.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.mutants.businessExceptions.InvalidAdnLetterException;
import com.meli.mutants.businessExceptions.InvalidSizeException;
import com.meli.mutants.businessExceptions.NoMutantException;
import com.meli.mutants.dtos.MutantDTO;
import com.meli.mutants.dtos.MutantStatDTO;
import com.meli.mutants.services.MutantService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = MutantController.class)
public class MutantControllerTest {

    private static final Long MUTANT_AMOUNT = 40L;
    private static final Long HUMAN_AMOUNT = 100L;
    private static final Double RATIO = 0.4;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MutantService mutantService;

    @InjectMocks
    private MutantController mutantController;

    @Test
    void isMutantOkTest() throws Exception {
        Mockito.when(mutantService.isMutant(Mockito.anyList())).thenReturn(true);
        MutantDTO mutantDTO = new MutantDTO();
        mutantDTO.setDna(mockMutantAdn());

        mockMvc.perform(post("/mutant").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(toJson(mutantDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Mutant")));
    }

    @Test
    void isMutantForbiddenInvalidAdnLetterExceptionTest() throws Exception {
        Mockito.when(mutantService.isMutant(Mockito.anyList()))
                .thenThrow(new InvalidAdnLetterException("Error InvalidSizeException"));
        MutantDTO mutantDTO = new MutantDTO();
        mutantDTO.setDna(mockMutantAdn());

        mockMvc.perform(post("/mutant").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(toJson(mutantDTO)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$", is("Error InvalidSizeException")));
    }

    @Test
    void isMutantForbiddenInvalidSizeExceptionTest() throws Exception {
        Mockito.when(mutantService.isMutant(Mockito.anyList()))
                .thenThrow(new InvalidSizeException("Error InvalidSizeException"));
        MutantDTO mutantDTO = new MutantDTO();
        mutantDTO.setDna(mockMutantAdn());

        mockMvc.perform(post("/mutant").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(toJson(mutantDTO)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$", is("Error InvalidSizeException")));
    }

    @Test
    void isMutantForbiddenNoMutantException() throws Exception {
        Mockito.when(mutantService.isMutant(Mockito.anyList()))
                .thenThrow(new NoMutantException("Error NoMutantException"));
        MutantDTO mutantDTO = new MutantDTO();
        mutantDTO.setDna(mockMutantAdn());

        mockMvc.perform(post("/mutant").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(toJson(mutantDTO)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$", is("Error NoMutantException")));
    }

    @Test
    void getStatsOkTest() throws Exception {
        MutantStatDTO mutantStatDTO = new MutantStatDTO(MUTANT_AMOUNT, HUMAN_AMOUNT, RATIO);
        Mockito.when(mutantService.getMutantsStat()).thenReturn(mutantStatDTO);
        MutantDTO mutantDTO = new MutantDTO();
        mutantDTO.setDna(mockMutantAdn());

        mockMvc.perform(get("/stats").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna", is(MUTANT_AMOUNT.intValue())))
                .andExpect(jsonPath("$.count_human_dna", is(HUMAN_AMOUNT.intValue())))
                .andExpect(jsonPath("$.ratio", is(RATIO)))
        ;
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

    private String toJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

}