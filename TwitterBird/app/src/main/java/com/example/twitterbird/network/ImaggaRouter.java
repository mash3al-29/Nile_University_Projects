// ImaggaRouter.java
package com.example.twitterbird.network;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ImaggaRouter {

    private static final String BASE_URL = "http://api.imagga.com/v1";
    private static final String AUTH_TOKEN = Credentials.basic("YWNjXzM5MWRkMjU2NjE4ZWU0Mzo2NWFjODVmMzNhNWE1YmQ3YTMzZGJkNzFjNTlmYTM5Yw==", "");

    public enum Endpoint {
        CONTENT("content"),
        TAGS("tagging"),
        COLORS("colors");

        private String path;

        Endpoint(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }

    public Request buildRequest(Endpoint endpoint, String contentID) {
        OkHttpClient client = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder()
                .url(BASE_URL + "/" + endpoint.getPath())
                .addHeader("Authorization", AUTH_TOKEN);

        if (endpoint == Endpoint.CONTENT) {
            requestBuilder.post(RequestBody.create(null, new byte[0]));
        } else {
            requestBuilder.get();
        }

        return requestBuilder.build();
    }

    // Add methods to execute the requests and handle responses...
}