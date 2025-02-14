/*
 * Copyright 2017 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.smallrye.config;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.io.Serializable;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import static io.smallrye.config.utils.ConfigSourceUtil.propertiesToMap;

/**
 * @author <a href="http://jmesnil.net/">Jeff Mesnil</a> (c) 2017 Red Hat inc.
 */
class SysPropConfigSource implements ConfigSource, Serializable {

    SysPropConfigSource() {
    }

    @Override
    public Map<String, String> getProperties() {

        return Collections.unmodifiableMap(
                propertiesToMap(AccessController.doPrivileged((PrivilegedAction<Properties>) System::getProperties)));
    }

    @Override
    public int getOrdinal() {
        return 400;
    }

    @Override
    public String getValue(String s) {
        return AccessController.doPrivileged((PrivilegedAction<String>) () -> System.getProperty(s));
    }

    @Override
    public String getName() {
        return "SysPropConfigSource";
    }
}
