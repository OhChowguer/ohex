package br.com.ohexpress.ohex;


import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.ohexpress.ohex.fragment.EstMainFragment;
import br.com.ohexpress.ohex.fragment.PedidoEstFragment;
import br.com.ohexpress.ohex.interfaces.PedidoService;
import br.com.ohexpress.ohex.model.Loja;
import br.com.ohexpress.ohex.model.Pedido;
import br.com.ohexpress.ohex.util.Constant;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class EstabelecimentoPedidosActivity extends ActionBarActivity {

    private Context mContext;
    private Toolbar ohTopBar;
    private List<Pedido> pedidos = new ArrayList<Pedido>(0);
    private float scale;
    private PedidoEstFragment frag;
    private MyApplication mApp;
    private Boolean filtro;
    private AccountManager mAccountManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estabelecimento_pedido);
        mContext = this;
        pedidos = getIntent().getParcelableArrayListExtra("pedidos");
        mAccountManager = AccountManager.get(this);
        scale = mContext.getResources().getDisplayMetrics().density;
        filtro = getIntent().getBooleanExtra("filtro", false);
        mApp  = (MyApplication) getApplication();
        ohTopBar = (Toolbar) findViewById(R.id.oh_top_toolbar);
        setSupportActionBar(ohTopBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        frag = (PedidoEstFragment) getSupportFragmentManager().findFragmentByTag("EstPedidoFrag");
        if (frag == null) {
            frag = new PedidoEstFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rl_fragment_container_pedido_est, frag, "EstPedidoFrag");
            ft.commit();
        }

        getAccounts(null);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_est_pedidos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {


            finish();
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_f1) {

            frag.listarProdutosConfec();
            return true;
        }

        if (id == R.id.action_f2) {

            frag.trocarAdapter(1);
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

    public void SetPedidosList(List<Pedido> peds){

        frag.setNewListaPedidos(peds);
    }



    public void getPedidoPorLoja() {

        RestAdapter restAdapterPedido = new RestAdapter.Builder().setEndpoint(Constant.SERVER_URL).build();
        PedidoService pedidoService = restAdapterPedido.create(PedidoService.class);

        pedidoService.listarPedidoPorComprador(mApp.getUser().getToken(),
                new Callback<List<Pedido>>() {


                    @Override
                    public void success(List<Pedido> pedidos, Response response) {


                        if (filtro) {

                            pedidos = (ArrayList<Pedido>) pedidos;
                            Loja loja = ((MyApplication) getApplication()).getMyLoja();

                            List<Pedido> lista = new ArrayList<Pedido>(0);

                            for (Pedido ped : pedidos) {

                                if (ped.getLoja().getId() == loja.getId()) {
                                    lista.add(ped);
                                }
                            }

                            frag.setNewListaPedidos(lista);


                        } else {

                            frag.setNewListaPedidos(pedidos);
                        }


                    }

                    @Override
                    public void failure(RetrofitError error) {

                        Toast.makeText(mContext, "Deu errado" + error, Toast.LENGTH_LONG).show();
                        //frag.removeProgress();


                    }
                }

        );


    }

    public void getAccounts(View view) {

        mAccountManager.getAuthTokenByFeatures(Constant.ACCOUNT_TYPE,
                Constant.ACCOUNT_TOKEN_TYPE_COMPRADOR,
                null,
                EstabelecimentoPedidosActivity.this,
                null,
                null,
                new AccountManagerCallback<Bundle>() {
                    @Override
                    public void run(AccountManagerFuture<Bundle> future) {
                        try {
                            Bundle bundle = future.getResult();
                            //Log.i("Script", "MainActivity.getAccounts()");
                            //Log.i("Script", "MainActivity.getAccounts() : AccountType = "+bundle.getString(AccountManager.KEY_ACCOUNT_TYPE));
                            //Log.i("Script", "MainActivity.getAccounts() : AccountName = "+bundle.getString(AccountManager.KEY_ACCOUNT_NAME));
                            //Log.i("Script", "MainActivity.getAccounts() : Token = "+bundle.getString(AccountManager.KEY_AUTHTOKEN));

                            mApp.getUser().setAccountType(bundle.getString(AccountManager.KEY_ACCOUNT_TYPE));
                            mApp.getUser().setAccountName(bundle.getString(AccountManager.KEY_ACCOUNT_NAME));
                            mApp.getUser().setToken(bundle.getString(AccountManager.KEY_AUTHTOKEN));
                            getPedidoPorLoja();

                            //setUserDataFromServer();
                        } catch (OperationCanceledException e) {
                            e.printStackTrace();
                        } catch (AuthenticatorException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                null);
    }


}