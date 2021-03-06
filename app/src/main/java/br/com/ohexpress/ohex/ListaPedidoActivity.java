package br.com.ohexpress.ohex;



import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.ohexpress.ohex.fragment.PedidoFragment;
import br.com.ohexpress.ohex.interfaces.PedidoService;
import br.com.ohexpress.ohex.model.Loja;
import br.com.ohexpress.ohex.model.Pedido;
import br.com.ohexpress.ohex.model.Usuario;
import br.com.ohexpress.ohex.util.Constant;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ListaPedidoActivity extends ActionBarActivity {

   // private Usuario user;
    private PedidoFragment frag;
    private AccountManager mAccountManager;
    private Toolbar ohTopBar;
    private ArrayList<Pedido> listPedido = new ArrayList<Pedido>(0);
    private MyApplication mApp;
    private Boolean filtro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedido);

        filtro = getIntent().getBooleanExtra("filtro", false);
        //user = ((MyApplication) getApplication()).getUser();
        mApp  = (MyApplication) getApplication();
        mAccountManager = AccountManager.get(ListaPedidoActivity.this);
        getAccounts(null);

        ohTopBar = (Toolbar) findViewById(R.id.oh_top_toolbar);
        setSupportActionBar(ohTopBar);

        // FRAGMENT
        frag = (PedidoFragment) getSupportFragmentManager().findFragmentByTag("mainFragLPed");
        if (frag == null) {
            frag = new PedidoFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rl_fragment_container_pedido, frag, "mainFragLPed");
            ft.commit();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




    }

    @Override
    public void onStart(){
        super.onStart();
        //if(mAccountManager.getAccountsByType(Constant.ACCOUNT_TYPE).length == 0){
        //    finish();
        //}
        Log.i("Script", "onStart()");
    }

    @Override
    public void onRestart(){
        super.onStart();
        if(mAccountManager.getAccountsByType(Constant.ACCOUNT_TYPE).length == 0){
            finish();
        }



        Log.i("Script", "onRestart()");
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.i("Script", "onResume()");
    }


    @Override
    public void onPause(){
        super.onPause();
        Log.i("Script", "onPause()");
    }


    @Override
    public void onStop(){
        super.onStop();
        Log.i("Script", "onStop()");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_lista_pedido, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {

            finish();
            return true;
        }

        if (id == R.id.action_lista_pedido) {

            mApp.getUser().setEmail("chowm");
            //getPedidos();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    public List<Pedido> getSetPedidosList() {


        return (listPedido);
    }

    public void getAccounts(View view) {

        mAccountManager.getAuthTokenByFeatures(Constant.ACCOUNT_TYPE,
                Constant.ACCOUNT_TOKEN_TYPE_COMPRADOR,
                null,
                ListaPedidoActivity.this,
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
                            getPedidos();

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


    public void getPedidos() {

        RestAdapter restAdapterPedido = new RestAdapter.Builder().setEndpoint(Constant.SERVER_URL).build();
        PedidoService pedidoService = restAdapterPedido.create(PedidoService.class);

        pedidoService.listarPedidoPorComprador(mApp.getUser().getToken(),
                new Callback<List<Pedido>>() {


                    @Override
                    public void success(List<Pedido> pedidos, Response response) {


                        if (filtro) {

                            listPedido = (ArrayList<Pedido>) pedidos;
                            Loja loja = ((MyApplication) getApplication()).getMyLoja();

                            List<Pedido> lista = new ArrayList<Pedido>(0);

                            for (Pedido ped:pedidos){

                                if(ped.getLoja().getId() == loja.getId()){
                                    lista.add(ped);
                                }
                            }

                            frag.loadPedidos(lista);


                        }else{

                            frag.loadPedidos(pedidos);
                        }




                    }

                    @Override
                    public void failure(RetrofitError error) {

                        Toast.makeText(ListaPedidoActivity.this, "Deu errado" + error, Toast.LENGTH_LONG).show();
                        frag.removeProgress();


                    }
                }

        );


    }
}