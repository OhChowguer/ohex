package br.com.ohexpress.ohex;


import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import java.util.Map;
import br.com.ohexpress.ohex.interfaces.UserService;
import br.com.ohexpress.ohex.model.Usuario;
import br.com.ohexpress.ohex.util.Constant;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LoginActivity extends  AccountAuthenticatorActivity {


    private RequestQueue requestQueue;
    private Map<String, String> params;
    private String url;
    private TextView nome;
    private EditText login;
    private TextView email;
    private EditText senha;
    private Toolbar ohTopBar;
    private Toolbar ohBaixoBar;
    private AccountManager mAccountManager;
    private Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //ohTopBar = (Toolbar) findViewById(R.id.oh_top_toolbar);
        //setSupportActionBar(ohTopBar);



        senha = (EditText) findViewById(R.id.tvDataExpCard);
        login = (EditText) findViewById(R.id.et_login_act);


        user = ((MyApplication) getApplication()).getUser();
        user.setAccountType(getIntent().getStringExtra(Constant.ARG_ACCOUNT_TYPE));
        user.setAccountName(getIntent().getStringExtra(Constant.ARG_ACCOUNT_NAME));
        user.setAuthTokenType(getIntent().getStringExtra(Constant.ARG_AUTH_TYPE));

        mAccountManager = AccountManager.get(LoginActivity.this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Toast.makeText(this, "Deu certo SILAAAAA", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.action_settings2) {

            Toast.makeText(this, "Eh Nois SILAAA", Toast.LENGTH_SHORT).show();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }



    public void signIn(View view) {

        final Intent it = new Intent();
        //Log.i("Script", "AuthenticatorActivity.signIn()");

        findViewById(R.id.bt_login).setEnabled(false);
        user.setLogin(((EditText) findViewById(R.id.et_login_act)).getText().toString());
        user.setSenha(((EditText) findViewById(R.id.tvDataExpCard)).getText().toString());

        RestAdapter restAdapterUser = new RestAdapter.Builder().setEndpoint(Constant.SERVER_URL).build();

        UserService userService = restAdapterUser.create(UserService.class);

        userService.gettoken(user.getLogin(),user.getSenha(),
                new Callback<String>() {


                    @Override
                    public void success(String token, Response response) {

                        //Toast.makeText(LoginActivity.this, "Deu erro na conexao!"+token, Toast.LENGTH_SHORT).show();

                        it.putExtra(AccountManager.KEY_ACCOUNT_TYPE, Constant.ACCOUNT_TYPE);
                        it.putExtra(AccountManager.KEY_ACCOUNT_NAME, user.getLogin());
                        it.putExtra(AccountManager.KEY_AUTHTOKEN, token);
                        finish(it);

                        //listaPedido = (ArrayList<Pedido>) pedidos;
                        //Intent itLProx = new Intent(MainActivity.this, EstabelecimentoMainActivity.class);
                        //itLProx.putParcelableArrayListExtra("pedidos", listaPedido);
                        //startActivity(itLProx);

                        //findViewById(R.id.bt_login).setEnabled(true);


                    }

                    @Override
                    public void failure(RetrofitError error) {
                        findViewById(R.id.bt_login).setEnabled(true);

                        Toast.makeText(LoginActivity.this, "Deu erro na conexao!"+error, Toast.LENGTH_SHORT).show();


                    }
                }

        );


    }

    public void finish(Intent it){
        //Log.i("Script", "AuthenticatorActivity.finish()");

        String accountType = it.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE);
        String accountName = it.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String token = it.getStringExtra(AccountManager.KEY_AUTHTOKEN);
        Account account = new Account(accountName, accountType);
        int countAccounts = mAccountManager.getAccountsByType(accountType).length;

        if(token == null){
            //Log.i("Script", "AuthenticatorActivity.finish() : if(token.equalsIgnoreCase(\"null\"))");
            Toast.makeText(LoginActivity.this, "Dados de acesso incorretos!", Toast.LENGTH_SHORT).show();
            findViewById(R.id.bt_login).setEnabled(true);
            return;
        }

        mAccountManager.addAccountExplicitly(account, null, null);
        mAccountManager.setAuthToken(account, user.getAuthTokenType(), token);

        setAccountAuthenticatorResult(it.getExtras());
        finish();

        if(countAccounts == 0){
            //startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }



}
