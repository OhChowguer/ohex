package br.com.ohexpress.ohex.fragment;

import br.com.ohexpress.ohex.LojaActivity;
import br.com.ohexpress.ohex.R;
import br.com.ohexpress.ohex.adapters.LojasAdapter;
import br.com.ohexpress.ohex.LojasProximasActivity;
import br.com.ohexpress.ohex.interfaces.RecyclerViewOnClickListenerHack;
import br.com.ohexpress.ohex.model.Loja;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


public class LojaFragment extends Fragment implements RecyclerViewOnClickListenerHack {


    private RecyclerView mRecyclerView;
    private List<Loja> lista ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lojas, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_lista);
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
                LojasAdapter adapter = (LojasAdapter) mRecyclerView.getAdapter();

                if (lista.size() == llm.findFirstCompletelyVisibleItemPosition() + 1) {


                    //Toast.makeText(getActivity(), "teste", Toast.LENGTH_SHORT);

                    //Carrega mais itens 26min video de recyclerView
                }

            }
        });


        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);



        lista = ((LojasProximasActivity) getActivity()).getSetLojaList();
        //carregaLojas();
        LojasAdapter adapter = new LojasAdapter(getActivity(), lista);
        adapter.setRecyclerViewOnClickListenerHack(this);
        mRecyclerView.setAdapter(adapter);


        return view;


    }


    @Override
    public void onClickListener(View view, int position) {
        //Toast.makeText(getActivity(), "Position: "+position, Toast.LENGTH_SHORT).show();

        Intent itLProx = new Intent(getActivity(), LojaActivity.class);
        itLProx.putExtra("loja", lista.get(position));
        startActivity(itLProx);
    }




}
