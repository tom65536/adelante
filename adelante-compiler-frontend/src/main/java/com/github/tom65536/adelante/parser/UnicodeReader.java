package com.github.tom65536.adelante.parser;

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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.SequenceInputStream;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.StandardCharsets;

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
     * Characterset determined by the BOM.
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
     * @param raw      the underlying input stream.
     * @param appendix some text to be appended
     */
    public UnicodeReader(
            final InputStream raw,
            final String appendix) {
        super(raw);
        this.in = new BOMInputStream(raw,
                false,
                ByteOrderMark.UTF_8,
                ByteOrderMark.UTF_16BE,
                ByteOrderMark.UTF_16LE);
        this.appended = appendix;
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
                        var inApp = (appended != null)
                            ? (new SequenceInputStream(
                                in,
                                new ByteArrayInputStream(
                                        appended.getBytes(charset))))
                            : in;
                        delegateReader = new InputStreamReader(inApp, charset);
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
            final int len) throws IOException {
        return ensureDelegate().read(
                cbuf, off, len);
    }
}
