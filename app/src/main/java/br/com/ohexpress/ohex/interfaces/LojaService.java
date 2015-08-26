package br.com.ohexpress.ohex.interfaces;

import java.util.List;

import br.com.ohexpress.ohex.model.Loja;
import br.com.ohexpress.ohex.model.LojaPorDistancia;
import retrofit.Callback;
import retrofit.http.*;

/**
 * Created by rafaelchowman on 19/07/15.
 */
public interface LojaService {

    String LINK_LOJAS = "/lojas";


    @GET("/")
    void listarLojas(Callback<List<Loja>> callback);

    @GET("/")
    void teste(Callback<List<String>> nome);

    @GET("/")
    void listarLojasDist(Callback<List<LojaPorDistancia>> callback);



}
