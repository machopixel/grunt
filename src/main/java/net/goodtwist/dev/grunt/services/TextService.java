package net.goodtwist.dev.grunt.services;

/**
 * Created by Diego on 8/16/2015.
 */
public class TextService {

    static public boolean isCharOnly(String name) {
        char[] chars = name.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }
}
