package com.github.tom65536.adelante.parser;

/**
 * Assertion on a token.
 */
public class TokenAssertion {
    /**
     * Token kind.
     */
    private String kind;

    /**
     * Token image.
     */
    private String image;

    /**
     * Check a token against this exception.
     * 
     * @param token the token
     */
    public void check(final Token token) {
        if (kind != null) {
            assert AdelanteParserConstants.tokenImage[token.kind] == kind;
        }

        if (image != null) {
            assert token.image == image;
        }
    }
}