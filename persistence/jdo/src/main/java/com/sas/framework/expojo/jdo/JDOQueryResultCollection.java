/*
 * Copyright 2006 Guido Anzuoni: voodoo@objectmagic.org.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * This inclusion of this class in expojo was kindly permitted by Guido Anzuoni http://objectmagic.org 
 */

package com.sas.framework.expojo.jdo;

import java.util.Collection;
import java.util.Iterator;

import javax.jdo.Query;

/**
 * A wrapper for the result of a query execution. Provides a specialized
 * iterator
 * 
 * @author voodoo@objectmagic.org
 */
public class JDOQueryResultCollection implements Collection, Disposable {

    /**
     * The JDO Query
     */
    private Query query;

    /**
     * The result
     */
    private Collection result;

    private boolean iteratorCreated = false;

    private boolean closeOnGCOnly = false;

    /** Creates a new instance of JDOQueryResultCollection */
    public JDOQueryResultCollection(Query q, Object r) {
        query = q;
        result = (Collection) r;
    }

    /**
     * @param q the query
     * @param r the result
     * @param closegconly if == false return the specialized iterator that closes the result when exhausted
     * if true behaves like a simple delegate collection (with close upon GC only)
     */
    public JDOQueryResultCollection(Query q, Object r, boolean closegconly) {
        query = q;
        result = (Collection) r;
        closeOnGCOnly = closegconly;
    }

    public int size() {
        return result != null ? result.size() : 0;
    }

    public boolean isEmpty() {
        return result != null ? result.isEmpty() : true;
    }

    public boolean contains(Object o) {
        return result != null ? result.contains(o) : false;
    }

    public Object[] toArray() {
        return result != null ? result.toArray() : new Object[0];
    }

    public Object[] toArray(Object[] a) {
        return result != null ? result.toArray(a) : new Object[0];
    }

    public String toString() {
        return result != null ? result.toString() : "";
    }

    public Iterator iterator() {
        if (result == null) {
            return null;
        }
        if (!closeOnGCOnly) {
            iteratorCreated = true;
            return new AutoCloseQueryIterator(query, result);
        }
        else {
            return result.iterator();
        }
    }

    public boolean add(Object o) {
        throw new UnsupportedOperationException();
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    public boolean containsAll(Collection coll) {
        if (result == null) {
            return false;
        }
        return result.containsAll(coll);
    }

    public boolean addAll(Collection coll) {
        throw new UnsupportedOperationException();
    }

    public boolean removeAll(Collection coll) {
        throw new UnsupportedOperationException();
    }

    public boolean retainAll(Collection coll) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    public void dispose() {
        // If we returned an iterator cleanup of JDO resources is delegated to
        // iterator
        if (!iteratorCreated && result != null) {
            query.close(result);
        }
        query = null;
        result = null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#finalize()
     */
    public void finalize() throws Throwable {
        dispose();
    }
}
