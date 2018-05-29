/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */

package org.elasticsearch.action.admin.indices.unfreeze;

import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.test.AbstractStreamableXContentTestCase;

public class UnfreezeIndexResponseTests extends AbstractStreamableXContentTestCase<UnfreezeIndexResponse> {

    @Override
    protected UnfreezeIndexResponse doParseInstance(XContentParser parser) {
        return UnfreezeIndexResponse.fromXContent(parser);
    }

    @Override
    protected UnfreezeIndexResponse createTestInstance() {
        boolean acknowledged = randomBoolean();
        boolean shardsAcknowledged = acknowledged && randomBoolean();
        return new UnfreezeIndexResponse(acknowledged, shardsAcknowledged);
    }

    @Override
    protected UnfreezeIndexResponse createBlankInstance() {
        return new UnfreezeIndexResponse();
    }

    @Override
    protected UnfreezeIndexResponse mutateInstance(UnfreezeIndexResponse response) {
        if (randomBoolean()) {
            boolean acknowledged = response.isAcknowledged() == false;
            boolean shardsAcknowledged = acknowledged && response.isShardsAcknowledged();
            return new UnfreezeIndexResponse(acknowledged, shardsAcknowledged);
        } else {
            boolean shardsAcknowledged = response.isShardsAcknowledged() == false;
            boolean acknowledged = shardsAcknowledged || response.isAcknowledged();
            return new UnfreezeIndexResponse(acknowledged, shardsAcknowledged);
        }
    }
}
