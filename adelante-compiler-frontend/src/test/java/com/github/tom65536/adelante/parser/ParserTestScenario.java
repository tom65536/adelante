package com.github.tom65536.adelante.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

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
     * Get the syntax tree asdertion.
     * 
     * @return the syntax tree assertion
     */
    public SyntaxTreeAssertion getAst() {
        return ast;
    }

    /**
     * Check the scenario for a given parser.
     * 
     * @param parser the parser to be checked.
     */
    public void check(final AdelanteParser parser) throws IOException {
        final var raw = new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8));
        final var reader = new UnicodeReader(raw, "\n");
        parser.ReInit(reader);

        try {
            var method = parser.getClass().getMethod(production);
            method.invoke(parser);
        } catch(NoSuchMethodException | IllegalArgumentException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        } catch(InvocationTargetException ex) {
            if (ex.getCause() instanceof ParseException) {
                assert ast == null :
                    ex.getCause().getMessage();
                return;
            } else {
                throw new RuntimeException(ex);
            }
        }
        final Node node = parser.jjtree.rootNode();
        assert ast != null :
            "expected to fail";
        try {
            ast.check(node);
        } catch (AssertionError err) {
            throw new AssertionError("[" + title + "]" + err.getMessage(), err);
        }
    }

    @Override
    public String toString() {
        if(title == null) {
            return "@" + production;
        }
        return title;
    }

    /**
     * Load test scenarios from YAML file.
     * 
     * @param url the URL to be read from
     * @throws IOException if the YAML file cannot be read
     */
    public static List<ParserTestScenario> fromYamlUrl(final URL url) throws IOException {
        final YAMLFactory yaml = new YAMLFactory();
        final ObjectMapper mapper = new ObjectMapper(yaml);

        return mapper.readValues(yaml.createParser(url), ParserTestScenario.class)
            .readAll();
    }
}
