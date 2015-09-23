package br.com.ohexpress.ohex.interfaces;

import java.util.List;
import br.com.ohexpress.ohex.model.Loja;
import br.com.ohexpress.ohex.model.Pedido;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;

public interface PedidoService {

    @POST("/addpedido")
    void addPedido(@Header ("Autentication") String token, @Body Pedido pedido, Callback<Pedido> callback);

    @POST("/listarpedidosporcomprador")
    void listarPedidoPorComprador(@Header("Autentication") String token, Callback<List<Pedido>> pedidos);

    @GET("/listarpedidosporloja")
    void listarPedidoPorLoja(@Header("Autentication") String token,@Body Loja loja, Callback<List<Pedido>> pedidos);

    @GET("/setstatuspedidos/{id}/{status}")
    void setarStatusPedido( @Path("id") Long id, @Path("status") int status,Callback<List<Pedido>> pedidos);

}
