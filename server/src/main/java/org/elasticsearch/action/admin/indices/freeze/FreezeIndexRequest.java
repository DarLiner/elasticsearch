/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */

package org.elasticsearch.action.admin.indices.freeze;

import org.elasticsearch.action.ActionRequestValidationException;
import org.elasticsearch.action.IndicesRequest;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedRequest;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.util.CollectionUtils;

import java.io.IOException;

import static org.elasticsearch.action.ValidateActions.addValidationError;

/**
 * A request to freeze an index.
 */
public class FreezeIndexRequest extends AcknowledgedRequest<FreezeIndexRequest> implements IndicesRequest.Replaceable {

    private String[] indices;
    private IndicesOptions indicesOptions = IndicesOptions.strictExpandOpen();

    public FreezeIndexRequest() {
    }

    /**
     * Constructs a new freeze index request for the specified index.
     */
    public FreezeIndexRequest(String... indices) {
        this.indices = indices;
    }

    @Override
    public ActionRequestValidationException validate() {
        ActionRequestValidationException validationException = null;
        if (CollectionUtils.isEmpty(indices)) {
            validationException = addValidationError("index is missing", validationException);
        }
        return validationException;
    }

    /**
     * The indices to be frozen
     * @return the indices to be frozen
     */
    @Override
    public String[] indices() {
        return indices;
    }

    /**
     * Sets the indices to be frozen
     * @param indices the indices to be frozen
     * @return the request itself
     */
    @Override
    public FreezeIndexRequest indices(String... indices) {
        this.indices = indices;
        return this;
    }

    /**
     * Specifies what type of requested indices to ignore and how to deal with wildcard expressions.
     * For example indices that don't exist.
     *
     * @return the desired behaviour regarding indices to ignore and wildcard indices expressions
     */
    @Override
    public IndicesOptions indicesOptions() {
        return indicesOptions;
    }

    /**
     * Specifies what type of requested indices to ignore and how to deal wild wildcard expressions.
     * For example indices that don't exist.
     *
     * @param indicesOptions the desired behaviour regarding indices to ignore and wildcard indices expressions
     * @return the request itself
     */
    public FreezeIndexRequest indicesOptions(IndicesOptions indicesOptions) {
        this.indicesOptions = indicesOptions;
        return this;
    }

    @Override
    public void readFrom(StreamInput in) throws IOException {
        super.readFrom(in);
        indices = in.readStringArray();
        indicesOptions = IndicesOptions.readIndicesOptions(in);
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        super.writeTo(out);
        out.writeStringArray(indices);
        indicesOptions.writeIndicesOptions(out);
    }
}
