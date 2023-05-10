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
     * Get the expected token kind.
     * 
     * @return the token kind
     */
    public String getKind() {
        return kind;
    }

    /**
     * Get the token image.
     * 
     * @return token image
     */
    public String getImage() {
        return image;
    }

    /**
     * Check a token against this exception.
     * 
     * @param token the token
     */
    public void check(final Token token) {
        if (kind != null) {
            assert AdelanteParserConstants.tokenImage[token.kind].equals(kind) :
               "Token kind was " +
               AdelanteParserConstants.tokenImage[token.kind] +
               " but expected was " + kind;
        }

        if (image != null) {
            assert image.equals(token.image) :
            "Token image was '" +
            token.image +
            "' but expected was '" + image;
        }
    }
}