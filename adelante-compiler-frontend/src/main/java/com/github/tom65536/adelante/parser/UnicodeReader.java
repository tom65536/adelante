package com.github.tom65536.adelante.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.io.input.BOMInputStream;
import org.apache.commons.io.ByteOrderMark;


/**
 * Implementation of an {@link InputStreamReader} respecting the BOM.
 */
public class UnicodeReader extends Reader {
    /**
     * Thw input stream pocessinf the BOM.
     */
    private final transient BOMInputStream in;

    /**
     * The underlying reader.
     */
    private transient Reader delegateReader;

    /**
     * Characterset  determined by the BOM.
     */
    private transient Charset charset;

    /**
     * Text to be apoended.
     */
    private transient String appended;

    /**
     * Initialize a new instance of the {@link UnicodeReader} class.
     *
     * @param raw the input stream to be wrapped.
     */
    public UnicodeReader(final InputStream raw) {
        this(raw, null);
    }

    /**
     * Initialize a new instance of the {@link UnicodeReader} class.
     * 
     * @param raw the underlying input stream.
     * @param appended some text to be appended
     */   
    public UnicodeReader(
        final InputStream raw,
        final String appended
    ) {
        super(raw);
        this.in = new BOMInputStream(raw,
            false,
            ByteOrderMark.UTF_8,
            ByteOrderMark.UTF_16BE,
            ByteOrderMark.UTF_16LE
        );
        this.appended = appended;
    }

    /**
     * Get the detected character set.
     *
     * @return the detected character set.
     * @throws IOException if the underlying stream cannot be read.
     */
    public Charset getCharset() throws IOException {
        ensureDelegate();
        return charset;
    }

    /**
     * Ensure that the underlying reader has been initialized.
     *
     * @return the underlying reader
     * @throws IOException if the underlying stream cannot be read.
     */
    private Reader ensureDelegate() throws IOException {
        if (delegateReader == null) {
            synchronized (lock) {
                if (delegateReader == null) {
                    try {
                        charset = (in.hasBOM())
                        ? Charset.forName(in.getBOM().getCharsetName())
                        : StandardCharsets.UTF_8;
                        var in_app = new SequenceInputStream(
                            in,
                            new ByteArrayInputStream(
                                appended.getBytes(charset)
                            )
                        );
                        delegateReader = new InputStreamReader(in, charset);
                    } catch (IllegalCharsetNameException ex) {
                        throw new IOException(ex);
                    }
                }
            }
        }
        return delegateReader;
    }

    /**
     * {@inheritDoc}
     */
    public void close() throws IOException {
        synchronized (lock) {
            if (delegateReader != null) {
                delegateReader.close();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public int read(
        final char[] cbuf,
        final int off,
        final int len
    ) throws IOException {
        return ensureDelegate().read(
            cbuf, off, len
        );
    }
}
