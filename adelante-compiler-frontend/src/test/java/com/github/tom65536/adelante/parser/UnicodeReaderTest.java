package com.github.tom65536.adelante.parser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Stream;

import org.apache.commons.io.ByteOrderMark;
import org.testng.annotations.*;

public class UnicodeReaderTest {
    public static final String SENTENCE = "Hello world, Καλημέρα κόσμε, コンニチハ";
    @Test
    void testUtf8WithBom() {
       final byte[] sent = SENTENCE.getBytes(StandardCharsets.UTF_8);
       final byte[] bom = ByteOrderMark.UTF_8.getBytes();
       final InputStream in = new ByteArrayInputStream(
            ByteBuffer.allocate(bom.length+sent.length)
            .put(bom)
            .put(sent, bom.length, sent.length)
            .array());
    }
}
