package br.com.ohexpress.ohex;

import android.accounts.Account;
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

import com.mikepenz.materialdrawer.model.ProfileDrawerItem;

import java.io.IOException;

import br.com.ohexpress.ohex.fragment.ConfigFragment;
import br.com.ohexpress.ohex.interfaces.UserService;
import br.com.ohexpress.ohex.model.Usuario;
import br.com.ohexpress.ohex.util.Constant;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ConfiguracaoActivity extends ActionBarActivity {

    private ConfigFragment frag;
    private FragmentTransaction ft;
    private Toolbar ohTopBar;
    private AccountManager mAccountManager;
    private Account[] accs;
    private MyApplication mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);

        ohTopBar = (Toolbar) findViewById(R.id.oh_top_toolbar);
        setSupportActionBar(ohTopBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mApp = (MyApplication) getApplication();
        mAccountManager = AccountManager.get(this);
        accs = mAccountManager.getAccountsByType(Constant.ACCOUNT_TYPE);


            getAccounts(null);


        // FRAGMENT
        frag = (ConfigFragment) getSupportFragmentManager().findFragmentByTag("confFrag");
        if (frag == null) {
            frag = new ConfigFragment();
            ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rl_fragment_container_config, frag, "confFrag");
            ft.commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mapa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void getAccounts(View view) {
        mAccountManager.getAuthTokenByFeatures(Constant.ACCOUNT_TYPE,
                Constant.ACCOUNT_TOKEN_TYPE_COMPRADOR,
                null,
                ConfiguracaoActivity.this,
                null,
                null,
                new AccountManagerCallback<Bundle>() {
                    @Override
                    public void run(AccountManagerFuture<Bundle> future) {
                        try {
                            Bundle bundle = future.getResult();
                            //Log.i("Script", "MainActivity.getAccounts()");
                            frag.reloadDados();
                            //getUser(bundle.getString(AccountManager.KEY_AUTHTOKEN));
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

    public void fechar(){
        finish();
    }

    @Override
    public void onRestart(){
        super.onStart();
        if(mAccountManager.getAccountsByType(Constant.ACCOUNT_TYPE).length == 0){
            finish();
        }



        Log.i("Script", "onRestart()");
    }
    public void getUser(String token){


        RestAdapter restAdapterPedido = new RestAdapter.Builder().setEndpoint(Constant.SERVER_URL).build();

        UserService userService = restAdapterPedido.create(UserService.class);

        userService.getuser(token,
                new Callback<Usuario>() {


                    @Override
                    public void success(Usuario usuario, Response response) {

                        //((MyApplication) getApplication()).setUser(usuario);
                        mApp.setUser(usuario);
                        frag.reloadDados();



                        //Toast.makeText(context, user.getToken(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void failure(RetrofitError error) {

                        //Toast.makeText(context, "Deu errado" + error, Toast.LENGTH_LONG).show();


                    }
                }

        );
    }
}
