package com.meli.mutants.controllers;

import com.meli.mutants.businessExceptions.InvalidAdnLetterException;
import com.meli.mutants.businessExceptions.InvalidSizeException;
import com.meli.mutants.businessExceptions.NoMutantException;
import com.meli.mutants.dtos.MutantDTO;
import com.meli.mutants.dtos.MutantStatDTO;
import com.meli.mutants.services.MutantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MutantController {

    private static final Logger logger = LoggerFactory.getLogger(MutantController.class);
    private final MutantService mutantService;

    public MutantController(MutantService mutantService) {
        this.mutantService = mutantService;
    }

    @PostMapping(path = "/mutant")
    @ResponseBody
    public ResponseEntity<String> isMutant(@RequestBody MutantDTO mutantDTO)
            throws InvalidSizeException, InvalidAdnLetterException, NoMutantException {
        logger.debug("Inside MutantController, start isMutant");
        mutantService.isMutant(mutantDTO.getDna());
        logger.debug("Inside MutantController,Finish isMutant");
        return new ResponseEntity<>("Mutant", HttpStatus.OK);
    }

    @GetMapping(path = "/stats")
    @ResponseBody
    public ResponseEntity<MutantStatDTO> getMutantStat()
            throws InvalidSizeException, InvalidAdnLetterException, NoMutantException {
        logger.debug("Inside MutantController, start getMutantStat");
        MutantStatDTO mutantsStat = mutantService.getMutantsStat();
        logger.debug("Inside MutantController,Finish getMutantStat");
        return new ResponseEntity<>(mutantsStat, HttpStatus.OK);
    }

}
