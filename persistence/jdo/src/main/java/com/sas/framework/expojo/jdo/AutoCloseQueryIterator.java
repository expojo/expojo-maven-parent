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

import javax.jdo.Query;


/**
 * An iterator over the result of a JDO query. Closes the result at the end of the iteration 
 * @author  voodoo@objectmagic.org
 */
public class AutoCloseQueryIterator extends ReadonlyIterator implements Disposable {
    
    /**
     * The JDO Query
     */
    private Query query;
    
    /**
     * The original result 
     */
    private Object result;
    
    /**
     * The current Iterator
     */
    private java.util.Iterator iterator;
    
    /**
     * @param q the original JDO query
     * @param r the result
     */
    public AutoCloseQueryIterator(Query q, Object r) {
        query = q;
        result = r;
        iterator = ((java.util.Collection)result).iterator();
    }
    
    /* (non-Javadoc)
     * @see java.util.Iterator#hasNext()
     */
    public boolean hasNext() {
        boolean hn = false;
        if (iterator != null) {
            hn = iterator.hasNext();
            if (!hn) {
                query.close(result);
                iterator = null;
            }
        }
        return hn;
    }
    
    /* (non-Javadoc)
     * @see java.util.Iterator#next()
     */
    public Object next() {
        if (!hasNext()) {
            throw new java.util.NoSuchElementException();
        }
        Object o = iterator.next();
        return o;
    }
    
    
    /* (non-Javadoc)
     * @see org.omj.core.util.Disposable#dispose()
     */
    public void dispose() {
        if (iterator != null) {
            query.close(result);
            iterator = null;
        }
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#finalize()
     */
    public void finalize() throws Throwable {
        if (iterator != null) {
            query.close(result);
            iterator = null;
        }
    }
    
}
