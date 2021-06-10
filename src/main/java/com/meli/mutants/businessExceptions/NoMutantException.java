package com.meli.mutants.businessExceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NoMutantException extends RuntimeException {

    private String message;

}
