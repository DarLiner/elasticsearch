/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */

package org.elasticsearch.cluster.ack;

/**
 * A cluster state update response with specific fields for index unfreezing.
 */
public class UnfreezeIndexClusterStateUpdateResponse extends ClusterStateUpdateResponse {

    private final boolean shardsAcknowledged;

    public UnfreezeIndexClusterStateUpdateResponse(boolean acknowledged, boolean shardsAcknowledged) {
        super(acknowledged);
        this.shardsAcknowledged = shardsAcknowledged;
    }

    /**
     * Returns whether the requisite number of shard copies started before the completion of the operation.
     */
    public boolean isShardsAcknowledged() {
        return shardsAcknowledged;
    }
}
