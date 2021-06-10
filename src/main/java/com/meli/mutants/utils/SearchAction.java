package com.meli.mutants.utils;

import com.meli.mutants.dtos.MutantHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Predicate;

@AllArgsConstructor
@Getter
public enum SearchAction {

    RIGHT(
            mutantHelper ->
                    mutantHelper.getIndex() % mutantHelper.getAdnRowSize() > mutantHelper.getAdnRowSize() - 1 - mutantHelper.getAmountSiblings(),
            (siblingIndex, mutantHelper) -> mutantHelper.getIndex() + siblingIndex
    ),
    BUTTON(
            mutantHelper -> mutantHelper.getIndex() + mutantHelper.getAdnRowSize() * mutantHelper.getAmountSiblings() > mutantHelper.getAdnTotalSize() - 1,
            (siblingIndex, mutantHelper) -> mutantHelper.getIndex() + (mutantHelper.getAdnRowSize() * siblingIndex)
    ),
    BUTTON_RIGHT(
            mutantHelper -> RIGHT.hasNotEnoughSiblings.test(mutantHelper) || BUTTON.hasNotEnoughSiblings.test(mutantHelper),
            (siblingIndex, mutantHelper) -> mutantHelper.getIndex() + siblingIndex + (mutantHelper.getAdnRowSize() * siblingIndex)
    ),
    BUTTON_LEFT(
            mutantHelper -> {
                int button = mutantHelper.getIndex() + (mutantHelper.getAdnRowSize() * mutantHelper.getAmountSiblings());
                return button > mutantHelper.getAdnTotalSize() ||
                        (button - mutantHelper.getAmountSiblings()) / mutantHelper.getAdnRowSize() < mutantHelper.getAmountSiblings() + 1;
            },
            (siblingIndex, mutantHelper) -> mutantHelper.getIndex() - siblingIndex + (mutantHelper.getAdnRowSize() * siblingIndex)
    );

    Predicate<MutantHelper> hasNotEnoughSiblings;
    SiblingOperation operation;

}
