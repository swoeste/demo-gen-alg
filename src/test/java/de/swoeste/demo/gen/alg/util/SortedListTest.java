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
package de.swoeste.demo.gen.alg.util;

import java.text.MessageFormat;
import java.util.Comparator;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author swoeste
 */
@Test
public class SortedListTest {

    public void testAddWithOnlyIdenticalElements() {
        final SortedList<TestElement> list = new SortedList<>(new TestElementComparator());
        list.add(new TestElement(1, 0));
        list.add(new TestElement(3, 1));
        list.add(new TestElement(3, 2));
        list.add(new TestElement(3, 3));
        list.add(new TestElement(3, 4));
        list.add(new TestElement(3, 5));
        list.add(new TestElement(3, 6));
        list.add(new TestElement(3, 7));
        list.add(new TestElement(5, 8));
        assertSorted(list);
    }

    public void testAddWithOnlyIdenticalElementsReverse() {
        final SortedList<TestElement> list = new SortedList<>(new TestElementComparatorReverse());
        list.add(new TestElement(1, 0));
        list.add(new TestElement(3, 1));
        list.add(new TestElement(3, 2));
        list.add(new TestElement(3, 3));
        list.add(new TestElement(3, 4));
        list.add(new TestElement(3, 5));
        list.add(new TestElement(3, 6));
        list.add(new TestElement(3, 7));
        list.add(new TestElement(5, 8));
        assertSortedReverse(list);
    }

    public void testAddWithMultipleOccurance() {
        final SortedList<TestElement> list = new SortedList<>(new TestElementComparator());
        list.add(new TestElement(0, 0));
        list.add(new TestElement(1, 1));
        list.add(new TestElement(2, 2));
        list.add(new TestElement(3, 3));
        list.add(new TestElement(4, 4));
        list.add(new TestElement(5, 5));
        list.add(new TestElement(9, 6));
        list.add(new TestElement(8, 7));
        list.add(new TestElement(7, 8));
        list.add(new TestElement(6, 9));
        list.add(new TestElement(6, 10));
        list.add(new TestElement(7, 11));
        list.add(new TestElement(8, 12));
        list.add(new TestElement(9, 13));
        list.add(new TestElement(5, 14));
        list.add(new TestElement(4, 15));
        list.add(new TestElement(3, 16));
        list.add(new TestElement(2, 17));
        list.add(new TestElement(1, 18));
        list.add(new TestElement(0, 19));
        assertSorted(list);
    }

    public void testAddWithMultipleOccuranceReverse() {
        final SortedList<TestElement> list = new SortedList<>(new TestElementComparatorReverse());
        list.add(new TestElement(0, 0));
        list.add(new TestElement(1, 1));
        list.add(new TestElement(2, 2));
        list.add(new TestElement(3, 3));
        list.add(new TestElement(4, 4));
        list.add(new TestElement(5, 5));
        list.add(new TestElement(9, 6));
        list.add(new TestElement(8, 7));
        list.add(new TestElement(7, 8));
        list.add(new TestElement(6, 9));
        list.add(new TestElement(6, 10));
        list.add(new TestElement(7, 11));
        list.add(new TestElement(8, 12));
        list.add(new TestElement(9, 13));
        list.add(new TestElement(5, 14));
        list.add(new TestElement(4, 15));
        list.add(new TestElement(3, 16));
        list.add(new TestElement(2, 17));
        list.add(new TestElement(1, 18));
        list.add(new TestElement(0, 19));
        assertSortedReverse(list);
    }

    public void testAddWithRandomOrder() {
        final SortedList<TestElement> list = new SortedList<>(new TestElementComparator());
        list.add(new TestElement(4, 0));
        list.add(new TestElement(1, 1));
        list.add(new TestElement(2, 2));
        list.add(new TestElement(6, 3));
        list.add(new TestElement(5, 4));
        list.add(new TestElement(3, 5));
        list.add(new TestElement(0, 6));
        assertSorted(list);
    }

    public void testAddWithRandomOrderReverse() {
        final SortedList<TestElement> list = new SortedList<>(new TestElementComparatorReverse());
        list.add(new TestElement(4, 0));
        list.add(new TestElement(1, 1));
        list.add(new TestElement(2, 2));
        list.add(new TestElement(6, 3));
        list.add(new TestElement(5, 4));
        list.add(new TestElement(3, 5));
        list.add(new TestElement(0, 6));
        assertSortedReverse(list);
    }

    public void testAddWithAlternatingOrder() {
        final SortedList<TestElement> list = new SortedList<>(new TestElementComparator());
        list.add(new TestElement(0, 1));
        list.add(new TestElement(9, 2));
        list.add(new TestElement(1, 3));
        list.add(new TestElement(8, 4));
        list.add(new TestElement(2, 5));
        list.add(new TestElement(7, 6));
        list.add(new TestElement(3, 7));
        list.add(new TestElement(6, 8));
        list.add(new TestElement(4, 9));
        list.add(new TestElement(5, 10));
        assertSorted(list);
    }

