/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */

package org.elasticsearch.action.admin.indices.unfreeze;

import org.elasticsearch.action.support.master.ShardsAcknowledgedResponse;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.xcontent.ConstructingObjectParser;
import org.elasticsearch.common.xcontent.XContentParser;

import java.io.IOException;

/**
 * A response for an unfreeze index action.
 */
public class UnfreezeIndexResponse extends ShardsAcknowledgedResponse {

    private static final ConstructingObjectParser<UnfreezeIndexResponse, Void> PARSER =
        new ConstructingObjectParser<>("unfreeze_index", true,
            args -> new UnfreezeIndexResponse((boolean) args[0], (boolean) args[1]));

    static {
        declareAcknowledgedAndShardsAcknowledgedFields(PARSER);
    }

    UnfreezeIndexResponse() {
    }

    UnfreezeIndexResponse(boolean acknowledged, boolean shardsAcknowledged) {
        super(acknowledged, shardsAcknowledged);
    }

    @Override
    public void readFrom(StreamInput in) throws IOException {
        super.readFrom(in);
        readAcknowledged(in);
        readShardsAcknowledged(in);
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        super.writeTo(out);
        writeAcknowledged(out);
        writeShardsAcknowledged(out);
    }

    public static UnfreezeIndexResponse fromXContent(XContentParser parser) {
        return PARSER.apply(parser, null);
    }
}
