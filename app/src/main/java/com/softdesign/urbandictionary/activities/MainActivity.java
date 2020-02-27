package com.softdesign.urbandictionary.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.softdesign.urbandictionary.R;
import com.softdesign.urbandictionary.utils.SpacesItemDecoration;
import com.softdesign.urbandictionary.adapters.WordAdapter;
import com.softdesign.urbandictionary.api.UrbanDictionaryApi;
import com.softdesign.urbandictionary.api.models.Word;

import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.view.KeyEvent.KEYCODE_ENTER;

public class MainActivity extends AppCompatActivity {
EditText definiton_et;
RecyclerView mRecyclerView;
UrbanDictionaryApi mUrbanDictionaryApi;
ImageButton seatch_bt;
WordAdapter mWordAdapter;
ProgressBar mProgressBar;
Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        definiton_et = findViewById(R.id.definition_et);
        mProgressBar = findViewById(R.id.progressBar);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(15));



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


        definiton_et.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                boolean consumed = false;
                if (keyCode == KEYCODE_ENTER) {
                    searchDefinitions();
                    ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    consumed = true;
                }
                return consumed;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_search) {
            searchDefinitions();
            ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE))
            .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        return true;
    }

    private void searchDefinitions(){
        Call<List<Word>> call = mUrbanDictionaryApi.get(definiton_et.getText().toString().toLowerCase());
        mProgressBar.setVisibility(ProgressBar.VISIBLE);
        call.enqueue(new Callback<List<Word>>() {
            @Override
            public void onResponse(Call<List<Word>> call, Response<List<Word>> response) {
                if(!response.isSuccessful()) {
                    mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Something went wrong",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                List<Word> list = response.body();
                if(list.isEmpty()){
                    mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                    Toast toast = Toast.makeText(getApplicationContext(),"Nothing found",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }else{
                    mWordAdapter = new WordAdapter(list,MainActivity.this);
                    mRecyclerView.setAdapter(mWordAdapter);
                    mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                }

            }

            @Override
            public void onFailure(Call<List<Word>> call, Throwable t) {

            }
        });

    }




}
