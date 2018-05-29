/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */

package org.elasticsearch.action.admin.indices.freeze;

import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.xcontent.ConstructingObjectParser;
import org.elasticsearch.common.xcontent.XContentParser;

import java.io.IOException;

/**
 * A response for a freeze index action.
 */
public class FreezeIndexResponse extends AcknowledgedResponse {
    private static final ConstructingObjectParser<FreezeIndexResponse, Void> PARSER = new ConstructingObjectParser<>("freeze_index", true,
            args -> new FreezeIndexResponse((boolean) args[0]));

    static {
        declareAcknowledgedField(PARSER);
    }

    FreezeIndexResponse() {
    }

    FreezeIndexResponse(boolean acknowledged) {
        super(acknowledged);
    }

    @Override
    public void readFrom(StreamInput in) throws IOException {
        super.readFrom(in);
        readAcknowledged(in);
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        super.writeTo(out);
        writeAcknowledged(out);
    }

    public static FreezeIndexResponse fromXContent(XContentParser parser) {
        return PARSER.apply(parser, null);
    }
}
