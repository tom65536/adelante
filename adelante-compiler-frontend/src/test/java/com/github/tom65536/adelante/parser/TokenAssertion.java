package com.github.tom65536.adelante.parser;

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
