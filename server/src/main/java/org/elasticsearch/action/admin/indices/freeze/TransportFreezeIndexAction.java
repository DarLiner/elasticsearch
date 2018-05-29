/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */

package org.elasticsearch.action.admin.indices.freeze;

import org.apache.logging.log4j.message.ParameterizedMessage;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.support.ActionFilters;
import org.elasticsearch.action.support.DestructiveOperations;
import org.elasticsearch.action.support.master.TransportMasterNodeAction;
import org.elasticsearch.cluster.ClusterState;
import org.elasticsearch.cluster.ack.ClusterStateUpdateResponse;
import org.elasticsearch.cluster.block.ClusterBlockException;
import org.elasticsearch.cluster.block.ClusterBlockLevel;
import org.elasticsearch.cluster.metadata.IndexNameExpressionResolver;
import org.elasticsearch.cluster.metadata.MetaDataIndexStateService;
import org.elasticsearch.cluster.service.ClusterService;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.Index;
import org.elasticsearch.tasks.Task;
import org.elasticsearch.threadpool.ThreadPool;
import org.elasticsearch.transport.TransportService;

/**
 * Freeze index action
 */
public class TransportFreezeIndexAction extends TransportMasterNodeAction<FreezeIndexRequest, FreezeIndexResponse> {

    private final MetaDataIndexStateService indexStateService;
    private final DestructiveOperations destructiveOperations;

    @Inject
    public TransportFreezeIndexAction(Settings settings, TransportService transportService, ClusterService clusterService,
                                      ThreadPool threadPool, MetaDataIndexStateService indexStateService,
                                      ActionFilters actionFilters, IndexNameExpressionResolver indexNameExpressionResolver,
                                      DestructiveOperations destructiveOperations) {
        super(settings, FreezeIndexAction.NAME, transportService, clusterService,
                threadPool, actionFilters, indexNameExpressionResolver, FreezeIndexRequest::new);
        this.indexStateService = indexStateService;
        this.destructiveOperations = destructiveOperations;
    }

    @Override
    protected String executor() {
        // no need to use a thread pool, we go async right away
        return ThreadPool.Names.SAME;
    }

    @Override
    protected FreezeIndexResponse newResponse() {
        return new FreezeIndexResponse();
    }

    @Override
    protected void doExecute(Task task, FreezeIndexRequest request, ActionListener<FreezeIndexResponse> listener) {
        destructiveOperations.failDestructive(request.indices());
        super.doExecute(task, request, listener);
    }

    @Override
    protected ClusterBlockException checkBlock(FreezeIndexRequest request, ClusterState state) {
        return state.blocks().indicesBlockedException(ClusterBlockLevel.METADATA_WRITE,
                indexNameExpressionResolver.concreteIndexNames(state, request));
    }

    @Override
    protected void masterOperation(final FreezeIndexRequest request, final ClusterState state,
                                   final ActionListener<FreezeIndexResponse> listener) {
        final Index[] concreteIndices = indexNameExpressionResolver.concreteIndices(state, request);
        if (concreteIndices == null || concreteIndices.length == 0) {
            listener.onResponse(new FreezeIndexResponse(true));
            return;
        }
        FreezeIndexClusterStateUpdateRequest updateRequest = new FreezeIndexClusterStateUpdateRequest()
                .ackTimeout(request.timeout()).masterNodeTimeout(request.masterNodeTimeout())
                .indices(concreteIndices);

        indexStateService.freezeIndex(updateRequest, new ActionListener<ClusterStateUpdateResponse>() {

            @Override
            public void onResponse(ClusterStateUpdateResponse response) {
                listener.onResponse(new FreezeIndexResponse(response.isAcknowledged()));
            }

            @Override
            public void onFailure(Exception t) {
                logger.debug(() -> new ParameterizedMessage("failed to freeze indices [{}]", (Object) concreteIndices), t);
                listener.onFailure(t);
            }
        });
    }
}
