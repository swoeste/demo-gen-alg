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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * TODO JAVADOC
 *
 * @author swoeste
 */
public class SortedList<E> implements List<E> {

    private final Comparator<E> comparator;
    private final List<E>       list;

    public SortedList(final Comparator<E> comparator) {
        this(new ArrayList<>(), comparator);
    }

    public SortedList(final List<E> list, final Comparator<E> comparator) {
        this.list = list;
        this.comparator = comparator;
    }

    /** {@inheritDoc} */
    @Override
    public int size() {
        return this.list.size();
    }

    /** {@inheritDoc} */
    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    /** {@inheritDoc} */
    @Override
    public boolean contains(final Object o) {
        return this.list.contains(o);
    }

    /** {@inheritDoc} */
    @Override
    public Iterator<E> iterator() {
        return this.list.iterator();
    }

    /** {@inheritDoc} */
    @Override
    public Object[] toArray() {
        return this.list.toArray();
    }

    /** {@inheritDoc} */
    @Override
    public <T> T[] toArray(final T[] a) {
        return this.list.toArray(a);
    }

    /** {@inheritDoc} */
    @Override
    public boolean add(final E element) {
        if (this.list.isEmpty()) {
            return this.list.add(element);
        }
        return add(element, 0, this.list.size() - 1);
    }

    private boolean add(final E element, final int left, final int right) {
        // check left border
        final E leftItem = this.list.get(left);
        final int leftCompare = this.comparator.compare(element, leftItem);
        if (leftCompare < 0) {
            this.list.add(left, element);
            return true;
        }

        // check right border
        final E rightItem = this.list.get(right);
        final int rightCompare = this.comparator.compare(element, rightItem);
        if (rightCompare > 0) {
            this.list.add(right + 1, element);
            return true;
        }

        // an element with the same sort identity was already added
        if (left >= right) {
            for (int i = left; i < (this.list.size() - 1); i++) {
                if (this.comparator.compare(element, this.list.get(i)) != 0) {
                    this.list.add(i - 1, element);
                    return true;
                }
            }

            this.list.add(left, element);
            return true;
        }

        final int mid = (int) (left + ((right - left) / 2.0));
        final E midElement = this.list.get(mid);
        final int midCompare = this.comparator.compare(element, midElement);

        if (midCompare > 0) {
            return add(element, mid + 1, right);
        } else if (midCompare < 0) {
            return add(element, left, mid - 1);
        } else {
            // compare == 0
            for (int i = mid; i >= 0; i--) {
                if (this.comparator.compare(element, this.list.get(i)) != 0) {
                    this.list.add(i + 1, element);
                    return true;
                }
            }

            this.list.add(left, element);
            return true;
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean remove(final Object o) {
        return this.list.remove(o);
    }

    /** {@inheritDoc} */
    @Override
    public boolean containsAll(final Collection<?> c) {
        return this.list.containsAll(c);
    }

    /** {@inheritDoc} */
    @Override
    public boolean addAll(final Collection<? extends E> c) {
        // add each element one by one, we don't know whether the input is already sorted or not
        boolean result = false;
        for (E e : c) {
            final boolean add = add(e);
            if (add) {
                result = true;
            }
        }
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public boolean addAll(final int index, final Collection<? extends E> c) {
        throw new UnsupportedOperationException("Can't add elements to specific indexes in a sorted list!"); //$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public boolean removeAll(final Collection<?> c) {
        return this.list.removeAll(c);
    }

    /** {@inheritDoc} */
    @Override
    public boolean retainAll(final Collection<?> c) {
        return this.list.retainAll(c);
    }

    /** {@inheritDoc} */
    @Override
    public void clear() {
        this.list.clear();
    }

    /** {@inheritDoc} */
    @Override
    public E get(final int index) {
        return this.list.get(index);
    }

    /** {@inheritDoc} */
    @Override
    public E set(final int index, final E element) {
        throw new UnsupportedOperationException("Can't set an element to a specific index in a sorted list!"); //$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public void add(final int index, final E element) {
        throw new UnsupportedOperationException("Can't add an element to a specific index in a sorted list!"); //$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public E remove(final int index) {
        return this.list.remove(index);
    }

    /** {@inheritDoc} */
    @Override
    public int indexOf(final Object o) {
        return this.list.indexOf(o);
    }

    /** {@inheritDoc} */
    @Override
    public int lastIndexOf(final Object o) {
        return this.list.lastIndexOf(o);
    }

    /** {@inheritDoc} */
    @Override
    public ListIterator<E> listIterator() {
        return this.list.listIterator();
    }

    /** {@inheritDoc} */
    @Override
    public ListIterator<E> listIterator(final int index) {
        return this.list.listIterator(index);
    }

    /** {@inheritDoc} */
    @Override
    public List<E> subList(final int fromIndex, final int toIndex) {
        return this.list.subList(fromIndex, toIndex);
    }

}
