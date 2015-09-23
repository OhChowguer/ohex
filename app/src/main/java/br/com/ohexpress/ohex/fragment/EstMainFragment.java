package br.com.ohexpress.ohex.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import br.com.ohexpress.ohex.R;
import br.com.ohexpress.ohex.adapters.EstMainAdapter;


public class EstMainFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private List<String> lista = new ArrayList<String>(0);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_est, container, false);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                if (position == 0){
                    return 1;

                }else if(position ==1){

                    return 1;

                }else if(position==2){

                    return 1;

                }
                else if(position==3){

                    return 3;

                }
                else if(position==4){

                    return 1;

                }
                else if(position==5){

                    return 2;

                }else
                {
                    return 3;
                }
            }
        });

        lista.add("teste1");
        lista.add("teste1");
        lista.add("teste1");
        lista.add("teste1");
        lista.add("teste1");
        lista.add("teste1");
        lista.add("teste1");
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_lista);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);

        EstMainAdapter adapter = new EstMainAdapter(getActivity(), lista);
        mRecyclerView.setAdapter(adapter);


        return view;


    }







}
