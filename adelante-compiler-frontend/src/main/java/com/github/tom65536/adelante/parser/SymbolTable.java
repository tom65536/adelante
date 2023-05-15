
package com.github.tom65536.adelante.symboltable;

import com.github.tom65536.adelante.parser.Token;

/**
 * Symbol table implementation.
 */
public class SymbolTable {

    /**
     * Entry in a symbol table.
     */
    public static class Entry {
        /**
         * The token at the defining code position.
         */
        private final Token token;

        /**
         * Initialize a new entry.
         *
         * @param aToken the defining token.
         */
        public Entry(final Token aToken) {
            this.token = aToken;
        }
    }

    /**
     * Parent scope.
     */
    private final transient SymbolTable parent;

    /**
     * Initialize a new symbol table.
     *
     * The symbol table is initialized with
     * no parent.
     */
    public SymbolTable() {
        this(null);
    }

    /**
     * Initialize a new symbol table.
     *
     * @param aParent the parent scope.
     */
    public SymbolTable(final SymbolTable aParent) {
        this.parent = aParent;
    }

}
