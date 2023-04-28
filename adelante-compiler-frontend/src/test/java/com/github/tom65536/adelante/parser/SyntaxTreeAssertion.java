package com.github.tom65536.adelante.parser;

import java.util.List;
/**
 * Assertion on an AST node and its child nodes.
 */
public class SyntaxTreeAssertion {

    /**
     * The expected node class.
     *
     */
    private String kind;

    /**
     * List of child nodes.
     */
    private List<SyntaxTreeAssertion> children;

    /**
     * List of tokens.
     */
    private List<TokenAssertion> tokens;

    /**
     * Get the expected node kind.
     * 
     * @return the node kind
     */
    public String getKind() {
        return kind;
    }

    /**
     * Get the expected list of child nodes.
     * 
     * @return the child nodes.
     */
    public List<SyntaxTreeAssertion> getChildren() {
        return children;
    }

    /**
     * Get the list of expected tokens.
     * 
     * @return list of tokens.
     */
    public List<TokenAssertion> getTokens() {
        return tokens;
    }


    public void check(final Node node) {
        assert kind.equals(AdelanteParserTreeConstants.jjtNodeName[node.getId()]) :
        "Node of kind " +
        AdelanteParserTreeConstants.jjtNodeName[node.getId()] +
        " found but expected was " + kind;

        if (children != null) {
            assert children.size() == node.jjtGetNumChildren() :
            Integer.toString(node.jjtGetNumChildren()) +
            " children found, expected are " +
            children.size();

            int i = 0;
            for (var child: children) {
                child.check(node.jjtGetChild(i++));     
            }
        }

        if (tokens != null) {
            Token tok = ((SimpleNode) node).jjtGetFirstToken();
            final Token last_token = ((SimpleNode) node).jjtGetLastToken();
            for(var tokassert : tokens) {
                tokassert.check(tok);
                tok = (tok != last_token)? tok.next : null;
            }
            assert tok == null :
            "Extraneous token '" +
            tok + " found.";
        }

    }
}
