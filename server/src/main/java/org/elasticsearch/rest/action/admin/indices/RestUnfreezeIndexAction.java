/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */

package org.elasticsearch.rest.action.admin.indices;

import org.elasticsearch.action.admin.indices.unfreeze.UnfreezeIndexRequest;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.rest.BaseRestHandler;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.action.RestToXContentListener;

import java.io.IOException;

public class RestUnfreezeIndexAction extends BaseRestHandler {
    public RestUnfreezeIndexAction(Settings settings, RestController controller) {
        super(settings);
        controller.registerHandler(RestRequest.Method.POST, "/_unfreeze", this);
        controller.registerHandler(RestRequest.Method.POST, "/{index}/_unfreeze", this);
    }

    @Override
    public String getName() {
        return "unfreeze_index_action";
    }

    @Override
    public RestChannelConsumer prepareRequest(final RestRequest request, final NodeClient client) throws IOException {
        UnfreezeIndexRequest unfreezeIndexRequest = new UnfreezeIndexRequest(Strings.splitStringByCommaToArray(request.param("index")));
        unfreezeIndexRequest.timeout(request.paramAsTime("timeout", unfreezeIndexRequest.timeout()));
        unfreezeIndexRequest.masterNodeTimeout(request.paramAsTime("master_timeout", unfreezeIndexRequest.masterNodeTimeout()));
        unfreezeIndexRequest.indicesOptions(IndicesOptions.fromRequest(request, unfreezeIndexRequest.indicesOptions()));
        String waitForActiveShards = request.param("wait_for_active_shards");
        if (waitForActiveShards != null) {
            unfreezeIndexRequest.waitForActiveShards(ActiveShardCount.parseString(waitForActiveShards));
        }
        return channel -> client.admin().indices().unfreeze(unfreezeIndexRequest, new RestToXContentListener<>(channel));
    }

}
