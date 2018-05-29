/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */

package org.elasticsearch.action.admin.indices.unfreeze;

import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.cluster.ack.IndicesClusterStateUpdateRequest;

/**
 * Cluster state update request that allows to unfreeze one or more indices
 */
public class UnfreezeIndexClusterStateUpdateRequest extends IndicesClusterStateUpdateRequest<UnfreezeIndexClusterStateUpdateRequest> {

    private ActiveShardCount waitForActiveShards = ActiveShardCount.DEFAULT;

    UnfreezeIndexClusterStateUpdateRequest() {

    }

    public ActiveShardCount waitForActiveShards() {
        return waitForActiveShards;
    }

    public UnfreezeIndexClusterStateUpdateRequest waitForActiveShards(ActiveShardCount waitForActiveShards) {
        this.waitForActiveShards = waitForActiveShards;
        return this;
    }
}
