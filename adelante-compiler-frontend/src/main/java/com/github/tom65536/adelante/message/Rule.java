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
 * A rule that may be violated by some offending code.
 */
public interface Rule {
    /**
     * Get a unique rule ID.
     *
     * @return the rule ID.
     */
    String getId();

    /**
     * Get the rule category.
     *
     * @return the rule category
     */
    MessageCategory getCategory();

    /**
     * Get the rule default severity.
     *
     * The default severit may be overwritten
     * by some cobfiguration.
     *
     * @return the default severity.
     */
    MessageSeverity getDefaultSeverity();
}
