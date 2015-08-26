package br.com.ohexpress.ohex;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import br.com.ohexpress.ohex.fragment.ProdutoFragment;
import br.com.ohexpress.ohex.model.Produto;


public class ListaProdutosActivity extends ActionBarActivity {


    private Toolbar ohTopBar;
    private List<Produto> listProd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_prod);

        listProd = getIntent().getParcelableArrayListExtra("produtos");

        ohTopBar = (Toolbar) findViewById(R.id.oh_top_toolbar);
        setSupportActionBar(ohTopBar);






        // FRAGMENT
        ProdutoFragment frag = (ProdutoFragment) getSupportFragmentManager().findFragmentByTag("mainFragLProd");
        if(frag == null) {
            frag = new ProdutoFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rl_fragment_container_prod, frag, "mainFragLProd");
            ft.commit();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_loja, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {

            finish();
            return true;
        }

        if (id == R.id.action_cesta_loja) {

            Intent it = new Intent(ListaProdutosActivity.this, CestaActivity.class);
            startActivity(it);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public List<Produto> getSetLojaList(){





        return(listProd);
    }


}
