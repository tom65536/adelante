package com.github.tom65536.adelante.message;

/**
 * Enumeration of categories of compiler messages.
 */
public enum CompilerMessageCategory {
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
