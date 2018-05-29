/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */
package org.elasticsearch.action.admin.indices.freeze;

import org.elasticsearch.cluster.ack.IndicesClusterStateUpdateRequest;

/**
 * Cluster state update request that allows to freeze one or more indices
 */
public class FreezeIndexClusterStateUpdateRequest extends IndicesClusterStateUpdateRequest<FreezeIndexClusterStateUpdateRequest> {

    FreezeIndexClusterStateUpdateRequest() {
    }
}
