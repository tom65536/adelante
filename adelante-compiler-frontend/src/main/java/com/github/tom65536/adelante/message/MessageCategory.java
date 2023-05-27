package com.github.tom65536.adelante.message;

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
 * Enumeration of categories of compiler messages.
 */
public enum MessageCategory {
    /**
     * Violations of recommended and essential
     * coding practice.
     */
    BAD_PRACTICE,

    /**
     * Probable bug - an apparent coding mistake
     * resulting in code that was probably not
     * what the developer intended.
     */
    ERROR_PRONE,

    /**
     * Code that is vulnerable to attacks from
     * untrusted code.
     */
    MALICIOUS_CODE,

    /**
     * Code that is not necessarily inxorrect
     * but may be inefficient.
     */
    PERFORMANCE,

    /**
     * A use of untrusted input in a way that
     * could create a remotely exploitable
     * security vulnerability.
     */
    SECURITY,

    /**
     * Code that is confusing, anomalous,
     * or written in a way that leads itself
     * to errors.
     */
    DODGY,

    /**
     * Code that indicates bad design.
     */
    DESIGN,

    /**
     * Violation of best practices for
     * providing good documentation.
     */
    DOCUMENTATION,

    /**
     * Messages that do not fall in any of the
     * above categories.
     */
    OTHER
}
