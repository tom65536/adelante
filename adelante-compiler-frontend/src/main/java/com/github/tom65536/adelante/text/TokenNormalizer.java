package com.github.tom65536.adelante.text;

import java.text.Normalizer;


/**
 * Contains routines for normalizing token representations.
 */
public final class TokenNormalizer {
    /**
     * Hide default constructor.
     */
    private TokenNormalizer() {
    }

    /**
     * Normalize an identifier.
     *
     * Identifier normalization comprises:
     * <ol>
     * <li>NFKC Unicode normalization</li>
     * <li>Medial character normalization:
     *     Each sequence of medial characters
     *     is replaced by a single underscore.
     * </li>
     * </ol>
     * @param img the token image
     * @return the nirmalized token
     */
    public static String normalizeIdentifier(final CharacterSequence img) {
        var norm = Normalizer.normalize(img, Normalizer.Form.NKFC);
        return "";
    }
}
