package br.com.ohexpress.ohex;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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


public class FavoritosActivity extends ActionBarActivity {


    private RequestQueue requestQueue;
    private Map<String, String> params;
    private String url;
    private TextView nome;
    private TextView login;
    private TextView email;
    private TextView senha;
    private Toolbar ohTopBar;
    private Toolbar ohBaixoBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ohTopBar = (Toolbar) findViewById(R.id.oh_top_toolbar);
        setSupportActionBar(ohTopBar);

        ohBaixoBar = (Toolbar) findViewById(R.id.inc_toolbar_baixo);
        ohBaixoBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            Intent it = null;

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.item_tb_pedido:

                        Toast.makeText(FavoritosActivity.this,"Pedido",Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.item_tb_favoritos:

                        Toast.makeText(FavoritosActivity.this,"Favoritos",Toast.LENGTH_SHORT).show();

                        break;

                    case R.id.item_tb_qrcode:
                        Toast.makeText(FavoritosActivity.this,"Qr Code",Toast.LENGTH_SHORT).show();


                        break;
                    case R.id.item_tb_config:

                        Toast.makeText(FavoritosActivity.this,"Configuracoes",Toast.LENGTH_SHORT).show();

                        break;

                }

                return true;
            }
        });

        ohBaixoBar.inflateMenu(R.menu.menu_baixo);

        url = "http://10.0.2.2:8080/ohexpress/registroApp";

        nome = (TextView) findViewById(R.id.et_nome);
        login = (TextView) findViewById(R.id.et_login);
        email = (TextView) findViewById(R.id.et_email);
        senha = (TextView) findViewById(R.id.et_senha);


        requestQueue = Volley.newRequestQueue(FavoritosActivity.this);


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

    public void add(View view) {

        params = new HashMap<String, String>();
        params.put("email", email.getText().toString());
        params.put("nome", nome.getText().toString());
        params.put("sNome", "Rafael");
        params.put("senha", senha.getText().toString());
        params.put("login", login.getText().toString());


        StringRequest request = new StringRequest(Method.POST, url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

                Toast.makeText(FavoritosActivity.this, "deu certo" + response, Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(FavoritosActivity.this, "deu erro" + error, Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {

                params = new HashMap<String, String>();
                params.put("email", email.getText().toString());
                params.put("nome", nome.getText().toString());
                params.put("sNome", "Rafael");
                params.put("senha", senha.getText().toString());
                params.put("login", login.getText().toString());

                return (params);
            }

        };

        request.setTag("tag");
        requestQueue.add(request);

        Toast.makeText(FavoritosActivity.this, "Usuario nao pode ser cadastrado", Toast.LENGTH_LONG).show();
        return;
    }
}
