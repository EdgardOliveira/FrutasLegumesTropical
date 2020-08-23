package br.com.technologies.venom.frutastropicais;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RESTService {

    //consultar o webservice
    @GET("tfvjsonapi.php?")
    Call<ResultWrapper> consultarTodos(@Query("search") String fruta);

    @GET("tfvjsonapi.php?")
    Call<DetalheWrapper> consultarDetalhe(@Query("tfvitem") String fruta);
}