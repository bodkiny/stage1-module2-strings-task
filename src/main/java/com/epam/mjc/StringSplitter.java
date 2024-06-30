package com.epam.mjc;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringSplitter {

    /**
     * Splits given string applying all delimeters to it. Keeps order of result substrings as in source string.
     *
     * @param source source string
     * @param delimiters collection of delimiter strings
     * @return List of substrings
     */
    public List<String> splitByDelimiters(String source, Collection<String> delimiters) {
        StringBuilder sb = new StringBuilder("[");
        for (String delimiter : delimiters) {
            sb.append(delimiter);
        }
        sb.append("]");
        String regexp = sb.toString();

        String[] split = source.split(regexp);
        return Stream.of(split)
                .filter(str -> !str.isBlank())
                .collect(Collectors.toList());
    }
}
