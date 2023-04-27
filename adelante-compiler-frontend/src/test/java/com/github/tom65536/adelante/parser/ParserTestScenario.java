package com.github.tom65536.adelante.parser;

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
}
