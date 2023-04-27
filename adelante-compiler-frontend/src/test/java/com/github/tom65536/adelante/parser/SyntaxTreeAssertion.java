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

    public void check(final Node node) {
        assert kind == AdelanteParserTreeConstants.jjtNodeName[node.getId()];

        if (children != null) {
            assert children.size() == node.jjtGetNumChildren();

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
            assert tok == null;
        }

    }
}
