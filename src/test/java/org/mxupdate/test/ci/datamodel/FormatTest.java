/*
 * Copyright 2008-2014 The MxUpdate Team
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

package org.mxupdate.test.ci.datamodel;

import org.mxupdate.test.AbstractDataExportUpdate;
import org.mxupdate.test.data.datamodel.FormatData;
import org.mxupdate.test.data.program.MQLProgramData;
import org.mxupdate.test.util.Version;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test class for format exports and updates.
 *
 * @author The MxUpdate Team
 */
@Test()
public class FormatTest
    extends AbstractDataExportUpdate<FormatData>
{
    /**
     * Creates for given <code>_name</code> a new format instance.
     *
     * @param _name     name of the format instance
     * @return format instance
     */
    @Override()
    protected FormatData createNewData(final String _name)
    {
        return new FormatData(this, _name);
    }

    /**
     * Data provider for test formats.
     *
     * @return object array with all test formats
     */
    @DataProvider(name = "data")
    public Object[][] getFormats()
    {
        return this.prepareData("format",
                new Object[]{
                        "format without anything (to test required fields)",
                        new FormatData(this, "hello \" test")},
                new Object[]{
                        "format with other symbolic name",
                        new FormatData(this, "hello \" test")
                                .setSymbolicName("format_Test")},
                new Object[]{
                        "format with description",
                        new FormatData(this, "hello \" test")
                                .setValue("description", "complex description \"test\"")},
                new Object[]{
                        "format with version",
                        new FormatData(this, "hello \" test")
                                .setValue("version", "version \"test\"")},
                new Object[]{
                        "format with suffix",
                        new FormatData(this, "hello \" test")
                                .setValue("suffix", "suffix \"test\"")},
                new Object[]{
                        "format with mime",
                        new FormatData(this, "hello \" test")
                                .setValue("mime", "mime \"test\"")},
                new Object[]{
                        "format with type",
                        new FormatData(this, "hello \" test")
                                .setValue("type", "type \"test\"")},
                new Object[]{
                        "issue ##212: format with view program",
                        new FormatData(this, "hello \" test")
                                .notSupported(Version.V6R2014x, Version.V6R2015x)
                                .setViewProgram(new MQLProgramData(this, "ViewProgram"))},
                new Object[]{
                        "issue ##212: format with view program",
                        new FormatData(this, "hello \" test")
                                .notSupported(Version.V6R2011x, Version.V6R2012x, Version.V6R2013x)
                                .setViewProgram(new MQLProgramData(this, "ViewProgram")),
                        new FormatData(this, "hello \" test"),
                        "[INFO]     - view program MXUPDATE_ViewProgram ignored (not supported anymore!)"},
                new Object[]{
                        "issue ##212: format with edit program",
                        new FormatData(this, "hello \" test")
                                .notSupported(Version.V6R2014x, Version.V6R2015x)
                                .setEditProgram(new MQLProgramData(this, "EditProgram"))},
                new Object[]{
                        "issue ##212: format with edit program",
                        new FormatData(this, "hello \" test")
                                .notSupported(Version.V6R2011x, Version.V6R2012x, Version.V6R2013x)
                                .setEditProgram(new MQLProgramData(this, "EditProgram")),
                        new FormatData(this, "hello \" test"),
                        "[INFO]     - edit program MXUPDATE_EditProgram ignored (not supported anymore!)"},
                new Object[]{
                        "issue ##212: format with print program",
                        new FormatData(this, "hello \" test")
                                .notSupported(Version.V6R2014x, Version.V6R2015x)
                                .setPrintProgram(new MQLProgramData(this, "PrintProgram"))},
                new Object[]{
                        "issue ##212: format with print program",
                        new FormatData(this, "hello \" test")
                                .notSupported(Version.V6R2011x, Version.V6R2012x, Version.V6R2013x)
                                .setPrintProgram(new MQLProgramData(this, "PrintProgram")),
                        new FormatData(this, "hello \" test"),
                        "[INFO]     - print program MXUPDATE_PrintProgram ignored (not supported anymore!)"}
        );
    }

    /**
     * Removes the MxUpdate formats and programs.
     *
     * @throws Exception if MQL execution failed
     */
    @BeforeMethod()
    @AfterMethod()
    public void cleanup()
        throws Exception
    {
        this.cleanup(CI.DM_FORMAT);
        this.cleanup(CI.PRG_MQL_PROGRAM);
    }
}
