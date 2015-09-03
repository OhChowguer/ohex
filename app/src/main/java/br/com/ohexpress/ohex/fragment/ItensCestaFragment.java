package br.com.ohexpress.ohex.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.ohexpress.ohex.LojaActivity;
import br.com.ohexpress.ohex.MyApplication;
import br.com.ohexpress.ohex.ProdutoActivity;
import br.com.ohexpress.ohex.R;
import br.com.ohexpress.ohex.adapters.CestaAdapter;
import br.com.ohexpress.ohex.adapters.ProdutoAdapter;
import br.com.ohexpress.ohex.interfaces.RecyclerViewOnClickListenerHack;
import br.com.ohexpress.ohex.model.ItemPedido;
import br.com.ohexpress.ohex.model.Produto;


public class ItensCestaFragment extends Fragment implements RecyclerViewOnClickListenerHack {


    private RecyclerView mRecyclerView;
    private List<ItemPedido> cesta = new ArrayList<ItemPedido>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cesta, container, false);

        cesta = ((MyApplication) getActivity().getApplication()).getMyPedido().getItem();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_itens_cesta_act);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager llm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                CestaAdapter adapter = (CestaAdapter) mRecyclerView.getAdapter();

                if (cesta.size() == llm.findFirstCompletelyVisibleItemPosition() + 1) {


                    //Toast.makeText(getActivity(), "teste", Toast.LENGTH_SHORT);

                    //Carrega mais itens 26min video de recyclerView
                }

            }
        });


        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);


        CestaAdapter adapter = new CestaAdapter(getActivity(), cesta);
        adapter.setRecyclerViewOnClickListenerHack(this);
        mRecyclerView.setAdapter(adapter);


        return view;


    }


    @Override
    public void onClickListener(View view, int position) {




        //Toast.makeText(getActivity(), "Position: "+position, Toast.LENGTH_SHORT).show();

        //Intent itLProx = new Intent(getActivity(), ProdutoActivity.class);
        //itLProx.putExtra("produto", lista.get(position));
        //startActivity(itLProx);
    }




}
