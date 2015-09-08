package br.com.ohexpress.ohex;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import br.com.ohexpress.ohex.adapters.LojasAdapter;
import br.com.ohexpress.ohex.interfaces.LojaService;
import br.com.ohexpress.ohex.interfaces.RecyclerViewOnClickListenerHack;
import br.com.ohexpress.ohex.model.Loja;
import br.com.ohexpress.ohex.model.LojaPorDistancia;
import br.com.ohexpress.ohex.util.Constant;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class SearchableActivity extends AppCompatActivity implements RecyclerViewOnClickListenerHack {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private List<Loja> lista = new ArrayList<Loja>(0);
    private List<Loja> listaAux = new ArrayList<Loja>(0);
    private LojasAdapter adapter;
    private CoordinatorLayout clContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        mToolbar = (Toolbar) findViewById(R.id.tb_main);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        //clContainer = (CoordinatorLayout) findViewById(R.id.cl_container);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
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

        LinearLayoutManager llm = new LinearLayoutManager( SearchableActivity.this );
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);


        adapter = new LojasAdapter(SearchableActivity.this,listaAux);
        adapter.setRecyclerViewOnClickListenerHack(SearchableActivity.this);
        mRecyclerView.setAdapter(adapter);



        hendleSearch( getIntent() );
    }
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent( intent );
        hendleSearch( intent );
    }

    public void hendleSearch( Intent intent ){
        if( Intent.ACTION_SEARCH.equalsIgnoreCase( intent.getAction() ) ){
            String q = intent.getStringExtra( SearchManager.QUERY );

            mToolbar.setTitle(q);
            filterLojas(q);
/*
            SearchRecentSuggestions searchRecentSuggestions = new SearchRecentSuggestions(this,
                    SearchableProvider.AUTHORITY,
                    SearchableProvider.MODE);
            searchRecentSuggestions.saveRecentQuery(q, null);*/
        }
    }


    public void filterLojas( String q ){
        //listaAux.clear();

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Constant.SERVER_URL).build();

        LojaService lojaService = restAdapter.create(LojaService.class);

        lojaService.listarLojas(
                new Callback<List<LojaPorDistancia>>() {


                    @Override
                    public void success(List<LojaPorDistancia> lojas, Response response) {

                        setList(setDistancia(lojas));

                        //Toast.makeText(SearchableActivity.this, "Position:okay", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {

                        Toast.makeText(SearchableActivity.this, "Deu errado" + error, Toast.LENGTH_LONG).show();


                    }
                }

        );

    /*
        for( int i = 0, tamI = mList.size(); i < tamI; i++ ){
            if( mList.get(i).getModel().toLowerCase().startsWith( q.toLowerCase() ) ){
                mListAux.add( mList.get(i) );
            }
        }
        for( int i = 0, tamI = mList.size(); i < tamI; i++ ){
            if( !mListAux.contains( mList.get(i) )
                    && mList.get(i).getBrand().toLowerCase().startsWith( q.toLowerCase() ) ){
                mListAux.add( mList.get(i) );
            }
        }

        mRecyclerView.setVisibility( mListAux.isEmpty() ? View.GONE : View.VISIBLE);
        if( mListAux.isEmpty() ){
            TextView tv = new TextView( this );
            tv.setText( "Nenhum carro encontrado." );
            tv.setTextColor( getResources().getColor( R.color.colorPrimarytext ) );
            tv.setId( 1 );
            tv.setLayoutParams( new FrameLayout.LayoutParams( FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT )  );
            tv.setGravity(Gravity.CENTER);

            clContainer.addView( tv );
        }
        else if( clContainer.findViewById(1) != null ) {
            clContainer.removeView( clContainer.findViewById(1) );
        }
*/
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        /*SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView;
        MenuItem item = menu.findItem(R.id.action_searchable_activity);

        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ){
            searchView = (SearchView) item.getActionView();
        }
        else{
            searchView = (SearchView) MenuItemCompat.getActionView(item);
        }

        searchView.setSearchableInfo( searchManager.getSearchableInfo( getComponentName() ) );
        searchView.setQueryHint(getResources().getString(R.string.search_hint));*/

        return true;
    }


    @Override
    public void onClickListener(View view, int position) {
        //Toast.makeText(SearchableActivity.this, "Position: "+position, Toast.LENGTH_SHORT).show();

        Intent itLProx = new Intent(SearchableActivity.this, LojaActivity.class);
        itLProx.putExtra("loja", listaAux.get(position));
        startActivity(itLProx);
    }

    public void setList(List<Loja> lojas) {

        this.listaAux = lojas;
        adapter = new LojasAdapter(SearchableActivity.this,listaAux);
        adapter.notifyDataSetChanged();
        adapter.setRecyclerViewOnClickListenerHack(SearchableActivity.this);
        mRecyclerView.setAdapter(adapter);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        int id = item.getItemId();



        if (id == android.R.id.home) {

            finish();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    public List<Loja> setDistancia(List<LojaPorDistancia> lpd) {

        List<Loja> lojas =  new ArrayList<Loja>();

        for (LojaPorDistancia lj: lpd)
        {
            Loja loja = lj.getLoja();
            loja.setDistNumber(lj.getDistNumber());
            loja.setDistText(lj.getDistText());
            lojas.add(loja);


        }
        return lojas;
    }



}
