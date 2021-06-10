package com.meli.mutants.services.impl;

import com.meli.mutants.businessExceptions.InvalidAdnLetterException;
import com.meli.mutants.businessExceptions.InvalidSizeException;
import com.meli.mutants.businessExceptions.NoMutantException;
import com.meli.mutants.dtos.MutantHelper;
import com.meli.mutants.dtos.MutantStatDTO;
import com.meli.mutants.entities.Mutant;
import com.meli.mutants.repositories.MutantRepository;
import com.meli.mutants.services.MutantService;
import com.meli.mutants.utils.MutantUtil;
import com.meli.mutants.utils.SearchAction;
import com.meli.mutants.utils.ValidateMutantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.meli.mutants.utils.Constants.AMOUNT_SIBLINGS;
import static com.meli.mutants.utils.Constants.MINIMAL_TO_BE_MUTANT;

@Service
public class MutantServiceImpl implements MutantService {

    private static final Logger logger = LoggerFactory.getLogger(MutantServiceImpl.class);

    private final MutantRepository mutantRepository;

    public MutantServiceImpl(MutantRepository mutantRepository) {
        this.mutantRepository = mutantRepository;
    }

    @Override
    @Cacheable("stats")
    public MutantStatDTO getMutantsStat() {
        Long mutants = mutantRepository.countByMutant(Boolean.TRUE);
        Long humans = mutantRepository.countByMutant(Boolean.FALSE);
        return new MutantStatDTO(mutants, humans, (double) mutants / humans);
    }

    @Override
    public boolean isMutant(List<String> adn) throws InvalidSizeException, InvalidAdnLetterException, NoMutantException {
        logger.debug("Inside of MutantServiceImpl, start isMutant");
        ValidateMutantUtil.validateSize(adn);
        String adnString = String.join("", adn);
        MutantHelper mutantHelper;
        Optional<Mutant> currentMutant = mutantRepository.findFirstByAdn(adnString);
        if (currentMutant.isPresent()) {
            mutantHelper = new MutantHelper(currentMutant.get().isMutant());
        } else {
            mutantHelper = validateADN(adnString, adn.size());
            createMutant(mutantHelper);
        }
        ValidateMutantUtil.validateResult(mutantHelper.isMutant());
        logger.debug("Inside of MutantServiceImpl, end isMutant");
        return true;
    }

    @CacheEvict(value = "stats", allEntries = true)
    private void createMutant(MutantHelper mutantHelper) {
        mutantRepository.save(new Mutant(mutantHelper.getAdn(), LocalDate.now(), mutantHelper.isMutant()));
    }


    private MutantHelper validateADN(String adnString, Integer rowSize) throws InvalidSizeException, InvalidAdnLetterException {
        logger.debug("Inside of MutantServiceImpl, start validateADN");
        List<Character> adnChar = MutantUtil.buildAdnChar(adnString);
        ValidateMutantUtil.validateLetter(adnChar);
        MutantHelper mutantHelper = new MutantHelper(adnChar.size(), rowSize, AMOUNT_SIBLINGS, adnString);
        for (int index = 0; index < adnChar.size(); index++) {
            mutantHelper.setIndex(index);
            if (validateThroughSearchActions(mutantHelper, adnChar)) {
                logger.debug("Inside of MutantServiceImpl, end validateADN");
                mutantHelper.setMutant(true);
                return mutantHelper;
            }
        }
        logger.debug("Inside of MutantServiceImpl, end validateADN");
        return mutantHelper;
    }

    private boolean validateThroughSearchActions(MutantHelper mutantHelper, List<Character> adnChar) {
        for (SearchAction searchAction : SearchAction.values()) {
            if (!searchAction.getHasNotEnoughSiblings().test(mutantHelper)
                    && hasMutantSequence(mutantHelper, adnChar, searchAction)) {
                mutantHelper.plusOneAdnMutantSequence();
                if (mutantHelper.getAdnMutantSequence() >= MINIMAL_TO_BE_MUTANT) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasMutantSequence(MutantHelper mutantHelper, List<Character> adnChar, SearchAction searchAction) {
        int index = mutantHelper.getIndex();
        for (int siblingIndex = 1; siblingIndex <= mutantHelper.getAmountSiblings(); siblingIndex++) {
            int siblingPosition = searchAction.getOperation().getSiblingPosition(siblingIndex, mutantHelper);
            if (!adnChar.get(index).equals(adnChar.get(siblingPosition))) {
                return false;
            }
        }
        return true;
    }

}
