package br.com.ohexpress.ohex.interfaces;

import java.util.List;

import br.com.ohexpress.ohex.model.ItemPedido;
import br.com.ohexpress.ohex.model.LojaPorDistancia;
import br.com.ohexpress.ohex.model.Pedido;
import br.com.ohexpress.ohex.model.Usuario;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by rafaelchowman on 19/07/15.
 */
public interface UserService {




    @POST("/autentic")
    void gettoken(@Header("user") String user,@Header("senha") String senha, Callback<String> callback);

    @POST("/registrar")
    void registrar(@Header("senha") String senha,@Body Usuario user, Callback<Usuario> callback);

    @POST("/buscausuario")
    void getuser(@Header("Autentication") String token, Callback<Usuario> callback);

    @POST("/listarlojasfavoritas")
    void listarfavoritas(@Header("Autentication") String token, Callback<List<LojaPorDistancia>> callback);

    @POST("/actionfavorito")
    void actionfavoritas(@Header("Autentication") String token, @Header("id") Long id, Callback<String> callback);

    //@GET("/setstatuspedidos/{id}/{status}")
    //void setarStatusPedido(@Path("id") Long id, @Path("status") int status, Callback<List<Pedido>> pedidos);




}
