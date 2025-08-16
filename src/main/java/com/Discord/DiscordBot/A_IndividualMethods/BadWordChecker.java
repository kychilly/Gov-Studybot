package com.Discord.DiscordBot.A_IndividualMethods;

import java.util.Arrays;

// A GPT generated class(for the sole purpose of maximum efficiency). If anyone is reading this, I take 0 credit for making this/
public class BadWordChecker {
    // Trie node optimized for ASCII characters
    private static class TrieNode {
        private static final int ASCII_RANGE = 128; // covers basic ASCII
        TrieNode[] children = new TrieNode[ASCII_RANGE];
        boolean isWord = false;

        TrieNode getChild(char c) {
            if (c < ASCII_RANGE) return children[c];
            return null;
        }

        void setChild(char c, TrieNode node) {
            if (c < ASCII_RANGE) children[c] = node;
        }
    }

    private static final TrieNode root = new TrieNode();
    private static final int MAX_WORD_LENGTH = 10; // Length of longest bad word

    // Normalization table (faster than HashMap)
    private static final char[] NORMALIZATION_TABLE = new char[Character.MAX_VALUE + 1];

    static {
        initializeNormalizationTable();
        initializeBadWords();
    }

    private static void initializeNormalizationTable() {
        // Initialize with identity mapping (lowercase)
        for (int i = 0; i < NORMALIZATION_TABLE.length; i++) {
            NORMALIZATION_TABLE[i] = Character.toLowerCase((char)i);
        }

        // Leetspeak mappings
        NORMALIZATION_TABLE['4'] = 'a';
        NORMALIZATION_TABLE['@'] = 'a';
        NORMALIZATION_TABLE['1'] = 'i';
        NORMALIZATION_TABLE['!'] = 'i';
        NORMALIZATION_TABLE['3'] = 'e';
        NORMALIZATION_TABLE['0'] = 'o';
        NORMALIZATION_TABLE['$'] = 's';
        NORMALIZATION_TABLE['5'] = 's';
        NORMALIZATION_TABLE['7'] = 't';

        // Cyrillic mappings
        NORMALIZATION_TABLE['а'] = 'a'; // Cyrillic a
        NORMALIZATION_TABLE['А'] = 'a';
        NORMALIZATION_TABLE['е'] = 'e'; // Cyrillic e
        NORMALIZATION_TABLE['Е'] = 'e';
        NORMALIZATION_TABLE['о'] = 'o'; // Cyrillic o
        NORMALIZATION_TABLE['О'] = 'o';
        NORMALIZATION_TABLE['с'] = 'c'; // Cyrillic c
        NORMALIZATION_TABLE['С'] = 'c';
        NORMALIZATION_TABLE['і'] = 'i'; // Cyrillic i
        NORMALIZATION_TABLE['І'] = 'i';
        NORMALIZATION_TABLE['г'] = 'r'; // Cyrillic g to r
        NORMALIZATION_TABLE['Г'] = 'r';
        NORMALIZATION_TABLE['к'] = 'k'; // Cyrillic k
        NORMALIZATION_TABLE['К'] = 'k';
        NORMALIZATION_TABLE['ո'] = 'n'; // Additional n

        // Add more mappings as needed
    }

    private static void initializeBadWords() {
        String[] badWords = {
                "nigger", "nigga", "fag", "faggot", "kike", "chink", "wop", "spic",
                "cunt", "bitch", "fuck", "shit", "retard", "moron"
                // Add more words as needed
        };

        for (String word : badWords) {
            addWord(word);
        }
    }

    private static void addWord(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            char normalized = NORMALIZATION_TABLE[c];
            TrieNode child = node.getChild(normalized);
            if (child == null) {
                child = new TrieNode();
                node.setChild(normalized, child);
            }
            node = child;
        }
        node.isWord = true;
    }

    /**
     * Checks if the input contains any bad words after normalization
     * @param input The string to check
     * @return true if a bad word is found, false otherwise
     */
    public static boolean hasAReallyBadWord(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        input = input.trim();
        int n = input.length();

        // Check all possible substrings
        for (int i = 0; i < n; i++) {
            // Skip non-word characters at start
            if (!isWordChar(input.charAt(i))) continue;

            TrieNode node = root;
            int charsMatched = 0;

            for (int j = i; j < n && charsMatched < MAX_WORD_LENGTH; j++) {
                char c = input.charAt(j);

                // Skip non-word characters within potential matches
                if (!isWordChar(c)) continue;

                c = NORMALIZATION_TABLE[c];
                node = node.getChild(c);
                charsMatched++;

                if (node == null) break;
                if (node.isWord) {
                    // Check if we're at a word boundary
                    if (j == n-1 || !isWordChar(input.charAt(j+1))) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Determines if a character should be considered part of a word
     * @param c The character to check
     * @return true if it's a word character, false otherwise
     */
    private static boolean isWordChar(char c) {
        // Include letters, digits, and common word-connecting characters
        return Character.isLetterOrDigit(c) || c == '\'' || c == '-';
    }

    /**
     * Normalizes a character (lowercase + leetspeak/cyrillic conversion)
     * @param c The character to normalize
     * @return The normalized character
     */
    private static char normalizeChar(char c) {
        return NORMALIZATION_TABLE[c];
    }

    /**
     * Utility method to check multiple strings efficiently
     * @param inputs Array of strings to check
     * @return true if any string contains a bad word
     */
    public static boolean containsAnyBadWords(String... inputs) {
        if (inputs == null) return false;
        return Arrays.stream(inputs).anyMatch(BadWordChecker::hasAReallyBadWord);
    }
}