package com.github.tom65536.adelante.parser;

import java.io.InputStream;
import org.apache.commons.io.input.BOMInputStream;


/**
 * Implementation of a {@link CharStream} respecting the BOM.
 */
public class UnicodeCharStream implements CharStream {

    private final transient BOMInputStream in;
    
    public UnicodeCharStream(final InputStream raw) {
        this.in = new BOMInputStream(raw);

    }
   
    
}
