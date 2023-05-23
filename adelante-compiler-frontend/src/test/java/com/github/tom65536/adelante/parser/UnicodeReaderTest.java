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
import java.io.StringWriter;
import java.util.Iterator;
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
        final BOMInputStream in = new BOMInputStream(encodeSentence(bom),
            false,
            ByteOrderMark.UTF_8,
            ByteOrderMark.UTF_16BE,
            ByteOrderMark.UTF_16LE
        );
        assertEquals(in.getBOM(), bom);
    }

    private InputStream encodeSentence(final ByteOrderMark bom) {
        final Charset chs = (bom == null)? StandardCharsets.UTF_8 : Charset.forName(bom.getCharsetName());
        final int boml = (bom == null)? 0 : bom.length();
        final byte[] sent = SENTENCE.getBytes(chs);
        final byte[] raw = new byte[sent.length+boml];
        if(bom != null) {
            System.arraycopy(bom.getBytes(), 0, raw, 0, boml);
        }
        System.arraycopy(sent, 0, raw, boml, sent.length);
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
