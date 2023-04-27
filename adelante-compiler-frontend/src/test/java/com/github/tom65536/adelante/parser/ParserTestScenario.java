package com.github.tom65536.adelante.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;

import javax.management.RuntimeErrorException;

/**
 * Test scenario for testing the parser.
 * 
 * This class provides
 * <ul>
 * <li>a title,</li>
 * <li>the name of the relevant production,</li>
 * <li>the source to be passed to the parser, and</li>
 * <li>the expected AST</li>
 * </ul>
 * 
 */
public class ParserTestScenario {
    /**
     * Briefly describes the test scenario in human-readable prosa.
     */
    private String title;

    /**
     * The method name of the production of the parser.
     */
    private String production;

    /**
     * The source passed ro the parser.
     */
    private String source;

    /**
     * Assertion about the AST.
     * If the assertion is {@code null}
     * the parser is expected to fail.
     */
    private SyntaxTreeAssertion ast;
    
    /**
     * Get the title.
     * 
     * @return the title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get the name of the production.
     * 
     * @return the name of the production.
     */
    public String getProduction() {
        return production;
    }

    /**
     * Get the source being passed to the parser.
     * 
     * @return the source code being passed to the parser
     */
    public String getSource() {
        return source;
    }

    /**
     * Check the scenario for a given parser.
     * 
     * @param parser the parser to be checked.
     */
    public void check(final AdelanteParser parser) throws IOException {
        final var raw = new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8));
        final var reader = new UnicodeReader(raw);
        parser.ReInit(reader);

        try {
            var method = parser.getClass().getMethod(production);
            method.invoke(parser);
        } catch(NoSuchMethodException | IllegalArgumentException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        } catch(InvocationTargetException ex) {
            if (ex.getCause() instanceof ParseException) {
                assert ast == null;
            } else {
                throw new RuntimeException(ex);
            }
        }
        final Node node = parser.jjtree.rootNode();

    }
}
