
package com.github.tom65536.adelante.text;

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