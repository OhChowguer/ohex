package br.com.ohexpress.ohex.interfaces;

import java.util.List;

import br.com.ohexpress.ohex.model.Loja;
import br.com.ohexpress.ohex.model.LojaPorDistancia;
import retrofit.Callback;
import retrofit.http.*;


public interface LojaService {


    @GET("/lojas")
    void listarLojas(Callback<List<LojaPorDistancia>> callback);

    @POST("/listalojas2/{tipo}")
    void listarLoja(@Header("Autentication") String token, @Path("tipo") int id, Callback<List<LojaPorDistancia>> callback);





}
