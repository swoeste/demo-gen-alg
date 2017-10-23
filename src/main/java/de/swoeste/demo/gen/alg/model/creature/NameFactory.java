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

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author swoeste
 */
public class NameFactory {

    private static final Logger       LOG          = LoggerFactory.getLogger(NameFactory.class);

    private static final String       UTF_8        = "UTF-8";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     //$NON-NLS-1$

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

        final String msg = MessageFormat.format("Gender {0} not supported", gender);
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
            final File file = new File(NameFactory.class.getResource(resource).toURI());
            final List<String> lines = FileUtils.readLines(file, UTF_8);
            for (final String line : lines) {
                final String trimmedLine = StringUtils.trimToNull(line);
                if (trimmedLine != null) {
                    result.add(trimmedLine);
                }
            }
            return result;
        } catch (IOException | URISyntaxException ex) {
            LOG.error("Unable to read {}", resource, ex); //$NON-NLS-1$
            throw new IllegalStateException("unable to read " + resource, ex); //$NON-NLS-1$
        }
    }

}
