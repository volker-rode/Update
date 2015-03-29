/*
 * Copyright 2008-2015 The MxUpdate Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.mxupdate.test.test.update.util;

import junit.framework.Assert;
import matrix.db.MQLCommand;
import matrix.util.MatrixException;

import org.mxupdate.test.AbstractTest;
import org.mxupdate.test.data.datamodel.TypeData;
import org.mxupdate.update.util.MqlBuilder_mxJPO;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Tests the JPO {@link MqlBuilder_mxJPO}.
 *
 * @author The MxUpdate Team
 */
public class MqlBuilderTest
    extends AbstractTest
{
    /**
     * Removes the MxUpdate types.
     *
     * @throws Exception if MQL execution failed
     */
    @BeforeMethod()
    public void cleanup()
        throws Exception
    {
        this.cleanup(AbstractTest.CI.DM_TYPE);
    }

    @DataProvider(name = "testData")
    public Object[][] getData()
    {
        return new Object[][]
        {
            {"abc"},
            {"abc \" abc"},
            {"abc ' abc"},
        };
    }

    /**
     * Simple positive test to set the description of a type.
     *
     * @param _descr    description to text
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    @Test(description = "simple positive test to set the description of a type",
          dataProvider = "testData")
    public void positiveTestSimple(final String _descr)
        throws Exception
    {
        final TypeData type = new TypeData(this, "name");
        type.create();

        MqlBuilder_mxJPO.init()
            .newLine()
            .cmd("escape mod type ").arg(AbstractTest.PREFIX + "name").cmd(" description ").arg(_descr)
            .exec(this.getContext());

        // check result with old method
        final MQLCommand cmd = new MQLCommand();
        cmd.executeCommand(this.getContext(), "print type "+ AbstractTest.PREFIX + "name select description dump");
        Assert.assertEquals(
                _descr,
                cmd.getResult().trim());
    }

    /**
     * Positive test without arguments.
     *
     * @throws Exception
     */
    @Test(description = "positive test without arguments")
    public void positiveTestWithoutArguments()
        throws Exception
    {
        final TypeData type = new TypeData(this, "name");
        type.create();

        MqlBuilder_mxJPO.init()
            .newLine()
            .cmd("escape print context")
            .exec(this.getContext());
    }

    /**
     * Negative test that matrix exception is thrown for non existing command.
     *
     * @throws Exception
     */
    @Test(description = "negative test that matrix exception is thrown for non existing command",
          expectedExceptions = MatrixException.class)
    public void negativeTestNonExistingCommand()
        throws Exception
    {
        MqlBuilder_mxJPO.init()
            .newLine()
            .cmd("escape mod ddd")
            .exec(this.getContext());
    }


    /**
     * Negative test that command starts not with escape.
     *
     * @throws Exception
     */
    @Test(description = "negative test that command starts not with escape",
          expectedExceptions = MatrixException.class)
    public void negativeTestCommandWOEscape()
        throws Exception
    {
        MqlBuilder_mxJPO.init()
            .newLine()
            .cmd("print context")
            .exec(this.getContext());
    }
}
