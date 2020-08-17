package com.mikhailvlasov.urbandictionary.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;


import com.google.android.material.snackbar.Snackbar;
import com.mikhailvlasov.urbandictionary.R;
import com.mikhailvlasov.urbandictionary.utils.App;
import com.mikhailvlasov.urbandictionary.adapters.WordAdapter;
import com.mikhailvlasov.urbandictionary.api.models.Word;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.KeyEvent.KEYCODE_ENTER;

public class MainActivity extends AppCompatActivity {
EditText definiton_et;
RecyclerView mRecyclerView;
RecyclerView.LayoutManager mLayoutManager;
ImageButton seatch_bt;
WordAdapter mWordAdapter;
ProgressBar mProgressBar;
Toolbar toolbar;
View main_activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        definiton_et = findViewById(R.id.definition_et);
        mProgressBar = findViewById(R.id.progressBar);
        mRecyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        main_activity = findViewById(R.id.main);







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
        Call<List<Word>> call = App.getApi().get(definiton_et.getText().toString().toLowerCase());
        mProgressBar.setVisibility(ProgressBar.VISIBLE);
        call.enqueue(new Callback<List<Word>>() {
            @Override
            public void onResponse(Call<List<Word>> call, Response<List<Word>> response) {
                if(!response.isSuccessful()) {
                    mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                    Snackbar.make(
                            main_activity,
                            "Something went wrong",
                            Snackbar.LENGTH_SHORT
                    ).show();
                }
                List<Word> list = response.body();
                if(list.isEmpty()){
                    mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                    Snackbar.make(
                            main_activity,
                            "Nothing found",
                            Snackbar.LENGTH_SHORT
                    ).show();
                }else{
                    mWordAdapter = new WordAdapter(list);
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
