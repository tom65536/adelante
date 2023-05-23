
package com.github.tom65536.adelante.symboltable;

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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.github.tom65536.adelante.parser.Token;
import com.github.tom65536.adelante.text.TokenNormalizer;

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
         * The kind of declaration.
         */
        private final DeclarationType kind;

        /**
         * Initialize a new entry.
         *
         * @param aToken the defining token.
         * @param aPacketName the name of the defining packet or file
         * @param aKind the kind of declaration
         */
        public Entry(
            final Token aToken,
            final String aPacketName,
            final DeclarationType aKind
        ) {
            this.token = aToken;
            this.packetName = aPacketName;
            this.kind = aKind;
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

        /**
         * Get the kind of declaration.
         *
         * @return the kind of declaration.
         */
        public DeclarationType getKind() {
            return kind;
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
    protected SymbolTable(final SymbolTable aParent) {
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

    /**
     * Look up a symbol.
     *
     * @param id the normalized identifier to be looked up.
     * @return a list of all matching definitions.
     */
    public List<Entry> lookup(final String id) {
        final List<Entry> result = new LinkedList<Entry>();
        lookup(result, id);
        return result;
    }

    /**
     * Look up a token of an identifier.
     *
     * @param token the identifier token.
     * @return a list of all matching definitions.
     */
    public List<Entry> lookup(final Token token) {
        final String id = TokenNormalizer.normalizeIdentifier(token.image);
        return lookup(id);
    }

    private void lookup(final List<Entry> result, final String id) {
        final Entry existing = entries.get(id);
        if (existing != null) {
            result.add(existing);
        }

        if (parent != null) {
            parent.lookup(result, id);
        }
    }

    /**
     * Report whether a symbol can be added to the current scope.
     *
     * @param token the defining token
     * @param kind the kind of declaration to be added
     * @return true if the symbol can be added, false otherwise
     */
    public boolean canAdd(final Token token, final DeclarationType kind) {
        final List<Entry> existing = lookup(token);
        if (existing.isEmpty()) {
            return true;
        }

        if (!kind.isOverloadable()) {
            return false;
        }

        return existing.stream()
            .map(Entry::getKind)
            .allMatch(it -> it == kind);
    }

    /**
     * Add a symbol to the symbol table.
     *
     * @param token the defining token
     * @param kind the declaration kind
     * @throws IllegalArgumentException if the symbol cannot be added.
     */
    public void add(final Token token, final DeclarationType kind)
        throws IllegalArgumentException {
        final String id = TokenNormalizer.normalizeIdentifier(token.image);
        final Entry newEntry = new Entry(token, getPacketName(), kind);

        newEntry.next = entries.get(id);
        entries.put(id, newEntry);
    }

    /**
     * Get the parent table.
     *
     * @return the parent table or {@code null}.
     */
    public SymbolTable getParent() {
        return parent;
    }

    /**
     * Create a new symbol table whose parent is this table.
     *
     * @return the created child parent table
     */
    public SymbolTable createChild() {
        return new SymbolTable(this);
    }
}
