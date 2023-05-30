package com.github.tom65536.adelante.message;

import java.util.Locale;

/**
 * A message displayed to the user.
 */
public interface Message {
    /**
     * Return a text representation for the given locale.
     *
     * @param locale the locale
     * @return a formatted message
     */
    String toString(Locale locale);

    /**
     * Get the severity of this message.
     *
     * @return the message severity.
     */
    MessageSeverity getSeverity();

    /**
     * Get the category of this message.
     *
     * @return the message category.
     */
    MessageCategory getCategory();

    /**
     * 
     */
    default String toString() {
        return toString(Locale.ENGLISH);
    }
}