package br.com.ohexpress.ohex;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import br.com.ohexpress.ohex.adapters.EstMainAdapter;
import br.com.ohexpress.ohex.fragment.EstMainFragment;
import br.com.ohexpress.ohex.fragment.PedidoEstFragment;
import br.com.ohexpress.ohex.model.Pedido;


public class EstabelecimentoMainActivity extends ActionBarActivity {

    private Context mContext;
    private Toolbar ohTopBar;
    private List<Pedido> pedidos = new ArrayList<Pedido>(0);
    private float scale;
    private EstMainFragment frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estabelecimento_main);
        mContext = this;
        pedidos = getIntent().getParcelableArrayListExtra("pedidos");

        scale = mContext.getResources().getDisplayMetrics().density;


        ohTopBar = (Toolbar) findViewById(R.id.oh_top_toolbar);
        setSupportActionBar(ohTopBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        frag = (EstMainFragment) getSupportFragmentManager().findFragmentByTag("EstMainFrag");
        if (frag == null) {
            frag = new EstMainFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rl_fragment_container_main_est, frag, "EstMainFrag");
            ft.commit();
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_est_pedidos, menu);
        return true;
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_f1) {


            return true;
        }

        if (id == R.id.action_f2) {


            return true;
        }

        if (id == R.id.action_f3) {


            return true;
        }


        return super.onOptionsItemSelected(item);
    }



    public List<Pedido> getSetPedidosList(){

        return(pedidos);
    }


}