package com.github.tom65536.adelante.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.input.BOMInputStream;
import org.apache.commons.io.ByteOrderMark;


/**
 * Implementation of an {@link InputStreamReader} respecting the BOM.
 */
public class UnicodeReader extends Reader {

    private final transient BOMInputStream in;
    private transient InputStreamReader delegate_reader;
    
    public UnicodeReader(final InputStream raw) {
        super(raw);
        this.in = new BOMInputStream(raw);
    }

    private Reader ensure_delegate() throws IOException {
        if(delegate_reader == null) {
            synchronized(lock) {
                if(delegate_reader == null) {
                    final Charset charset;
                    if(!in.hasBOM()) charset = StandardCharsets.UTF_8;
                    else if(in.hasBOM(ByteOrderMark.UTF_8)) charset = StandardCharsets.UTF_8;
                    else if(in.hasBOM(ByteOrderMark.UTF_16LE)) charset = StandardCharsets.UTF_16LE;
                    else if(in.hasBOM(ByteOrderMark.UTF_16BE)) charset = StandardCharsets.UTF_16BE;
                    else { throw new IOException("The charset of the input file is not supported.");}
                    delegate_reader = new InputStreamReader(in, charset);
                }
            }
        }
        return delegate_reader;
    }

    public void close() throws IOException {
        synchronized(lock) {
            if(delegate_reader != null) {
                delegate_reader.close();
            }
        }
    }

    public int read(char[] cbuf,
    int off,
    int len) throws IOException {
        return ensure_delegate().read(
            cbuf, off, len
        );
    }
}
