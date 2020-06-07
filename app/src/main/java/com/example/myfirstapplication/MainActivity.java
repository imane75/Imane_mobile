package com.example.myfirstapplication;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static final String BASE_URL = "https://raw.githubusercontent.com/imane75/Imane_mobile/master/app/src/main/java/com/example/myfirstapplication/";
    private SharedPreferences sharedPreferences;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("application_esiea", Context.MODE_PRIVATE);
        gson = new GsonBuilder()
                .setLenient()
                .create();

        //List<Pays> listPays = getDataFromCache();

//        if(listPays != null){
//            showList(listPays);
//        } else {
        makeAPIcall();
//        }


    }

    private List<Pays> getDataFromCache() {
        String jsonPays = sharedPreferences.getString(Constants.KEY_PAYS_LIST, null);

        if(jsonPays == null){
            return null;
        } else {
            Type listType = new TypeToken<List<Pays>>(){}.getType();
            return gson.fromJson(jsonPays, listType);
        }

    }
    private void showList(List<Pays> listPays) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        final SwipeRefreshLayout swiperefresh = findViewById(R.id.swiperefresh);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swiperefresh.setRefreshing(false);
            }
        });
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ListAdapter(listPays, MainActivity.this);
        recyclerView.setAdapter(mAdapter);
    }


    private void makeAPIcall() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        PaysAPI paysApi = retrofit.create(PaysAPI.class);

        Call<RestPaysResponse> call = paysApi.getPaysResponse();
        call.enqueue(new Callback<RestPaysResponse>() {
            @Override
            public void onResponse(Call<RestPaysResponse> call, Response<RestPaysResponse> response) {
                if(response.isSuccessful() && response.body() != null) {
                    List<Pays> listPays = response.body().getResults();
                    Toast.makeText(getApplicationContext(), "API SUCCESS", Toast.LENGTH_SHORT).show();
                    saveList(listPays);
                    showList(listPays);
                } else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<RestPaysResponse> call, Throwable t) {
                showError();
            }
        });
    }

    private void saveList(List<Pays> listPays) {
        String jsonString = gson.toJson(listPays);

        sharedPreferences
                .edit()
                .putString(Constants.KEY_PAYS_LIST, jsonString)
                .apply();

        Toast.makeText(getApplicationContext(), "List Saved", Toast.LENGTH_SHORT).show();

    }

    private void showError() {
        //Merci Monsieur pour vos explications <3
        Toast.makeText(getApplicationContext(), "API ERROR", Toast.LENGTH_SHORT).show();
    }
}