    public void testAddWithAlternatingOrderReverse() {
        final SortedList<TestElement> list = new SortedList<>(new TestElementComparatorReverse());
        list.add(new TestElement(0, 1));
        list.add(new TestElement(9, 2));
        list.add(new TestElement(1, 3));
        list.add(new TestElement(8, 4));
        list.add(new TestElement(2, 5));
        list.add(new TestElement(7, 6));
        list.add(new TestElement(3, 7));
        list.add(new TestElement(6, 8));
        list.add(new TestElement(4, 9));
        list.add(new TestElement(5, 10));
        assertSortedReverse(list);
    }

    public void testAddInReverseSortedOrder() {
        final SortedList<TestElement> list = new SortedList<>(new TestElementComparator());
        list.add(new TestElement(7, 1));
        list.add(new TestElement(6, 2));
        list.add(new TestElement(5, 3));
        list.add(new TestElement(4, 4));
        list.add(new TestElement(3, 5));
        list.add(new TestElement(2, 6));
        list.add(new TestElement(1, 7));
        assertSorted(list);
    }

    public void testAddInReverseSortedOrderReverse() {
        final SortedList<TestElement> list = new SortedList<>(new TestElementComparatorReverse());
        list.add(new TestElement(7, 1));
        list.add(new TestElement(6, 2));
        list.add(new TestElement(5, 3));
        list.add(new TestElement(4, 4));
        list.add(new TestElement(3, 5));
        list.add(new TestElement(2, 6));
        list.add(new TestElement(1, 7));
        assertSortedReverse(list);
    }

    public void testAddInSortedOrder() {
        final SortedList<TestElement> list = new SortedList<>(new TestElementComparator());
        list.add(new TestElement(1, 1));
        list.add(new TestElement(2, 2));
        list.add(new TestElement(3, 3));
        list.add(new TestElement(4, 4));
        list.add(new TestElement(5, 5));
        list.add(new TestElement(6, 6));
        list.add(new TestElement(7, 7));
        assertSorted(list);
    }

    public void testAddInSortedOrderReverse() {
        final SortedList<TestElement> list = new SortedList<>(new TestElementComparatorReverse());
        list.add(new TestElement(1, 1));
        list.add(new TestElement(2, 2));
        list.add(new TestElement(3, 3));
        list.add(new TestElement(4, 4));
        list.add(new TestElement(5, 5));
        list.add(new TestElement(6, 6));
        list.add(new TestElement(7, 7));
        assertSortedReverse(list);
    }

    public void testAddRandom() {
        final Random random = new Random();
        final SortedList<TestElement> list = new SortedList<>(new TestElementComparator());
        for (int i = 0; i < 1000; i++) {
            list.add(new TestElement(random.nextInt(11), i));
        }
        assertSorted(list);
    }

    public void testAddRandomReverse() {
        final Random random = new Random();
        final SortedList<TestElement> list = new SortedList<>(new TestElementComparatorReverse());
        for (int i = 0; i < 1000; i++) {
            list.add(new TestElement(random.nextInt(11), i));
        }
        assertSortedReverse(list);
    }

    private void assertSorted(final SortedList<TestElement> list) {
        TestElement prevElement = null;
        for (TestElement testElement : list) {
            if (prevElement != null) {
                Assert.assertTrue(prevElement.getValue() <= testElement.getValue());
                if (prevElement.getValue() == testElement.getValue()) {
                    Assert.assertTrue(prevElement.getIndex() > testElement.getIndex());
                }
            }
            prevElement = testElement;
        }
    }

    private void assertSortedReverse(final SortedList<TestElement> list) {
        TestElement prevElement = null;
        for (TestElement testElement : list) {
            if (prevElement != null) {
                Assert.assertTrue(prevElement.getValue() >= testElement.getValue());
                if (prevElement.getValue() == testElement.getValue()) {
                    Assert.assertTrue(prevElement.getIndex() > testElement.getIndex());
                }
            }
            prevElement = testElement;
        }
    }

    private class TestElementComparator implements Comparator<TestElement> {

        /** {@inheritDoc} */
        @Override
        public int compare(final TestElement o1, final TestElement o2) {
            return Integer.compare(o1.getValue(), o2.getValue());
        }

    }

    private class TestElementComparatorReverse implements Comparator<TestElement> {

        /** {@inheritDoc} */
        @Override
        public int compare(final TestElement o1, final TestElement o2) {
            return Integer.compare(o1.getValue(), o2.getValue()) * -1;
        }

    }

    private class TestElement {

        private final int value;
        private final int index;

        public TestElement(final int value, final int index) {
            this.value = value;
            this.index = index;
        }

        public int getValue() {
            return this.value;
        }

        public int getIndex() {
            return this.index;
        }

        @Override
        public String toString() {
            return MessageFormat.format("{0}|{1}", this.value, this.index); //$NON-NLS-1$
        }

    }

}
