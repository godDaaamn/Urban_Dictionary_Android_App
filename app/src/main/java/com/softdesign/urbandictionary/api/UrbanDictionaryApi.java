package com.softdesign.urbandictionary.api;

import com.softdesign.urbandictionary.api.models.Word;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UrbanDictionaryApi {
    @GET("search/{word}")
    Call<List<Word>> get(@Path("word") String word);
}
