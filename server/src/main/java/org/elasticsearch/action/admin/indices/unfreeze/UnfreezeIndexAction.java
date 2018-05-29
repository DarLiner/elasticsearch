/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */

package org.elasticsearch.action.admin.indices.unfreeze;

import org.elasticsearch.action.Action;
import org.elasticsearch.client.ElasticsearchClient;

public class UnfreezeIndexAction extends Action<UnfreezeIndexRequest, UnfreezeIndexResponse, UnfreezeIndexRequestBuilder> {

    public static final UnfreezeIndexAction INSTANCE = new UnfreezeIndexAction();
    public static final String NAME = "indices:admin/unfreeze";

    private UnfreezeIndexAction() {
        super(NAME);
    }

    @Override
    public UnfreezeIndexResponse newResponse() {
        return new UnfreezeIndexResponse();
    }

    @Override
    public UnfreezeIndexRequestBuilder newRequestBuilder(ElasticsearchClient client) {
        return new UnfreezeIndexRequestBuilder(client, this);
    }
}
