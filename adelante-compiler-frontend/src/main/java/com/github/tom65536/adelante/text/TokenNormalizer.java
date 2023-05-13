package com.github.tom65536.adelante.text;

import java.text.Normalizer;
import java.util.regex.Pattern;


/**
 * Contains routines for normalizing token representations.
 */
public final class TokenNormalizer {

    /**
     * Matches sequences of medial characters.
     */
    public static final Pattern MEDIAL_MATCHER = Pattern.compile(
        "(\\p{Zs}|\u200C|\u200D|_)+"
    );

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
    public static String normalizeIdentifier(final CharSequence img) {
        var norm = Normalizer.normalize(img, Normalizer.Form.NFKC);
        return MEDIAL_MATCHER.matcher(norm).replaceAll("_");
    }
}
