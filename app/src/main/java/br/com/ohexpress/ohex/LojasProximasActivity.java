package br.com.ohexpress.ohex;



import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.List;
import br.com.ohexpress.ohex.fragment.LojaFragment;
import br.com.ohexpress.ohex.model.Loja;


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

        return super.onOptionsItemSelected(item);
    }

    public List<Loja> getSetLojaList(){

        return(listLj);
    }


}
