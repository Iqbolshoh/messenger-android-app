package uz.iqbolshoh.messenger;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

class ApiConfig {

    private static final String BASE_URL = "https://messenger.iqbolshoh.uz/api/";

    public static String getApiResponse(String endpoint) throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(BASE_URL + endpoint)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            } else {
                throw new Exception("Response Code: " + response.code());
            }
        }
    }

    public static String postApiResponse(String endpoint, String jsonBody) throws Exception {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(jsonBody, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(BASE_URL + endpoint)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            } else {
                throw new Exception("Response Code: " + response.code());
            }
        }
    }

}

