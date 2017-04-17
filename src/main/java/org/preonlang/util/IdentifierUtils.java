package org.preonlang.util;

public class IdentifierUtils {
    public static String getTargetName(String preonName) {
        // + - * / & | > < ^ = , %
        // P M S D A O G L K E C Q
        return preonName
            .replace('+', 'P')
            .replace('-', 'M')
            .replace('*', 'S')
            .replace('/', 'D')
            .replace('&', 'A')
            .replace('|', 'O')
            .replace('>', 'G')
            .replace('<', 'L')
            .replace('^', 'K')
            .replace('=', 'E')
            .replace(',', 'C')
            .replace('%', 'Q');
    }
}
