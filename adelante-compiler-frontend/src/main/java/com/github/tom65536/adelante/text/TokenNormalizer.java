package com.github.tom65536.adelante.text;

/*-
 * #%L
 * adelante-compiler-frontend
 * %%
 * Copyright (C) 2023 Thomas Reiter
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */

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
