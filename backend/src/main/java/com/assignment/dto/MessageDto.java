package com.assignment.dto;

import org.springframework.http.HttpStatus;

public class MessageDto {

    private HttpStatus responseStatusCode;

    private String responseMessage;

    public MessageDto() {
    }

    public MessageDto(HttpStatus responseStatusCode, String responseMessage) {
        this.responseStatusCode = responseStatusCode;
        this.responseMessage = responseMessage;
    }

    public HttpStatus getResponseStatusCode() {
        return responseStatusCode;
    }

    public void setResponseStatusCode(HttpStatus responseStatusCode) {
        this.responseStatusCode = responseStatusCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "responseStatusCode=" + responseStatusCode +
                ", responseMessage='" + responseMessage + '\'' +
                '}';
    }
}
