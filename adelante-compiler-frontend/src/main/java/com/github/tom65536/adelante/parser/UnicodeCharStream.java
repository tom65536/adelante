package com.github.tom65536.adelante.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.input.BOMInputStream;
import org.apache.commons.io.ByteOrderMark;


/**
 * Implementation of a {@link CharStream} respecting the BOM.
 */
public class UnicodeCharStream implements CharStream {

    private final transient BOMInputStream in;
    private transient BufferedReader reader;
    
    public UnicodeCharStream(final InputStream raw) {
        this.in = new BOMInputStream(raw);
    }
   
    public char readChar() throws IOException {
        if(reader == null) {
            final Charset charset;
            if(!in.hasBOM()) charset = StandardCharsets.UTF_8;
            else if(in.hasBOM(ByteOrderMark.UTF_8)) charset = StandardCharsets.UTF_8;
            else if(in.hasBOM(ByteOrderMark.UTF_16LE)) charset = StandardCharsets.UTF_16LE;
            else if(in.hasBOM(ByteOrderMark.UTF_16BE)) charset = StandardCharsets.UTF_16BE;
            else { throw new IOException("The charset of the input file is not supported.");}
            reader = new BufferedReader(new InputStreamReader(in, charset));

        }
        final int ch = reader.read();
        if (ch < 0) {

        }
        return (char) ch;
    }
    
    public int getColumn() {
        return 0;
    }

    public int getLine() {
        return 0;
    }

    public int getBeginColumn() {
        return 0;
    }

    public int getBeginLine() {
        return 0;
    }

    public int getEndColumn() {
        return 0;
    }

    public int getEndLine() {
        return 0;
    }

    public int getTabSize() {
        return 4;
    }

    public void backup(final int amount) {

    }

    public char BeginToken() throws IOException {
        return 'x';
    }

    public String getImage() {
        return "";
    }

    public char[] GetSuffix(final int len) {
        return new char[0];
    }

    public void Done() {
        if (reader != null) {
            try {
                reader.close();
            } catch(IOException ex) {
                // ignore
            }
            reader = null;
        }
    }

    public void setTabSize( final int text_size) {
        
    }
}
