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

import org.testng.annotations.*;
import static org.testng.Assert.*;

public class UnicodeReaderTest {
    public static final String SENTENCE = "Hello world, Καλημέρα κόσμε, コンニチハ";

    @Test(dataProvider = "bom-provider")
    void testBoms(ByteOrderMark bom, Charset chs) throws IOException {
        final byte[] sent = SENTENCE.getBytes(chs);
        final ByteBuffer buffer = ByteBuffer.allocate(((bom==null)?0:bom.length())+sent.length);
        if(bom!=null) buffer.put(bom.getBytes());
        buffer.put(sent);

        final InputStream in = new ByteArrayInputStream(buffer.array());
        final UnicodeReader reader = new UnicodeReader(in);
        final StringWriter writer = new StringWriter();
        IOUtils.copy(reader, writer);
        assertEquals(writer.toString(), SENTENCE);
    }

    @DataProvider(name = "bom-provider")
    Iterator<Object[]> createBOM() {
        return Arrays.asList(
            new Object[]{ByteOrderMark.UTF_8, StandardCharsets.UTF_8},
            new Object[]{ByteOrderMark.UTF_16BE, StandardCharsets.UTF_16BE},
            new Object[]{ByteOrderMark.UTF_16LE, StandardCharsets.UTF_16LE},
            new Object[]{null, StandardCharsets.UTF_8}
        ).iterator();
    }
}
