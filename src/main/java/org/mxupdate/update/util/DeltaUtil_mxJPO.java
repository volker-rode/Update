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

package org.mxupdate.update.util;

import java.util.Set;

/**
 * The JPO class holds utilities for calculating delta's.
 *
 * @author The MxUpdate Team
 */
public final class DeltaUtil_mxJPO
{
    /**
     * The constructor is defined so that no instance of the delta utility
     * could be created.
     */
    private DeltaUtil_mxJPO()
    {
    }

    /**
     * Calculates the delta between the new and the old value. If a delta
     * exists, the kind with the new delta is added to the string builder.
     *
     * @param _mql      MQL builder to append the delta
     * @param _kind     kind of the delta
     * @param _newVal   new target value
     * @param _curVal   current value in the database
     */
    public static void calcValueDelta(final MqlBuilder_mxJPO _mql,
                                      final String _kind,
                                      final String _newVal,
                                      final String _curVal)
    {
        final String curVal = (_curVal == null) ? "" : _curVal;
        final String newVal = (_newVal == null) ? "" : _newVal;

        if ((_curVal == null) || !curVal.equals(newVal))  {
            _mql.newLine()
                .cmd(_kind).cmd(" ").arg(newVal);
        }
    }

    /**
     * Calculates the delta between the new and the old value. If a delta
     * exists, the kind with the new delta is added to the string builder.
     *
     * @param _out      appendable instance where the delta must be append
     * @param _kind     kind of the delta
     * @param _newVal   new target value
     * @param _curVal   current value in the database
     */
    @Deprecated()
    public static void calcValueDelta(final StringBuilder _out,
                                      final String _kind,
                                      final String _newVal,
                                      final String _curVal)
    {
        final String curVal = (_curVal == null) ? "" : _curVal;
        final String newVal = (_newVal == null) ? "" : _newVal;

        if (!curVal.equals(newVal))  {
            _out.append(' ').append(_kind).append(" \"").append(StringUtil_mxJPO.convertMql(newVal)).append('\"');
        }
    }

    /**
     * Calculates the delta between the new and the old value. If a delta
     * exists, the kind with the new delta is added to the string builder.
     *
     * @param _mql      MQL builder to append the delta
     * @param _kind     kind of the delta
     * @param _newVal   new target value
     * @param _curVal   current value in the database
     */
    public static void calcFlagDelta(final MqlBuilder_mxJPO _mql,
                                     final String _kind,
                                     final boolean _newVal,
                                     final Boolean _curVal)
    {
        if ((_curVal == null) || (_curVal != _newVal))  {
            _mql.newLine();
            if (!_newVal)  {
                _mql.cmd("!");
            }
            _mql.cmd(_kind);
        }
    }

    /**
     * Calculates the delta between the new and the old value. If a delta
     * exists, the kind with the new delta is added to the string builder.
     *
     * @param _out      appendable instance where the delta must be append
     * @param _kind     kind of the delta
     * @param _newVal   new target value
     * @param _curVal   current value in the database
     */
    public static void calcFlagDelta(final StringBuilder _out,
                                     final String _kind,
                                     final boolean _newVal,
                                     final Boolean _curVal)
    {
        if ((_curVal == null) || (_curVal != _newVal))  {
            _out.append(' ');
            if (!_newVal)  {
                _out.append('!');
            }
            _out.append(_kind).append(' ');
        }
    }

    /**
     * Calculates the delta between the new and the old list set. If a delta
     * exists, the different elements are added or removed.
     *
     * @param _mql      MQL builder to append the delta
     * @param _kind     kind of the delta
     * @param _new      new target values
     * @param _current  current values in MX
     */
    public static void calcListDelta(final MqlBuilder_mxJPO _mql,
                                     final String _kind,
                                     final Set<String> _new,
                                     final Set<String> _current)
    {
        boolean equal = (_current.size() == _new.size());
        if (equal)  {
            for (final String format : _current)  {
                if (!_new.contains(format))  {
                    equal = false;
                    break;
                }
            }
        }
        if (!equal)  {
            for (final String curValue : _current)  {
                if (!_new.contains(curValue))  {
                    _mql.newLine()
                        .cmd("remove ").cmd(_kind).cmd(" ").arg(curValue);
                }
            }
            for (final String newValue : _new)  {
                if (!_current.contains(newValue))  {
                    _mql.newLine()
                        .cmd("add ").cmd(_kind).cmd(" ").arg(newValue);
                }
            }
        }
    }
}
