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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import br.com.ohexpress.ohex.model.ItemPedido;
import br.com.ohexpress.ohex.model.Pedido;


public class PedidosActivity extends ActionBarActivity {

    private TextView totalPedido;
    private Toolbar ohTopBar;
    private Pedido pedido;
    private DecimalFormat format;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ohTopBar = (Toolbar) findViewById(R.id.oh_top_toolbar);
        totalPedido = (TextView) findViewById(R.id.tv_total_pedido);

        setSupportActionBar(ohTopBar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }


    public double getTotal() {

        double total = 0;

        for (ItemPedido itens : pedido.getItem()) {

            total = total + itens.getQuantidade() * itens.getProduto().getPreco();


        }

        return total;
    }


}
