package br.com.ohexpress.ohex;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;


public class CadFormaDePagamentoActivity extends ActionBarActivity {



   private Spinner mSpinner;
    private Toolbar ohTopBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_pag);


        ohTopBar = (Toolbar) findViewById(R.id.oh_top_toolbar_form_pag);
        setSupportActionBar(ohTopBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSpinner = (Spinner) findViewById(R.id.sp_cad_form_pag);
        List<String> list = new ArrayList<String>();
        list.add("VISA CREDITO");
        list.add("VISA DEBITO");
        list.add("MASTERCARD MAESTRO");
        list.add("MASTERCARD CREDITO");
        list.add("ELLO CREDITO");
        list.add("ELLO DEBITO");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,list);
        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(dataAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_cad_forma_de_pagamento, menu);
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
}
