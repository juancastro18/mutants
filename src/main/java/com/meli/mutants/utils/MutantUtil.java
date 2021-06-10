package com.meli.mutants.utils;

import java.util.List;
import java.util.stream.Collectors;

public class MutantUtil {

    public static List<Character> buildAdnChar(String adn) {
        return adn.toUpperCase().chars()
                .mapToObj(e -> (char) e)
                .collect(Collectors.toList());
    }

}
