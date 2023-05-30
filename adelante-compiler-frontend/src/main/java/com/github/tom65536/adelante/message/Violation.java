package com.github.tom65536.adelante.message;

import java.util.Locale;

import com.github.tom65536.adelante.parser.Token;;
/**
 * A message informing the user about the violation of a rule.
 */
public class Violation implements Message {

    /**
     * the violated rule.
     */
    private final Rule rule;

    /**
     * the actual severity.
     */
    private final MessageSeverity severity;

    /**
     * the name of the offending packet.
     */
    private final String packetName;

    /**
     * the first offending token.
     */
    private final Token startToken;

    /**
     * the last offending token.
     */
    private final Token endToken;

    /**
     * list of parameters.
     */
    private final String[] params;

    /**
     * Initialize a new instance of the {@link Violation} class.
     *
     * @param aRule the violated rule
     * @param aSeverity the actual message severity
     * @param aPacketName the name of the offending packet
     * @param aStartToken the first offending token
     * @param anEndToken the last offending token
     * @param aParameterList list of parameters for customizing the message
     */
    public Violation(
        final Rule aRule,
        final MessageSeverity aSeverity,
        final String aPacketName,
        final Token aStartToken,
        final Token anEndToken,
        final String... aParameterList) {
        rule = aRule;
        severity = aSeverity;
        packetName = aPacketName;
        startToken = aStartToken;
        endToken = anEndToken;
        params = aParameterList;
    }

    @Override
    public MessageCategory getCategory() {
        return rule.getCategory();
    }

    @Override
    public MessageSeverity getSeverity() {
        return severity;
    }

    @Override
    public String toString(final Locale locale) {
        
    }
}