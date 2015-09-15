package br.com.ohexpress.ohex;


import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import br.com.ohexpress.ohex.interfaces.UserService;
import br.com.ohexpress.ohex.model.Usuario;
import br.com.ohexpress.ohex.util.Constant;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;


public class RegistrarActivity extends ActionBarActivity {


    private Map<String, String> params;
    private TextView nome;
    private TextView rAqui;
    private TextView login;
    private TextView email;
    private TextView senha;
    private Toolbar ohTopBar;
    private AccountManager mAccountManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        ohTopBar = (Toolbar) findViewById(R.id.oh_top_toolbar);
        setSupportActionBar(ohTopBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAccountManager = AccountManager.get(RegistrarActivity.this);

        nome = (TextView) findViewById(R.id.tvNumeroCard);
        login = (TextView) findViewById(R.id.tvNomeTitular);
        email = (TextView) findViewById(R.id.tvEndereco);
        senha = (TextView) findViewById(R.id.tvDataExpCard);
        rAqui = (TextView) findViewById(R.id.tv_reg_aqui);
        rAqui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrarActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == android.R.id.home) {

            finish();
            return true;
        }
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

    public void add(View view) {


        Usuario usuario = new Usuario(login.getText().toString(),nome.getText().toString(),email.getText().toString());

        RestAdapter restAdapterPedido = new RestAdapter.Builder().setEndpoint(Constant.SERVER_URL).build();

        UserService userService = restAdapterPedido.create(UserService.class);

        userService.registrar(senha.getText().toString(),usuario,
                new Callback<Usuario>() {


                    @Override
                    public void success(Usuario usuario, retrofit.client.Response response) {

                        if (usuario.getId() != null){

                            ((MyApplication) getApplication()).setUser(usuario);
                            //mAccountManager

                            finish();

                        }else {


                            Toast.makeText(RegistrarActivity.this, "Erro no registro", Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {

                        Toast.makeText(RegistrarActivity.this, "Deu errado =(  " + error, Toast.LENGTH_LONG).show();


                    }
                }

        );

    }
}
