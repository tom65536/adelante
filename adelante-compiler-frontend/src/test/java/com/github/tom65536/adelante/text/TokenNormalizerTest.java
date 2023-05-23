
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

import static org.testng.Assert.*;

/**
 * Test cases for the {@link TokenNormalizer} class.
 */
public class TokenNormalizerTest {

    /**
     * Test the {@link TokenNormalizer#normalizeIdentifier}
     * method.
     */
    public void testNormalizeIdentifier() {
        final String img = "a _b\u200CDz\u030Cemper";
        final String expected = "a_b_\u01C5emper";
        final String actual = TokenNormalizer.normalizeIdentifier(img);
        assertEquals(expected, actual);
    }
}
