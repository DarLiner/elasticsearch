/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */

package org.elasticsearch.rest.action.admin.indices;

import org.elasticsearch.action.admin.indices.freeze.FreezeIndexRequest;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.rest.BaseRestHandler;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.action.RestToXContentListener;

import java.io.IOException;

public class RestFreezeIndexAction extends BaseRestHandler {
    public RestFreezeIndexAction(Settings settings, RestController controller) {
        super(settings);
        controller.registerHandler(RestRequest.Method.POST, "/_freeze", this);
        controller.registerHandler(RestRequest.Method.POST, "/{index}/_freeze", this);
    }

    @Override
    public String getName() {
        return "freeze_index_action";
    }

    @Override
    public RestChannelConsumer prepareRequest(final RestRequest request, final NodeClient client) throws IOException {
        FreezeIndexRequest freezeIndexRequest = new FreezeIndexRequest(Strings.splitStringByCommaToArray(request.param("index")));
        freezeIndexRequest.masterNodeTimeout(request.paramAsTime("master_timeout", freezeIndexRequest.masterNodeTimeout()));
        freezeIndexRequest.timeout(request.paramAsTime("timeout", freezeIndexRequest.timeout()));
        freezeIndexRequest.indicesOptions(IndicesOptions.fromRequest(request, freezeIndexRequest.indicesOptions()));
        return channel -> client.admin().indices().freeze(freezeIndexRequest, new RestToXContentListener<>(channel));
    }

}
