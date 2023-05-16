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
