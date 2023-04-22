package com.github.tom65536.adelante.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Iterator;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.apache.commons.io.ByteOrderMark;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.BOMInputStream;
import org.testng.annotations.*;
import static org.testng.Assert.*;

public class UnicodeReaderTest {
    public static final String SENTENCE = "Hello world, Καλημέρα κόσμε, コンニチハ";

    @Test(dataProvider = "bom-provider")
    void testBoms(final ByteOrderMark bom) throws IOException {
        final InputStream in= encodeSentence(bom);
        final UnicodeReader reader = new UnicodeReader(in);
        
        final StringWriter writer = new StringWriter();
        IOUtils.copy(reader, writer);
        assertEquals(writer.toString(), SENTENCE);
    }

    @Test(dataProvider = "bom-provider")
    void testBOMInputStream(final ByteOrderMark bom) throws IOException {
        final BOMInputStream in = new BOMInputStream(encodeSentence(bom));
        assertEquals(in.getBOM(), bom);
    }

    private InputStream encodeSentence(final ByteOrderMark bom) {
        final Charset chs = (bom == null)? StandardCharsets.UTF_8 : Charset.forName(bom.getCharsetName());
        final int boml = (bom == null)? 0 : bom.length();
        final byte[] sent = SENTENCE.getBytes(chs);
        final byte[] raw = new byte[sent.length+boml];
        System.arraycopy(bom.getBytes(), 0, raw, 0, boml);
        System.arraycopy(sent, 0, raw, bom.length(), sent.length);
        return new ByteArrayInputStream(raw);
    }

    @DataProvider(name = "bom-provider")
    Iterator<Object[]> createBOM() {
        return Arrays.asList(
            new Object[]{ByteOrderMark.UTF_8},
            new Object[]{ByteOrderMark.UTF_16BE},
            new Object[]{ByteOrderMark.UTF_16LE},
            new Object[]{null}
        ).iterator();
    }
}
