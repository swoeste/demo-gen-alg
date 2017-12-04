/*-
 * Copyright (C) 2017 Sebastian Woeste
 *
 * Licensed to Sebastian Woeste under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership. I license this file to You under
 * the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License
 * at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package de.swoeste.demo.gen.alg.model.creature;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author swoeste
 */
public class NameFactory {

    private static final Logger       LOG          = LoggerFactory.getLogger(NameFactory.class);

    private static final String       UTF_8        = "UTF-8";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             //$NON-NLS-1$

    private static final Random       RANDOM       = new Random();

    private static final List<String> FEMALE_NAMES = initFemaleNames();
    private static final List<String> MALE_NAMES   = initMaleNames();
    private static final List<String> LASTNAMES    = initLastnames();

    private NameFactory() {
        // hidden
    }

    public static final String getRandomName(final Gender gender) {
        if (Gender.MALE.equals(gender)) {
            final int index = RANDOM.nextInt(MALE_NAMES.size());
            return MALE_NAMES.get(index);
        }

        if (Gender.FEMALE.equals(gender)) {
            final int index = RANDOM.nextInt(FEMALE_NAMES.size());
            return FEMALE_NAMES.get(index);
        }

        final String msg = MessageFormat.format("Gender {0} not supported", gender); //$NON-NLS-1$
        throw new IllegalArgumentException(msg);
    }

    public static final String getRandomLastname() {
        final int index = RANDOM.nextInt(LASTNAMES.size());
        return LASTNAMES.get(index);
    }

    private static final List<String> initFemaleNames() {
        return getValuesFromResource("/names-female.txt"); //$NON-NLS-1$
    }

    private static final List<String> initLastnames() {
        return getValuesFromResource("/lastnames.txt"); //$NON-NLS-1$
    }

    private static final List<String> initMaleNames() {
        return getValuesFromResource("/names-male.txt"); //$NON-NLS-1$
    }

    private static final List<String> getValuesFromResource(final String resource) {
        try {
            final List<String> result = new ArrayList<>();

            try (final InputStream is = NameFactory.class.getResourceAsStream(resource);
                    final InputStreamReader isr = new InputStreamReader(is, UTF_8);
                    final BufferedReader br = new BufferedReader(isr);) {
                br.lines().forEach(entry -> result.add(StringUtils.trimToNull(entry)));
            }

            return result;
        } catch (IOException ex) {
            LOG.error("Unable to read {}", resource, ex); //$NON-NLS-1$
            throw new IllegalStateException("unable to read " + resource, ex); //$NON-NLS-1$
        }
    }

}
