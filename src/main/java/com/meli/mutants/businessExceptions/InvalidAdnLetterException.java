package com.meli.mutants.businessExceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InvalidAdnLetterException extends RuntimeException {

    private String message;

}
