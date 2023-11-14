package com.vention.stockmarket.exceptions.handler;

import com.vention.stockmarket.exceptions.CustomFeignClientException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class RetrieveMessageErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        String reason = response.reason();
        throw new CustomFeignClientException(response.status(), response.reason(), response.body().toString());
    }
}