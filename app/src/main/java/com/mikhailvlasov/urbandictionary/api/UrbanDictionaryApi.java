package com.mikhailvlasov.urbandictionary.api;

import com.mikhailvlasov.urbandictionary.api.models.Word;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UrbanDictionaryApi {
    @GET("search/{word}")
    Call<List<Word>> get(@Path("word") String word);
}
