/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */

package org.elasticsearch.action.admin.indices.freeze;

import org.elasticsearch.action.Action;
import org.elasticsearch.client.ElasticsearchClient;

public class FreezeIndexAction extends Action<FreezeIndexRequest, FreezeIndexResponse, FreezeIndexRequestBuilder> {

    public static final FreezeIndexAction INSTANCE = new FreezeIndexAction();
    public static final String NAME = "indices:admin/freeze";

    private FreezeIndexAction() {
        super(NAME);
    }

    @Override
    public FreezeIndexResponse newResponse() {
        return new FreezeIndexResponse();
    }

    @Override
    public FreezeIndexRequestBuilder newRequestBuilder(ElasticsearchClient client) {
        return new FreezeIndexRequestBuilder(client, this);
    }
}
