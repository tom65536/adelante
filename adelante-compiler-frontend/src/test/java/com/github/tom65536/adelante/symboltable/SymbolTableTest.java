
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

import org.testng.annotations.Test;
import static org.testng.Assert.*;

import com.github.tom65536.adelante.parser.AdelanteParserConstants;
import com.github.tom65536.adelante.parser.Token;
import com.github.tom65536.adelante.symboltable.DeclarationType;
import com.github.tom65536.adelante.symboltable.SymbolTable;

/**
 * Tests for the {@link SymbolTable} class.
 */
public class SymbolTableTest
    implements AdelanteParserConstants
{
    /**
     * Add some symbols and check the lookup.
     */
    @Test
    public void testAddAndLookup() {
        final SymbolTable root = new SymbolTable();
        final SymbolTable scope = root.createChild();

        root.add(
            Token.newToken(IDENTIFIER, "Foo"),
            DeclarationType.AbstractType
        );

        root.add(
            Token.newToken(IDENTIFIER, "add"),
            DeclarationType.Subroutine
        );

        scope.add(
            Token.newToken(IDENTIFIER, "foo"),
            DeclarationType.Object   
        );

        scope.add(
            Token.newToken(IDENTIFIER, "add"),
            DeclarationType.Subroutine
        );

        assertFalse(root.canAdd(
            Token.newToken(IDENTIFIER, "Foo"),
            DeclarationType.Synonym)
        );
    }

}
