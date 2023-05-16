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

    /**
     * Check the assertion.
     * 
     * @param node the node ro compare with
     */
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
