
package com.github.tom65536.adelante.symboltable;

import com.github.tom65536.adelante.parser.Token;

/**
 * Symbol rable implementation.
 */
public class SymbolTable {
    /**
     * Entry in a symbol table.
     */
    public static class Entry {
        /**
         * Initialize a new entry,
         * 
         * @param aToken the defining token.
         */
        public Entry(final Token aToken) {
            this.token = aToken;
        }
    }

    /**
     * Initialize a new symbol table.
     * 
     */
    public SymbolTable() {

    }

    }

}