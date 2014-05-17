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

package org.mxupdate.update.datamodel;

import java.io.IOException;

import org.mxupdate.mapping.TypeDef_mxJPO;
import org.mxupdate.update.util.DeltaUtil_mxJPO;
import org.mxupdate.update.util.MqlBuilder_mxJPO;
import org.mxupdate.update.util.ParameterCache_mxJPO;
import org.mxupdate.update.util.ParameterCache_mxJPO.ValueKeys;

/**
 * The class is used to evaluate information from date attributes within MX
 * used to export, delete and update a date attribute.
 *
 * @author The MxUpdate Team
 */
public class AttributeDate_mxJPO
    extends AbstractAttribute_mxJPO<AttributeDate_mxJPO>
{
    /** Range value flag. */
    private boolean rangeValue;

    /**
     * Constructor used to initialize the date attribute instance with
     * related type definition and attribute name.
     *
     * @param _typeDef  defines the related type definition
     * @param _mxName   MX name of the date attribute object
     */
    public AttributeDate_mxJPO(final TypeDef_mxJPO _typeDef,
                               final String _mxName)
    {
        super(_typeDef, _mxName, "date", "timestamp,");
    }

    /**
     * The method parses the date attribute specific XML URLs. This includes
     * information about:
     * <ul>
     * <li>contains the attribute {@link #rangeValue rangevalues}?</li>
     * </ul>
     *
     * @param _paramCache   parameter cache with MX context
     * @param _url      URL to parse
     * @param _content  content of the URL to parse
     * @return <i>true</i> if <code>_url</code> could be parsed; otherwise
     *         <i>false</i>
     */
    @Override()
    protected boolean parse(final ParameterCache_mxJPO _paramCache,
                            final String _url,
                            final String _content)
    {
        final boolean parsed;
        if ("/attrValueType".equals(_url) && "2".equals(_content))  {
            this.rangeValue = true;
            parsed = true;
        } else  {
            parsed = super.parse(_paramCache, _url, _content);
        }
        return parsed;
    }

    /**
     * {@inheritDoc}
     * Appends the date attribute specific values. Following values are
     * written:
     * <li>{@link #rangeValue range value} flag (if
     *     {@link ValueKeys#DMAttrSupportsFlagRangeValue} is defined)</li>
     */
    @Override()
    protected void writeAttributeSpecificValues(final ParameterCache_mxJPO _paramCache,
                                                final Appendable _out)
        throws IOException
    {
        if (_paramCache.getValueBoolean(ValueKeys.DMAttrSupportsFlagRangeValue))  {
            _out.append("  ").append(this.rangeValue ? "" : "!").append("rangevalue\n");
        }
    }

    /**
     * {@inheritDoc}
     * Appends the date attribute specific delta values. Following values are
     * calculated:
     * <li>{@link #rangeValue range value} flag (if
     *     {@link ValueKeys#DMAttrSupportsFlagRangeValue} is defined)</li>
     */
    @Override()
    protected void calcDelta(final ParameterCache_mxJPO _paramCache,
                             final MqlBuilder_mxJPO _mql,
                             final AttributeDate_mxJPO _target)
    {
        super.calcDelta(_paramCache, _mql, _target);

        if (_paramCache.getValueBoolean(ValueKeys.DMAttrSupportsFlagRangeValue))  {
            DeltaUtil_mxJPO.calcFlagDelta(_mql, "rangevalue", _target.rangeValue, this.rangeValue);
        }
    }
}
