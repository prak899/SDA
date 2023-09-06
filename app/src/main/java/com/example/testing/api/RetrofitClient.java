package com.example.testing.api;
import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://reqres.in/";
    private static final String HOSTNAME = "api.example.com";

    // Replace with your actual pins
    private static final String PIN1 = "sha256/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=";
    private static final String PIN2 = "sha256/BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB=";

    public static Retrofit getClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .certificatePinner(new CertificatePinner.Builder()
                        .add(HOSTNAME, PIN1)
                        .add(HOSTNAME, PIN2)
                        .build())
                .build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
