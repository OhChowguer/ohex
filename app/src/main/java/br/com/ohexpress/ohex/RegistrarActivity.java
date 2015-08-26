package br.com.ohexpress.ohex;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
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


public class RegistrarActivity extends ActionBarActivity {


    private RequestQueue requestQueue;
    private Map<String, String> params;
    private String url;
    private TextView nome;
    private TextView login;
    private TextView email;
    private TextView senha;
    private Toolbar ohTopBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        ohTopBar = (Toolbar) findViewById(R.id.oh_top_toolbar);
        setSupportActionBar(ohTopBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


         url = "http://10.0.2.2:8080/ohexpress/registroApp";

        nome = (TextView) findViewById(R.id.tvNumeroCard);
        login = (TextView) findViewById(R.id.tvNomeTitular);
        email = (TextView) findViewById(R.id.tvEndereco);
        senha = (TextView) findViewById(R.id.tvDataExpCard);


        requestQueue = Volley.newRequestQueue(RegistrarActivity.this);


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

        params = new HashMap<String, String>();
        params.put("email", email.getText().toString());
        params.put("nome", nome.getText().toString());
        params.put("sNome", "Rafael");
        params.put("senha", senha.getText().toString());
        params.put("login", login.getText().toString());


        StringRequest request = new StringRequest(Method.POST, url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

                Toast.makeText(RegistrarActivity.this, "deu certo" + response, Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(RegistrarActivity.this, "deu erro" + error, Toast.LENGTH_LONG).show();

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


        return;
    }
}
