
package com.github.tom65536.adelante.symboltable;

import java.util.HashMap;
import java.util.Map;

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
         * The packet name at the defining position.
         */
        private final String packetName;

        /**
         * Overloading definitions.
         *
         * Must be null unless this entry refers to
         * procedures or operators.
         */
        private Entry next = null;

        /**
         * Initialize a new entry.
         *
         * @param aToken the defining token.
         * @param aPacketName the name of the defining packet or file
         */
        public Entry(final Token aToken, final String aPacketName) {
            this.token = aToken;
            this.packetName = aPacketName;
        }

        /**
         * Get the defining token.
         *
         * @return the defining token.
         */
        public Token getToken() {
            return token;
        }

        /**
         * Get the name of the defining packet.
         *
         * @return a packet or file name.
         */
        public String getPacketName() {
            return packetName;
        }

        /**
         * Get the next overloaded definition.
         *
         * @return the next overloaded definition
         */
        public Entry getNext() {
            return next;
        }
    }

    /**
     * Parent scope.
     */
    private final transient SymbolTable parent;

    /**
     * The current packet or file name.
     */
    private String packetName = null;

    /**
     * Stored symbol definitions.
     */
    private final Map<String, Entry> entries =
        new HashMap<String, Entry>();

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

    /**
     * Set the current packet name.
     *
     * @param aName the new name.
     */
    public void setPacketName(final String aName) {
        this.packetName = aName;
    }

    /**
     * Get the current packet name.
     *
     * If the packet name is not set in the current scope
     * this method recursively traverses the parent scopes.
     *
     * @return the current packet or file name.
     */
    public String getPacketName() {
        if (packetName == null) {
            if (parent != null) {
                return parent.getPacketName();
            }
        }
        return packetName;
    }
}
