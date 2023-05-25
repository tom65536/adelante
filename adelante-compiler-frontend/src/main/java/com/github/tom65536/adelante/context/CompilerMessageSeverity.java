
package com.github.tom65536.adelante.context;

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
 * Enumeration of severities of compiler messages.
 */
public enum CompilerMessageSeverity {
    /**
     * Reports a condition of the compiler
     * or the code that the user
     * may find interesting but
     * may safely be ignored.
     */
    INFO,

    /**
     * Reports a condition about the code or the
     * configuration that should typically have
     * no inpact on correctness but may indicate
     * potential improvenents.
     */
    HINT,

    /**
     * Reports a condition of the code or the configuration
     * that may be related to potentially unintended
     * behavior.
     */
    WARNING,

    /**
     * Reports a condition of the code or the configuration
     * that compromises the correctness of the
     * compilation process.
     *
     * The compiler may interrupt the
     * compilation process at any point after
     * an error has been issued. No assumptions
     * should be implied about the code generated
     * up to that point.
     */
    ERROR,

    /**
     * Reports a condition of the compiler itself
     * (not the code or the configuration) that may
     * affect the correctness of the compilation process.
     *
     * The compiler may interrupt the
     * compilation process at any point after
     * an internal error has been issued. No assumptions
     * should be implied about the code generated
     * up to that point.
     */
    INTERNAL;
}
