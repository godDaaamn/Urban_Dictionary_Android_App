package com.mikhailvlasov.urbandictionary.utils;

import android.app.Application;

import com.mikhailvlasov.urbandictionary.api.UrbanDictionaryApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    private static UrbanDictionaryApi mUrbanDictionaryApi;
    @Override
    public void onCreate() {
        super.onCreate();

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://urbanscraper.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        mUrbanDictionaryApi = retrofit.create(UrbanDictionaryApi.class);
    }
    public static UrbanDictionaryApi getApi(){
        return mUrbanDictionaryApi;
    }
}
