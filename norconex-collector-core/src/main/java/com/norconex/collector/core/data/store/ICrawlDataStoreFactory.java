/* Copyright 2014 Norconex Inc.
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
package com.norconex.collector.core.data.store;

import com.norconex.collector.core.crawler.ICrawlerConfig;

/**
 * Factory responsible for creating new crawl data stores.
 * @author Pascal Essiembre
 */
public interface ICrawlDataStoreFactory {

    /**
     * Creates a new crawl data store.
     * @param config crawler configuration
     * @param resume whether the crawler was started or resumed
     * @return new crawl data store
     */
    ICrawlDataStore createCrawlDataStore(
            ICrawlerConfig config, boolean resume);
}
