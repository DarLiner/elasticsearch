/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */

package org.elasticsearch.action.admin.indices.freeze;

import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.test.AbstractStreamableXContentTestCase;

public class FreezeIndexResponseTests extends AbstractStreamableXContentTestCase<FreezeIndexResponse> {

    @Override
    protected FreezeIndexResponse doParseInstance(XContentParser parser) {
        return FreezeIndexResponse.fromXContent(parser);
    }

    @Override
    protected FreezeIndexResponse createTestInstance() {
        return new FreezeIndexResponse(randomBoolean());
    }

    @Override
    protected FreezeIndexResponse createBlankInstance() {
        return new FreezeIndexResponse();
    }

    @Override
    protected FreezeIndexResponse mutateInstance(FreezeIndexResponse response) {
        return new FreezeIndexResponse(response.isAcknowledged() == false);
    }
}
