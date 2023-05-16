
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

/**
 * Kind of declaration in the Symbol table.
 */
public enum DeclarationType {
    /**
     * Declaration of an abstract type.
     */
    AbstractType(false),

    /**
     * Declaration of a type synonym.
     */
    TypeSynonym(false),

    /**
     * Definition of a synonym.
     */
    Synonym(false),

    /**
     * Declaration of a procedure or operator.
     */
    Subroutine(true),

    /**
     * Declaration of a variable or constant.
     */
    Object(false),

    /**
     * Definition of a measure.
     */
    Measure(false),

    /**
     * Definition of a protocol.
     */
    Protocol(false),

    /**
     * Definition of a formal type parameter.
     */
    FormalTypeParam(false),

    /**
     * Definition of a formal measure.
     */
    FormalMeasureParam(false),

    /**
     * Definition of a formal parameter.
     */
    FormalParam(false);

    /**
     * Indicates whether a declaration of this kind
     * can be overloaded.
     */
    private final boolean overloadable;

    /**
     * Initialize an enum value.
     *
     * @param canOverload indicates whether declarations of
     *  that kind can be overloaded
     */
    DeclarationType(
        final boolean canOverload
    ) {
        this.overloadable = canOverload;
    }

    /**
     * Indicate whether this kind of declaration
     * can be overloaded.
     *
     * @return true if the declaration of that
     *  type is overloadable.
     */
    public boolean isOverloadable() {
        return overloadable;
    }
}
