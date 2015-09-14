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

import br.com.ohexpress.ohex.fragment.LojaFragment;
import br.com.ohexpress.ohex.interfaces.LojaService;
import br.com.ohexpress.ohex.interfaces.UserService;
import br.com.ohexpress.ohex.model.Loja;
import br.com.ohexpress.ohex.model.LojaPorDistancia;
import br.com.ohexpress.ohex.model.Usuario;
import br.com.ohexpress.ohex.util.Constant;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LojasFavoritasActivity extends ActionBarActivity {


    private Toolbar ohTopBar;
    private LojaFragment fragment;
    private List<Loja> listLj = new ArrayList<Loja>(0);
    private Usuario user;
    private AccountManager mAccountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_loja_pesq);
        user = ((MyApplication) getApplication()).getUser();
        mAccountManager = AccountManager.get(LojasFavoritasActivity.this);
        getAccounts(null);

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
        //getLojas();


    }

    @Override
    public void onResume(){
        super.onResume();
        if (user.getToken() != null){
        //getLojas ();
        }

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


        RestAdapter restAdapterPedido = new RestAdapter.Builder().setEndpoint(Constant.SERVER_URL).build();
        UserService userService = restAdapterPedido.create(UserService.class);

        userService.listarfavoritas(user.getToken(),
                new Callback<List<LojaPorDistancia>>() {


                    @Override
                    public void success(List<LojaPorDistancia> lojas, Response response) {

                        List<Loja> lista = (List<Loja>) setDistancia(lojas);
                        fragment.refreshLista(lista);


                    }

                    @Override
                    public void failure(RetrofitError error) {

                        Toast.makeText(LojasFavoritasActivity.this, "Deu errado" + error, Toast.LENGTH_LONG).show();


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

    public void getAccounts(View view) {

        mAccountManager.getAuthTokenByFeatures(Constant.ACCOUNT_TYPE,
                Constant.ACCOUNT_TOKEN_TYPE_COMPRADOR,
                null,
                LojasFavoritasActivity.this,
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

                            user.setAccountType(bundle.getString(AccountManager.KEY_ACCOUNT_TYPE));
                            user.setAccountName(bundle.getString(AccountManager.KEY_ACCOUNT_NAME));
                            user.setToken(bundle.getString(AccountManager.KEY_AUTHTOKEN));
                            getLojas();

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
