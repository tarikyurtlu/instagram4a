package com.bodiart.instagram4a.requests.other;


import androidx.annotation.NonNull;

import com.bodiart.instagram4a.payload.base.InstagramSyncFeaturesPayload;
import com.bodiart.instagram4a.payload.base.InstagramSyncFeaturesResult;
import com.bodiart.instagram4a.requests.base.InstagramPostRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by root on 09/06/17.
 */

public class InstagramSyncFeaturesRequest extends InstagramPostRequest<InstagramSyncFeaturesResult> {

    @NonNull
    private InstagramSyncFeaturesPayload payload;

    public InstagramSyncFeaturesRequest(@NonNull InstagramSyncFeaturesPayload payload) {
        this.payload = payload;
    }

    @Override
    public String getUrl() {
        return "qe/sync/";
    }

    @Override
    public String getPayload() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(payload);
    }

    @Override
    public InstagramSyncFeaturesResult parseResult(int statusCode, String content) {
        return new InstagramSyncFeaturesResult();
    }


}
