package com.astar.java.library.pojo;

import java.util.List;
import java.util.regex.Pattern;

//TODO INSERT THIS LATER
public class StringRule {

    private Length length;
    private Pattern pattern;
    private CharacterRules characterRules;
    private WhitespaceRules whitespaceRules;
    private CasingRules casingRules;
    private WordRules wordRules;
    private Boolean allowUnicode;
    private Boolean allowEmoji;

    public Length getLength() {
        return length;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public CharacterRules getCharacterRules() {
        return characterRules;
    }

    public WhitespaceRules getWhitespaceRules() {
        return whitespaceRules;
    }

    public CasingRules getCasingRules() {
        return casingRules;
    }

    public WordRules getWordRules() {
        return wordRules;
    }

    public Boolean getAllowUnicode() {
        return allowUnicode;
    }

    public Boolean getAllowEmoji() {
        return allowEmoji;
    }

    public static class Length {
        private Integer min;
        private Integer max;

        public Length() {
        }

        public Length(Integer min, Integer max) {
            this.min = min;
            this.max = max;
        }

        public Integer getMin() {
            return min;
        }

        public Integer getMax() {
            return max;
        }
    }

    public static class CharacterRules {
        private Character[] allowed;
        private Character[] disallowed;

        public CharacterRules() {
        }

        public CharacterRules(Character[] allowed, Character[] disallowed) {
            this.allowed = allowed;
            this.disallowed = disallowed;
        }

        public Character[] getAllowed() {
            return allowed;
        }

        public Character[] getDisallowed() {
            return disallowed;
        }
    }

    public static class WhitespaceRules {
        private Boolean allowSpaces;
        private Boolean allowTrim;

        public WhitespaceRules() {
        }

        public WhitespaceRules(Boolean allowSpaces, Boolean allowTrim) {
            this.allowSpaces = allowSpaces;
            this.allowTrim = allowTrim;
        }

        public Boolean getAllowSpaces() {
            return allowSpaces;
        }

        public Boolean getAllowTrim() {
            return allowTrim;
        }
    }

    public static class CasingRules {
        private Boolean allowUppercase;
        private Boolean allowLowercase;

        public CasingRules() {
        }

        public CasingRules(Boolean allowUppercase, Boolean allowLowercase) {
            this.allowUppercase = allowUppercase;
            this.allowLowercase = allowLowercase;
        }

        public Boolean getAllowUppercase() {
            return allowUppercase;
        }

        public Boolean getAllowLowercase() {
            return allowLowercase;
        }
    }

    public static class WordRules {
        private List<String> disallowedWords;

        public WordRules() {
        }

        public WordRules(List<String> disallowedWords) {
            this.disallowedWords = disallowedWords;
        }

        public List<String> getDisallowedWords() {
            return disallowedWords;
        }
    }
}
