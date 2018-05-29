/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */

package org.elasticsearch.action.admin.indices.freeze;

import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedRequestBuilder;
import org.elasticsearch.client.ElasticsearchClient;

/**
 * Builder for freeze index request
 */
public class FreezeIndexRequestBuilder extends
    AcknowledgedRequestBuilder<FreezeIndexRequest, FreezeIndexResponse, FreezeIndexRequestBuilder> {

    public FreezeIndexRequestBuilder(ElasticsearchClient client, FreezeIndexAction action) {
        super(client, action, new FreezeIndexRequest());
    }

    public FreezeIndexRequestBuilder(ElasticsearchClient client, FreezeIndexAction action, String... indices) {
        super(client, action, new FreezeIndexRequest(indices));
    }

    /**
     * Sets the indices to be frozen
     *
     * @param indices the indices to be frozen
     * @return the request itself
     */
    public FreezeIndexRequestBuilder setIndices(String... indices) {
        request.indices(indices);
        return this;
    }

    /**
     * Specifies what type of requested indices to ignore and wildcard indices expressions
     * For example indices that don't exist.
     *
     * @param indicesOptions the desired behaviour regarding indices to ignore and indices wildcard expressions
     * @return the request itself
     */
    public FreezeIndexRequestBuilder setIndicesOptions(IndicesOptions indicesOptions) {
        request.indicesOptions(indicesOptions);
        return this;
    }
}
