/*
 * Copyright 2008-2009 The MxUpdate Team
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
 * Revision:        $Rev$
 * Last Changed:    $Date$
 * Last Changed By: $Author$
 */

package org.mxupdate.plugin;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

import matrix.util.Mime64;

/**
 * Abstract class which defines common methods to extract called parameters
 * from the Eclipse plug-in.
 *
 * @author The MxUpdate Team
 * @version $Id$
 */
abstract class AbstractPlugin_mxJPO
{
    /**
     * Name of the key in the return map for the log message.
     *
     * @see #prepareReturn(String, String, Exception, Object)
     */
    private static final String RETURN_KEY_LOG = "log";

    /**
     * Name of the key in the return map for the error message.
     *
     * @see #prepareReturn(String, String, Exception, Object)
     */
    private static final String RETURN_KEY_ERROR = "error";

    /**
     * Name of the key in the return map for the exception.
     *
     * @see #prepareReturn(String, String, Exception, Object)
     */
    private static final String RETURN_KEY_EXCEPTION = "exception";

    /**
     * Name of the key in the return map for the values.
     *
     * @see #prepareReturn(String, String, Exception, Object)
     */
    private static final String RETURN_KEY_VALUES = "values";

    /**
     * Decodes given string value to an object of given type
     * <code>&lt;T&gt;</code>. First the string is <b>base64</b> decoded, then
     * the object instance is extracted from the decoded bytes via the Java
     * &quot;standard&quot; feature of the {@link ObjectInputStream}.
     *
     * @param <T>       type of the object which must be decoded
     * @param _value    string with the value to decode to a object
     * @return object instance of given type <code>&lt;T&gt;</code>
     * @throws IOException              if the value could not be decoded or
     *                                  the or decoder stream could not be
     *                                  opened
     * @throws ClassNotFoundException   if the object itself could not be read
     *                                  from decoder stream
     */
    @SuppressWarnings("unchecked")
    protected final <T> T decode(final String _value)
            throws IOException, ClassNotFoundException
    {
        final byte[] bytes = Mime64.decode(_value);

        final ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        final ObjectInputStream ois = new ObjectInputStream(in);
        final T fileNames = (T) ois.readObject();
        ois.close();
        return fileNames;
    }

    /**
     * Packed the values to return in a map.
     *
     * @param <T>           defines the Java type of the values
     * @param _log          log message
     * @param _error        error message
     * @param _exception    throws exception
     * @param _values       values itself
     * @return arguments packed in a map
     */
    protected <T> Map<String, Object> prepareReturn(final String _log,
                                                    final String _error,
                                                    final Exception _exception,
                                                    final T _values)
    {
        final Map<String,Object> jpoReturn = new HashMap<String,Object>(4);
        jpoReturn.put(AbstractPlugin_mxJPO.RETURN_KEY_LOG,       _log);
        jpoReturn.put(AbstractPlugin_mxJPO.RETURN_KEY_ERROR,     _error);
        jpoReturn.put(AbstractPlugin_mxJPO.RETURN_KEY_EXCEPTION, _exception);
        jpoReturn.put(AbstractPlugin_mxJPO.RETURN_KEY_VALUES,    _values);
        return jpoReturn;
    }
}