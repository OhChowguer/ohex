package br.com.ohexpress.ohex.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import br.com.ohexpress.ohex.ListaPedidoActivity;
import br.com.ohexpress.ohex.LojaActivity;
import br.com.ohexpress.ohex.LojasProximasActivity;
import br.com.ohexpress.ohex.PedidoActivity;
import br.com.ohexpress.ohex.R;
import br.com.ohexpress.ohex.adapters.LojasAdapter;
import br.com.ohexpress.ohex.adapters.PedidoAdapter;
import br.com.ohexpress.ohex.interfaces.RecyclerViewOnClickListenerHack;
import br.com.ohexpress.ohex.model.Loja;
import br.com.ohexpress.ohex.model.Pedido;


public class PedidoFragment extends Fragment implements RecyclerViewOnClickListenerHack {


    private RecyclerView mRecyclerView;
    private List<Pedido> listaPedido = new ArrayList<Pedido>(0);
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pedidos, container, false);

;
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar_pedidos);
        progressBar.setVisibility(View.VISIBLE);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_lista_pedido);
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
                PedidoAdapter adapter = (PedidoAdapter) mRecyclerView.getAdapter();

                if (lista.size() == llm.findFirstCompletelyVisibleItemPosition() + 1) {


                    //Toast.makeText(getActivity(), "teste", Toast.LENGTH_SHORT);

                    //Carrega mais itens 26min video de recyclerView
                }

            }
        });
    */

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);






        return view;


    }


    @Override
    public void onClickListener(View view, int position) {
        //Toast.makeText(getActivity(), "Position: "+position, Toast.LENGTH_SHORT).show();

        Intent itLProx = new Intent(getActivity(), PedidoActivity.class);
        itLProx.putExtra("pedido", listaPedido.get(position));
        startActivity(itLProx);
    }


    public void loadPedidos(List<Pedido> pedidos){

        listaPedido = pedidos;
        PedidoAdapter adapter = new PedidoAdapter(getActivity(), listaPedido);
        adapter.setRecyclerViewOnClickListenerHack(this);
        mRecyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);

    }

    public void removeProgress(){

        progressBar.setVisibility(View.GONE);

    }


}
