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

package org.mxupdate.test.util;

/**
 * MX Versions to define not supported test data.
 *
 * @author The MxUpdate Team
 */
public enum Version
{
    /** MX version V6R2011x. */
    V6R2011x(1),
    /** MX version V6R2012x. */
    V6R2012x(2),
    /** MX version V6R2013x. */
    V6R2013x(3),
    /** MX version V6R2014x. */
    V6R2014x(4);

    /** Index. */
    private final int idx;

    /**
     * Constructor.
     *
     * @param _idx  index
     */
    private Version(final int _idx)
    {
        this.idx = _idx;
    }

    /**
     * Checks if this version has maximum {@code _version}.
     *
     * @param _version  maximum version
     * @return <i>true</i> if this version is greater or equal;
     *         otherwise <i>false</i>
     */
    public boolean max(final Version _version)
    {
        return (this.idx <= _version.idx);
    }

    /**
     * Checks if this version has minimum {@code _version}.
     *
     * @param _version  minimum version
     * @return <i>true</i> if this version is less or equal;
     *         otherwise <i>false</i>
     */
    public boolean min(final Version _version)
    {
        return (this.idx >= _version.idx);
    }
}
