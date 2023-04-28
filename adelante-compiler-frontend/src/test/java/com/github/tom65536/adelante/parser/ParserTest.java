package com.github.tom65536.adelante.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.testng.annotations.*;

/**
 * Unit test for the adelante parser.
 */
public class ParserTest 
{
    /**
     * Local path to the tests YAML filen
     */
    public static final String TEST_FILE_PATH = "/" + ParserTest.class.getPackageName().replace('.', '/') + "/parser-tests.yml";

    /**
     * Rigorous Test :-)
     */
    @Test(dataProvider = "load-scenarios", enabled = false)
    public void testScenario(final ParserTestScenario scenario)
        throws IOException
    {
        final AdelanteParser p = new AdelanteParser(new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8)));
        scenario.check(p);
    }

    @DataProvider(name = "load-scenarios")
    public Object[][] loadScenariosFromTestResources() throws IOException {
        System.out.println(TEST_FILE_PATH);
        final var url = getClass().getClassLoader().getResource(TEST_FILE_PATH);
        final var list = ParserTestScenario.fromYamlUrl(url);
        final Object[][] result = new Object[list.size()][];
        int i = 0;
        for(var scenario : list) {
            result[i] = new Object[1];
            result[i][0] = scenario;
            ++i;
        }
        return result;
    }

}
