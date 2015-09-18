package br.com.ohexpress.ohex;


import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import br.com.ohexpress.ohex.interfaces.UserService;
import br.com.ohexpress.ohex.model.Usuario;
import br.com.ohexpress.ohex.util.Constant;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LoginActivity extends AccountAuthenticatorActivity  {


    private EditText login;
    private TextView email;
    private TextView cliqueAqui;
    private EditText senha;
    private Toolbar ohTopBar;
    private AccountManager mAccountManager;
   // private Usuario user;
    private MyApplication mApp;
    private ImageView voltar;
    private Button btLogin;
    private Drawable warning;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mApp = (MyApplication) getApplication();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        warning = (Drawable)getResources().getDrawable(R.drawable.ic_alert_circle);
        senha = (EditText) findViewById(R.id.et_senha);
        login = (EditText) findViewById(R.id.et_login_act);
        //user = ((MyApplication) getApplication()).getUser();
        mApp.getUser().setAccountType(getIntent().getStringExtra(Constant.ARG_ACCOUNT_TYPE));
        mApp.getUser().setAccountName(getIntent().getStringExtra(Constant.ARG_ACCOUNT_NAME));
        mApp.getUser().setAuthTokenType(getIntent().getStringExtra(Constant.ARG_AUTH_TYPE));
        mAccountManager = AccountManager.get(LoginActivity.this);
        cliqueAqui = (TextView) findViewById(R.id.tv_clique_aqui);
        cliqueAqui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrarActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btLogin = (Button) findViewById(R.id.bt_login);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = senha.getText().toString().trim();
                String log = login.getText().toString().trim();


                if (!isEmptyFields(log,pass)) {
                    signIn(null);
                }

            }
        });
        voltar = (ImageView) findViewById(R.id.iv_voltar_login);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }


    private boolean isEmptyFields(String log,String pass) {

        if (TextUtils.isEmpty(log)) {
            login.requestFocus(); //seta o foco para o campo user
            login.setError("Campo login não pode ser vazio", warning);
            btLogin.setClickable(true);
            return true;
        }

        if (TextUtils.isEmpty(pass)) {
            senha.requestFocus(); //seta o foco para o campo user
            senha.setError("Campo senha não pode ser vazio", warning);
            btLogin.setClickable(true);
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }




    public void signIn(View view) {

        final Intent it = new Intent();

        btLogin.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        mApp.getUser().setLogin(((EditText) findViewById(R.id.et_login_act)).getText().toString());
        mApp.getUser().setSenha(((EditText) findViewById(R.id.et_senha)).getText().toString());

        RestAdapter restAdapterUser = new RestAdapter.Builder().setEndpoint(Constant.SERVER_URL).build();

        UserService userService = restAdapterUser.create(UserService.class);

        userService.gettoken(mApp.getUser().getLogin(),mApp.getUser().getSenha(),
                new Callback<String>() {


                    @Override
                    public void success(String token, Response response) {

                        //
                            if(token != null) {
                                it.putExtra(AccountManager.KEY_ACCOUNT_TYPE, Constant.ACCOUNT_TYPE);
                                it.putExtra(AccountManager.KEY_ACCOUNT_NAME, mApp.getUser().getLogin());
                                it.putExtra(AccountManager.KEY_AUTHTOKEN, token);
                                finish(it);
                            }else {
                                btLogin.setEnabled(true);
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "Usuario ou senha incorretos", Toast.LENGTH_SHORT).show();
                            }


                        //listaPedido = (ArrayList<Pedido>) pedidos;
                        //Intent itLProx = new Intent(MainActivity.this, EstabelecimentoMainActivity.class);
                        //itLProx.putParcelableArrayListExtra("pedidos", listaPedido);
                        //startActivity(itLProx);

                        //findViewById(R.id.bt_login).setEnabled(true);


                    }

                    @Override
                    public void failure(RetrofitError error) {
                        findViewById(R.id.bt_login).setEnabled(true);

                        Toast.makeText(LoginActivity.this, "Deu erro na conexao!" + "   " + error, Toast.LENGTH_SHORT).show();
                        btLogin.setEnabled(true);
                        progressBar.setVisibility(View.GONE);


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
            //findViewById(R.id.bt_login).setEnabled(true);
            btLogin.setEnabled(true);
            progressBar.setVisibility(View.GONE);
            return;
        }

        mAccountManager.addAccountExplicitly(account, null, null);
        mAccountManager.setAuthToken(account, mApp.getUser().getAuthTokenType(), token);

        setAccountAuthenticatorResult(it.getExtras());
        finish();

        if(countAccounts == 0){
            //startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }



}
