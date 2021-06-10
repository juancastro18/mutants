package com.meli.mutants.businessExceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InvalidSizeException extends RuntimeException {

    private String message;

}
