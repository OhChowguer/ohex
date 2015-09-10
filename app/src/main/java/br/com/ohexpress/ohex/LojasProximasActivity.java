package br.com.ohexpress.ohex;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import br.com.ohexpress.ohex.fragment.LojaFragment;
import br.com.ohexpress.ohex.interfaces.LojaService;
import br.com.ohexpress.ohex.model.Loja;
import br.com.ohexpress.ohex.model.LojaPorDistancia;
import br.com.ohexpress.ohex.util.Constant;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LojasProximasActivity extends ActionBarActivity {


    private Toolbar ohTopBar;
    private LojaFragment fragment;
    private List<Loja> listLj = new ArrayList<Loja>(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_loja_pesq);

        //listLj = getIntent().getParcelableArrayListExtra("lojas");
        ohTopBar = (Toolbar) findViewById(R.id.oh_top_toolbar);
        setSupportActionBar(ohTopBar);

        // FRAGMENT
        fragment= (LojaFragment) getSupportFragmentManager().findFragmentByTag("mainFrag");
        if(fragment == null) {
            fragment = new LojaFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rl_fragment_container, fragment, "mainFrag");
            ft.commit();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getLojas();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_pesq_lojas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {

            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public List<Loja> getSetLojaList(){

        return(listLj);
    }

    public boolean getLojas () {


        RestAdapter restAdapterDist = new RestAdapter.Builder().setEndpoint(Constant.SERVER_URL).build();

        LojaService lojaServiceDist = restAdapterDist.create(LojaService.class);

        lojaServiceDist.listarLojas(
                new Callback<List<LojaPorDistancia>>() {


                    @Override
                    public void success(List<LojaPorDistancia> lojas, Response response) {

                        if (lojas != null){
                        List<Loja> lista = (List<Loja>) setDistancia(lojas);
                        //Toast.makeText(LojasProximasActivity.this,""+lojas.get(0).getLoja().getNome(),Toast.LENGTH_LONG).show();
                        fragment.refreshLista(lista);
                        }else{
                            Toast.makeText(LojasProximasActivity.this,"erro ao carregar lojas",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {

                        Toast.makeText(LojasProximasActivity.this, "Deu errado" + error, Toast.LENGTH_LONG).show();


                    }
                }

        );
        return true;
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
