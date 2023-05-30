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
 * Enumeration of builtin rules.
 */
public enum BuiltinRule implements Rule {
    /**
     * Parse exceptions.
     *
     * The offending code does not comply with
     * the gramnar definition.
     */
    PAR(MessageCategory.ERROR_PRONE, MessageSeverity.ERROR);

    /**
     * The message category.
     */
    private final MessageCategory category;

    /**
     * The default severity dor this rule.
     */
    private final MessageSeverity defaultSeverity;

    BuiltinRule(
        final MessageCategory aCategory,
        final MessageSeverity aSeverity
    ) {
        category = aCategory;
        defaultSeverity = aSeverity;
    }

    @Override
    public String getId() {
        return name();
    }

    @Override
    public MessageCategory getCategory() {
        return category;
    }

    @Override
    public MessageSeverity getDefaultSeverity() {
        return defaultSeverity;
    }
}
