package br.com.ohexpress.ohex;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.software.shell.fab.ActionButton;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.ohexpress.ohex.model.ItemPedido;
import br.com.ohexpress.ohex.model.Loja;
import br.com.ohexpress.ohex.model.Pedido;
import br.com.ohexpress.ohex.model.Produto;


public class ProdutoActivity extends ActionBarActivity {

    private Toolbar ohTopBar;
    private Produto produto;
    private TextView tvNomeProduto;
    private TextView tvQtdProduto;
    private TextView tvTotalProduto;
    private SimpleDraweeView imageProduto;
    private Pedido pedido;
    private DecimalFormat format;
    private float scale;
    private Context mContext;
    private ActionButton fab;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        mContext=this;
        scale = mContext.getResources().getDisplayMetrics().density;
        fab = (ActionButton) findViewById(R.id.fab_menu_opcionais);
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.GERMAN);
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');
        format = new DecimalFormat("###0.00",otherSymbols);
        pedido = ((MyApplication) getApplication()).getMyPedido();
        produto = getIntent().getExtras().getParcelable("produto");
        imageProduto = (SimpleDraweeView) findViewById(R.id.fv_img_produto_unit);
        Uri uri = Uri.parse(produto.getImgProduto());
        imageProduto.setImageURI(uri);
        tvNomeProduto = (TextView) findViewById(R.id.tv_nome_produto_unit);
        tvNomeProduto.setText(produto.getNome());
        tvTotalProduto = (TextView) findViewById(R.id.tv_total_produto);
        tvTotalProduto.setText(format.format(produto.getPreco()));
        tvQtdProduto = (TextView) findViewById(R.id.tv_qtd_produto);
        ohTopBar = (Toolbar) findViewById(R.id.oh_top_toolbar);
        setSupportActionBar(ohTopBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





    }

   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_loja, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        int id = item.getItemId();

        if (id == android.R.id.home) {

            finish();
            return true;
        }

        if (id == R.id.action_cesta_loja) {

            Intent it = new Intent(ProdutoActivity.this, CestaActivity.class);
            startActivity(it);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public boolean AddCesta(View view){

        Loja loja= ((MyApplication) getApplication()).getMyLoja();
        //cesta = pedido.getItem();
    if(pedido.getLoja().getId() == loja.getId() || pedido.getLoja().getNome() == null){

        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setProduto(produto);
        itemPedido.setQuantidade(Integer.parseInt(tvQtdProduto.getText().toString()));
        //LojaActivity.addCesta(itemPedido);
        //cesta.add(itemPedido);
        //pedido.setItem(cesta);
        pedido.getItem().add(itemPedido);
        pedido.setLoja(((MyApplication) getApplication()).getMyLoja());
        Toast.makeText(ProdutoActivity.this,"Item adicionado",Toast.LENGTH_SHORT).show();
        finish();

        return true;}




        /*
        try {
            prodDao = new ProdutoDao(dh.getConnectionSource());
            ipDao = new ItemPedidoDao(dh.getConnectionSource());
            prodDao.create(produto);
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setProduto(produto);
            itemPedido.setQuantidade(1);
            ipDao.create(itemPedido);
            Toast.makeText(ProdutoActivity.this, "inseriu", Toast.LENGTH_SHORT).show();


        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(ProdutoActivity.this, "faiu", Toast.LENGTH_SHORT).show();
            return false;

        }*/

        return true;
    }


    public void addQtd(View view) {
        int qtd = Integer.parseInt(tvQtdProduto.getText().toString());
        if (qtd<99) {
            int qtd2 = qtd+1;
            tvQtdProduto.setText(qtd2+"");
            tvTotalProduto.setText(format.format(qtd2 * produto.getPreco() ));
        }

        return;

    }


    public void delQtd(View view) {
        int qtd = Integer.parseInt(tvQtdProduto.getText().toString());
        if (qtd>1) {
            int qtd2 = qtd-1;
            tvQtdProduto.setText(qtd2+"");
            tvTotalProduto.setText(format.format(qtd2 * produto.getPreco()));
        }

    }

    public void getAdicionais(View view) {

        final ListPopupWindow listPopupWindow = new ListPopupWindow(mContext);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, produto.getAdicionalString());
        //ListView listView = (ListView) dialog.findViewById(R.id.listview);
        //.setChoiceMode(listView.CHOICE_MODE_SINGLE);
       // listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

         //   @Override
        //    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ///

        //    }
       // });
       // listView.setAdapter(adapter);


        listPopupWindow.setAdapter(adapter);
        listPopupWindow.setAnchorView(fab);

        listPopupWindow.setWidth((int) (150 * scale + 0.5f));
        listPopupWindow.setDropDownGravity(ListPopupWindow.POSITION_PROMPT_ABOVE);
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                //Intent intent = new Intent(mContext, ListaProdutosActivity.class);
                //intent.putParcelableArrayListExtra("produtos", (ArrayList<Produto>) loja.getProduto());
                // listPopupWindow.dismiss();
                // mContext.startActivity(intent);

            }
        });
        listPopupWindow.setModal(true);


        //listPopupWindow.getBackground().setAlpha(0);
        listPopupWindow.show();
        ListView listView = listPopupWindow.getListView();
        listView.setChoiceMode(listView.CHOICE_MODE_MULTIPLE);

    }





}



