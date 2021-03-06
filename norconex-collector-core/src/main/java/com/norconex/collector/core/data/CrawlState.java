/* Copyright 2014-2015 Norconex Inc.
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
 */
package com.norconex.collector.core.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Reference processing status.
 * @author Pascal Essiembre
 */
public class CrawlState implements Serializable {

    private static final Map<String, CrawlState> STATUSES = 
            new HashMap<>();
    
    //TODO make default state UNKNOWN/UNPROCESSED, or equivalent that means
    // TBD/IN_PROGRESS????
    // Because NEW is misleading in the case where it exists in the cache but 
    // no checksum defined... then it is nether new nor modified.
    private static final long serialVersionUID = 6542269270632505768L;

    public static final CrawlState NEW        = new CrawlState("NEW");
    public static final CrawlState MODIFIED   = new CrawlState("MODIFIED");
    public static final CrawlState UNMODIFIED = new CrawlState("UNMODIFIED");
    public static final CrawlState ERROR      = new CrawlState("ERROR");
    public static final CrawlState REJECTED   = new CrawlState("REJECTED");
    public static final CrawlState BAD_STATUS = new CrawlState("BAD_STATUS");
    public static final CrawlState DELETED    = new CrawlState("DELETED");
    public static final CrawlState NOT_FOUND  = new CrawlState("NOT_FOUND");
    
    private final String state;

    
    /**
     * Constructor.
     * @param state state code
     */
    protected CrawlState(String state) {
        this.state = state;
        STATUSES.put(state, this);
    }

    /**
     * Returns whether a reference should be considered "good" (the
     * corresponding document is not in a "bad" state, such as being rejected
     * or produced an error.  
     * This implementation will consider valid these reference statuses:
     * {@link #NEW}, {@link #MODIFIED}, {@link #UNMODIFIED}.
     * This method can be overridden to provide different logic for a valid
     * reference.
     * @return <code>true</code> if status is valid.
     */
    public boolean isGoodState() {
        return isOneOf(NEW, MODIFIED, UNMODIFIED);
    }

    /**
     * Returns whether a state indicates new or modified.
     * @return <code>true</code> if new or modified
     * @deprecated Since 1.2.0, use {@link #isNewOrModified()}
     */
    @Deprecated
    public boolean isCommittable() {
        return isOneOf(NEW, MODIFIED);
    }
    /**
     * Returns whether a state indicates new or modified.
     * @return <code>true</code> if new or modified
     */
    public boolean isNewOrModified() {
        return isOneOf(NEW, MODIFIED);
    }

    
    public boolean isOneOf(CrawlState... states) {
        if (ArrayUtils.isEmpty(states)) {
            return false;
        }
        for (CrawlState crawlState : states) {
            if (equals(crawlState)) {
                return true;
            }
        }
        return false;
    }
    
    public static synchronized CrawlState valueOf(String state) {
        CrawlState refState = STATUSES.get(state);
        if (refState == null) {
            refState = new CrawlState(state);
        }
        return refState;
    }
    
    @Override
    public String toString() {
        return state;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof CrawlState)) {
            return false;
        }
        CrawlState castOther = (CrawlState) other;
        return new EqualsBuilder().append(state, castOther.state).isEquals();
    }

    private transient int hashCode;
    @Override
    public int hashCode() {
        if (hashCode == 0) {
            hashCode = new HashCodeBuilder().append(state).toHashCode();
        }
        return hashCode;
    }
    
}
