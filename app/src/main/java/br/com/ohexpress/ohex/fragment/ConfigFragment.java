package br.com.ohexpress.ohex.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.ohexpress.ohex.R;
import br.com.ohexpress.ohex.adapters.CestaAdapter;
import br.com.ohexpress.ohex.adapters.ConfigAdapter;
import br.com.ohexpress.ohex.model.ItemPedido;


public class ConfigFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private List<String> lista;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_config, container, false);


        lista = new ArrayList<String>(0);
        lista.add("teste1");
        lista.add("teste2");
        lista.add("teste3");
        lista.add("teste4");

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_itens_config_act);
        mRecyclerView.setHasFixedSize(true);



        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);


        ConfigAdapter adapter = new ConfigAdapter(getActivity(), lista);
        //adapter.setRecyclerViewOnClickListenerHack(this);
        mRecyclerView.setAdapter(adapter);

        return  view;


    }


    public void reloadDados(){

        ConfigAdapter adapter = new ConfigAdapter(getActivity(), lista);
        mRecyclerView.setAdapter(adapter);
    }







}
