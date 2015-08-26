package br.com.ohexpress.ohex;



import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.ohexpress.ohex.fragment.LojaFragment;
import br.com.ohexpress.ohex.interfaces.LojaService;
import br.com.ohexpress.ohex.model.Loja;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LojasProximasActivity extends ActionBarActivity {


    private Toolbar ohTopBar;
    private Toolbar ohBaixoBar;
    private List<Loja> listLj = new ArrayList<Loja>(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_loja_pesq);

        listLj = getIntent().getParcelableArrayListExtra("lojas");

        ohTopBar = (Toolbar) findViewById(R.id.oh_top_toolbar);
        setSupportActionBar(ohTopBar);




        // FRAGMENT
        LojaFragment frag = (LojaFragment) getSupportFragmentManager().findFragmentByTag("mainFrag");
        if(frag == null) {
            frag = new LojaFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rl_fragment_container, frag, "mainFrag");
            ft.commit();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





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

        if (id == R.id.action_settings) {

            RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://10.0.2.2:8080/ohexpress/lojas/test").build();

            LojaService lojaService = restAdapter.create(LojaService.class);

            lojaService.teste(
                    new Callback<List<String>>() {


                        @Override
                        public void success(List<String> strings, Response response) {




                        }

                        @Override
                        public void failure(RetrofitError error) {

                            Toast.makeText(LojasProximasActivity.this, "Deu errado"+error, Toast.LENGTH_LONG).show();


                        }
                    }

            );

            return true;
        }

        if (id == R.id.action_settings2) {

            RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://10.0.2.2:8080/ohexpress/lojas").build();

            LojaService lojaService = restAdapter.create(LojaService.class);

            lojaService.listarLojas(
                    new Callback<List<Loja>>() {


                        @Override
                        public void success(List<Loja> lojas, Response response) {


                            Toast.makeText(LojasProximasActivity.this, "Deu certo, Nome da loja -> " +lojas.get(0).getNome(), Toast.LENGTH_LONG).show();


                        }

                        @Override
                        public void failure(RetrofitError error) {

                            Toast.makeText(LojasProximasActivity.this, "Deu errado" + error, Toast.LENGTH_LONG).show();


                        }
                    }

            );
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public List<Loja> getSetLojaList(){





        return(listLj);
    }


}
