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
 * 
 */

package com.sas.framework.expojo.jdo;

/**
 * Non-mutable iterator
 * @author  voodoo@objectmagic.org
 */
public abstract class ReadonlyIterator implements java.util.Iterator {
    
    /** Creates a new instance of ReadonlyIterator */
    public ReadonlyIterator() {
    }
    
    /* (non-Javadoc)
     * @see java.util.Iterator#remove()
     */
    public void remove() {
        throw new UnsupportedOperationException("READONLY");
    }
    
}
