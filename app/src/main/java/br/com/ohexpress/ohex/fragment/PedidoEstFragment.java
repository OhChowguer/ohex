package br.com.ohexpress.ohex.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.ohexpress.ohex.EstabelecimentoMainActivity;
import br.com.ohexpress.ohex.EstabelecimentoPedidosActivity;
import br.com.ohexpress.ohex.ListaPedidoActivity;
import br.com.ohexpress.ohex.PedidoActivity;
import br.com.ohexpress.ohex.R;
import br.com.ohexpress.ohex.adapters.PedidoAdapter;
import br.com.ohexpress.ohex.adapters.PedidoEstAdapter;
import br.com.ohexpress.ohex.adapters.PedidoEstProdAdapter;
import br.com.ohexpress.ohex.interfaces.RecyclerViewOnClickListenerHack;
import br.com.ohexpress.ohex.model.ItemPedido;
import br.com.ohexpress.ohex.model.Pedido;
import br.com.ohexpress.ohex.model.Produto;


public class PedidoEstFragment extends Fragment implements RecyclerViewOnClickListenerHack {


    private RecyclerView mRecyclerView;
    private List<Pedido> lista = new ArrayList<Pedido>(0);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_est_pedidos, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_lista_pedido_est);
        mRecyclerView.setHasFixedSize(true);
        /*
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager llm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                PedidoEstAdapter adapter = (PedidoEstAdapter) mRecyclerView.getAdapter();

                if (lista.size() == llm.findFirstCompletelyVisibleItemPosition() + 1) {


                    //Toast.makeText(getActivity(), "teste", Toast.LENGTH_SHORT);

                    //Carrega mais itens 26min video de recyclerView
                }

            }
        });*/


        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);


        /*Pedido pedido = new Pedido();
        Pedido pedido2 = new Pedido();
        Pedido pedido3 = new Pedido();
        Pedido pedido4 = new Pedido();
        pedido.setId(1L);
        pedido.setStatus(1);
        lista.add(pedido);
        pedido2.setStatus(2);
        lista.add(pedido2);
        pedido3.setStatus(3);
        lista.add(pedido3);
        pedido4.setStatus(4);
        lista.add(pedido4);*/


       //lista = ((EstabelecimentoPedidosActivity) getActivity()).getSetPedidosList();
       // lista = ((EstabelecimentoMainActivity) getActivity()).filterForStatus1(((EstabelecimentoMainActivity) getActivity()).getSetPedidosList(),1);
        PedidoEstAdapter adapter = new PedidoEstAdapter(getActivity(), lista);
        adapter.setRecyclerViewOnClickListenerHack(this);
        mRecyclerView.setAdapter(adapter);


        return view;


    }


    @Override
    public void onClickListener(View view, int position) {
        Toast.makeText(getActivity(), "Position: " + position, Toast.LENGTH_SHORT).show();


    }

    public void setNewListaPedidos(List<Pedido> pedidos) {

        lista = pedidos;
        PedidoEstAdapter adapter = new PedidoEstAdapter(getActivity(), pedidos);
        mRecyclerView.setAdapter(adapter);


    }

    public void trocarAdapter(int tipo) {

        if (tipo == 1){

            PedidoEstAdapter adapter = new PedidoEstAdapter(getActivity(), lista);
            mRecyclerView.setAdapter(adapter);

        }
        if (tipo == 2){

            PedidoEstAdapter adapter = new PedidoEstAdapter(getActivity(), lista);
            mRecyclerView.setAdapter(adapter);

        }



    }

    public void loadPedidosPorLoja(List<Pedido> pedidos){

        lista = pedidos;
        PedidoEstAdapter adapter = new PedidoEstAdapter(getActivity(), lista);
        //adapter.setRecyclerViewOnClickListenerHack(this);
        mRecyclerView.setAdapter(adapter);
        //progressBar.setVisibility(View.GONE);

    }

    public void listarProdutosConfec(){

        List<Produto> produtos = new ArrayList<Produto>();

        for (Pedido pedido:lista){

            for (ItemPedido itemPedido:pedido.getItem()){
                produtos.add(itemPedido.getProduto());
            }

        }
        PedidoEstProdAdapter adapter = new PedidoEstProdAdapter(getActivity(), produtos);
        //adapter.setRecyclerViewOnClickListenerHack(this);
        mRecyclerView.setAdapter(adapter);




    }



}
