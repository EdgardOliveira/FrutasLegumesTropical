package br.com.technologies.venom.frutastropicais;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static br.com.technologies.venom.frutastropicais.Constantes.BASE_URL;
import static br.com.technologies.venom.frutastropicais.Constantes.TAG;

public class DetalheActivity extends AppCompatActivity {

    private TextInputEditText edtNome, edtBotNome, edtOutroNome, edtDescricao;
    private ImageView ivFoto;
    private Retrofit retrofit;
    private RESTService restService;
    private Detalhe detalhe;
    String fruta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        edtNome = findViewById(R.id.edtNome);
        edtBotNome = findViewById(R.id.edtNomeBotanico);
        edtOutroNome = findViewById(R.id.edtOutroNome);
        edtDescricao = findViewById(R.id.edtDescricao);
        ivFoto = findViewById(R.id.ivImagem);

        fruta = getIntent().getStringExtra("fruta");
    }

    @Override
    protected void onStart() {
        super.onStart();

        consumirAPI(fruta);
    }

    private void consumirAPI(String fruta) {
        //configurar o retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        restService = retrofit.create(RESTService.class);

        Call<DetalheWrapper> call = restService.consultarDetalhe(fruta);

        call.enqueue(new Callback<DetalheWrapper>() {
            @Override
            public void onResponse(Call<DetalheWrapper> call, Response<DetalheWrapper> response) {
                if (response.isSuccessful()) {
                    detalhe = response.body().getResults().get(0);
                    atualizarDadosTela();
                }else{
                    Log.d(TAG, "onResponse: deu ruim!!!");
                }
            }

            @Override
            public void onFailure(Call<DetalheWrapper> call, Throwable t) {
                Log.d(Constantes.TAG, "onFailure: Ocorreu um erro ao requisitar:" + t.getMessage() + "\nRequisição: " + call.request().toString());
            }
        });
    }

    private void atualizarDadosTela(){
        edtNome.setText(detalhe.getTfvname());
        edtBotNome.setText(detalhe.getBotname());
        edtOutroNome.setText(detalhe.getOthname());
        edtDescricao.setText(detalhe.getDescription());
        Glide.with(getApplicationContext()).load(detalhe.getImageurl()).apply(RequestOptions.centerInsideTransform()).into(ivFoto);
    }
}