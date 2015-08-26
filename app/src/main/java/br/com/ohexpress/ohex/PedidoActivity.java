package br.com.ohexpress.ohex;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import br.com.ohexpress.ohex.dao.DatabaseHelper;
import br.com.ohexpress.ohex.dao.ItemPedidoDao;
import br.com.ohexpress.ohex.dao.ProdutoDao;
import br.com.ohexpress.ohex.fragment.BodyPedidoFragment;
import br.com.ohexpress.ohex.interfaces.PedidoService;
import br.com.ohexpress.ohex.model.ItemPedido;
import br.com.ohexpress.ohex.model.Pedido;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class PedidoActivity extends ActionBarActivity {

    private Toolbar ohTopBar;
    private Pedido pedido;
    private TextView tvNomeProduto;
    public SimpleDraweeView imageProduto;
    private ItemPedidoDao ipDao;
    private ProdutoDao prodDao;
    private DatabaseHelper dh;
    private List<ItemPedido> itens;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        dh = new DatabaseHelper(PedidoActivity.this);

        pedido =  getIntent().getExtras().getParcelable("pedido");
        itens = pedido.getItem();
        ohTopBar = (Toolbar) findViewById(R.id.oh_top_toolbar);
        setSupportActionBar(ohTopBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // FRAGMENT
        BodyPedidoFragment frag = (BodyPedidoFragment) getSupportFragmentManager().findFragmentByTag("mainFragCestaPed");
        if(frag == null) {
            frag = new BodyPedidoFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rl_fragment_container_pedido, frag, "mainFragCestaPed");
            ft.commit();
        }


    }

   @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
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

            //Toast.makeText(this, loja.getImgLoja(), Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.action_settings2) {

            Toast.makeText(this, itens.size()+"", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.action_menu_registrar) {

            Intent it = new Intent(PedidoActivity.this, RegistrarActivity.class);
            startActivity(it);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void AddPedido(View view){



        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://10.0.3.2:8080/ohexpress/phone").build();

        PedidoService pedidoService = restAdapter.create(PedidoService.class);

        /*pedidoService.addPedido(itens,
                new Callback<Pedido>(){


                    @Override
                    public void success(Pedido pedido, Response response) {


                        if (pedido.getId()!= null){

                            Toast.makeText(PedidoActivity.this, "Pedido realizado com sucesso!", Toast.LENGTH_LONG).show();
                            itens.clear();
                            finish();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {

                        Toast.makeText(PedidoActivity.this, "errado" + error, Toast.LENGTH_LONG).show();


                    }
                }

        );*/


        return;
    }

    public List<ItemPedido> getItemPedido(){


        return itens;
    }



}
