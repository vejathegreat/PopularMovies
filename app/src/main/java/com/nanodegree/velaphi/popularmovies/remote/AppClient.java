package com.nanodegree.velaphi.popularmovies.remote;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppClient {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private final static String API_KEY_IDENTIFIER = "api_key";

    private static OkHttpClient getHttpClient() {
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalUrl = original.url();
                HttpUrl url = originalUrl.newBuilder()
                        .addQueryParameter(API_KEY_IDENTIFIER, "5028fde5e3652cfd9ac738403e7d0787")
                        .build();
                Request.Builder requestBuilder = original.newBuilder().url(url);
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        return httpClient.build();
    }

    public static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getHttpClient())
                .build();
    }

    public static RequestInterface getApi() {
        return getRetrofitInstance().create(RequestInterface.class);
    }
}
