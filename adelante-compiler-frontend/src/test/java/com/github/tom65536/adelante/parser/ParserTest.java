package com.github.tom65536.adelante.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

import org.testng.ITest;
import org.testng.annotations.*;

/**
 * Unit test for the adelante parser.
 */
public class ParserTest implements ITest
{
    /**
     * Local path to the tests YAML filen
     */
    public static final String TEST_FILE_PATH = ParserTest.class.getPackageName().replace('.', '/') + "/parser-tests.yml";

    private ThreadLocal<String> testName = new ThreadLocal<>();

    @BeforeMethod
    public void beforeMethod(Method method, Object[] testData){
        testName.set(method.getName() + ":" + testData[0].toString());
    }

    /**
     * Repeated test for a fiven scenario.
     * 
     * @param scenario the scenario ro be tested
     */
    @Test(dataProvider = "load-scenarios", enabled = true)
    public void testScenario(final ParserTestScenario scenario)
        throws IOException
    {
        final AdelanteParser p = new AdelanteParser(new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8)));
        scenario.check(p);
    }

    @DataProvider(name = "load-scenarios")
    public Object[][] loadScenariosFromTestResources() throws IOException {
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

    @Override
    public String getTestName() {
       return testName.get();
    }
}
