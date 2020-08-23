package br.com.technologies.venom.frutastropicais;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static br.com.technologies.venom.frutastropicais.Constantes.BASE_URL;
import static br.com.technologies.venom.frutastropicais.Constantes.TAG;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,
        RecyclerItemClickListener.OnItemClickListener {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private ResultAdapter frutaAdapter;
    private Retrofit retrofit;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RESTService restService;
    private List<Result> resultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swpFrutas);
        recyclerView = findViewById(R.id.rvFrutas);
        resultList = new ArrayList<>();

        configurarRecyclerView();
        configurarSwipeRefresh();

        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView, this));
    }

    private void configurarRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        frutaAdapter = new ResultAdapter();
        recyclerView.setAdapter(frutaAdapter);
    }

    private void configurarSwipeRefresh() {
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_filtrar).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Filtrar...");
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                frutaAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                frutaAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_filtrar) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onRefresh() {
        consumirAPI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        consumirAPI();
    }

    private void consumirAPI() {
        //configurar o retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        restService = retrofit.create(RESTService.class);

        Call<ResultWrapper> call = restService.consultarTodos("all");

        call.enqueue(new Callback<ResultWrapper>() {
            @Override
            public void onResponse(Call<ResultWrapper> call, Response<ResultWrapper> response) {
                if (response.isSuccessful()) {
                    resultList = response.body().getResults();
                    frutaAdapter.setFrutaList(getApplicationContext(), resultList);
                } else {
                    Log.d(TAG, "onResponse: Ocorreu um erro ao ter uma resposta. Erro:" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ResultWrapper> call, Throwable t) {
                Log.d(Constantes.TAG, "onFailure: Ocorreu um erro ao requisitar:" +
                        t.getMessage() + "\nRequisição: " + call.request().toString());
            }
        });

        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onItemClick(View view, int position) {
        final String fruta = frutaAdapter.getFilteredList().get((int) frutaAdapter.getItemId(position)).getTfvname();
        Intent intent = new Intent(getApplicationContext(), DetalheActivity.class);
        intent.putExtra("fruta", fruta);
        startActivity(intent);
    }

    @Override
    public void onLongItemClick(View view, int position) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}