package br.com.ohexpress.ohex.util;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.ohexpress.ohex.EstabelecimentoMainActivity;
import br.com.ohexpress.ohex.MainActivity;
import br.com.ohexpress.ohex.interfaces.PedidoService;
import br.com.ohexpress.ohex.model.Pedido;
import br.com.ohexpress.ohex.model.Usuario;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by rafaelchowman on 10/08/15.
 */
public class PedidosUtil {

    private ArrayList<Pedido> listaPedido = new ArrayList<Pedido>(0);
    private Context context;
    private RestAdapter restAdapterPedido = new RestAdapter.Builder().setEndpoint("http://10.0.3.2:8080/ohexpress/phone").build();
    private PedidoService pedidoService = restAdapterPedido.create(PedidoService.class);


    public void getPedidos(Context c, Usuario user){
        context = c;

        pedidoService.listarPedidoPorComprador(user.getToken(),
                new Callback<List<Pedido>>() {


                    @Override
                    public void success(List<Pedido> pedidos, Response response) {

                        listaPedido = (ArrayList<Pedido>) pedidos;
                        Intent itLProx = new Intent(context, EstabelecimentoMainActivity.class);
                        itLProx.putParcelableArrayListExtra("pedidos", listaPedido);
                        context.startActivity(itLProx);


                    }

                    @Override
                    public void failure(RetrofitError error) {

                        Toast.makeText(context, "Deu errado" + error, Toast.LENGTH_LONG).show();


                    }
                }

        );




    }

/*
    public void getListaPedidos(){

        pedidoService.listarPedido(
                new Callback<List<Pedido>>() {


                    @Override
                    public void success(List<Pedido> pedidos, Response response) {

                        listaPedido = (ArrayList<Pedido>) pedidos;



                    }

                    @Override
                    public void failure(RetrofitError error) {

                        //Toast.makeText(getActivity(), "Deu errado" + error, Toast.LENGTH_LONG).show();


                    }
                }

        );



    return ;
    }
*/
    public List<Pedido> setStatusPedidos(Long id, int status, Context c){
        context = c;

        pedidoService.setarStatusPedido(id,status,
                new Callback<List<Pedido>>() {


                    @Override
                    public void success(List<Pedido> pedidos, Response response) {

                        listaPedido = (ArrayList<Pedido>) pedidos;

                        ((EstabelecimentoMainActivity) context).getFrag(pedidos);


                    }

                    @Override
                    public void failure(RetrofitError error) {

                        //Toast.makeText(getActivity(), "Deu errado" + error, Toast.LENGTH_LONG).show();


                    }
                }

        );



        return listaPedido;
    }


}
