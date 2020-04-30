package com.example.makank.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Filresponse {
    @SerializedName("response")
    @Expose
    private Response response;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("status_code")
    @Expose
    private Integer statusCode;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public class Response {

        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("message")
        @Expose
        private String message;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }
}
