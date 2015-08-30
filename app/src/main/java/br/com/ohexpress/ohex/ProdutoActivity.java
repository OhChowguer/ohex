package br.com.ohexpress.ohex;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
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
import br.com.ohexpress.ohex.model.ItemProduto;
import br.com.ohexpress.ohex.model.Loja;
import br.com.ohexpress.ohex.model.Pedido;
import br.com.ohexpress.ohex.model.Produto;


public class ProdutoActivity extends ActionBarActivity {

    private Toolbar ohTopBar;
    private ItemPedido itemPedido;
    private Produto produto;
    private TextView tvNomeProduto;
    private TextView tvQtdProduto;
    private TextView tvTotalProduto;
    private SimpleDraweeView imageProduto;
    private List<ItemProduto> adicionais = new ArrayList<ItemProduto>(0);
    private List<ItemProduto> opcinais = new ArrayList<ItemProduto>(0);
    private Pedido pedido;
    private DecimalFormat format;
    private float scale;
    private Context mContext;
    private ActionButton fabAdc;
    private ActionButton fabOpc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        mContext = this;
        scale = mContext.getResources().getDisplayMetrics().density;
        fabAdc = (ActionButton) findViewById(R.id.fab_menu_adicionais);
        fabOpc = (ActionButton) findViewById(R.id.fab_menu_opcionais);
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.GERMAN);
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');
        format = new DecimalFormat("###0.00", otherSymbols);
        pedido = ((MyApplication) getApplication()).getMyPedido();
        itemPedido = new ItemPedido();
        produto = getIntent().getExtras().getParcelable("produto");
        for (ItemProduto item: produto.getItemProduto()){
            opcinais.add(item);
        }
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
        getMenuInflater().inflate(R.menu.menu_loja, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

    public boolean AddCesta(View view) {


        Loja loja = new Loja();
        if (pedido.getLoja().getId() == ((MyApplication) getApplication()).getMyLoja().getId() || pedido.getLoja().getId() == null) {
            itemPedido = new ItemPedido();
            itemPedido.setProduto(produto);
            itemPedido.setAdicionais(adicionais);
            itemPedido.setOpcionais(opcinais);
            itemPedido.setQuantidade(Integer.parseInt(tvQtdProduto.getText().toString()));
            pedido.getItem().add(itemPedido);
            loja.setId(((MyApplication) getApplication()).getMyLoja().getId());
            pedido.setLoja(loja);
            Toast.makeText(ProdutoActivity.this, "Item adicionado", Toast.LENGTH_SHORT).show();
            finish();

            return true;
        }

        return true;
    }


    public void addQtd(View view) {
        int qtd = Integer.parseInt(tvQtdProduto.getText().toString());
        if (qtd < 9) {
            int qtd2 = qtd + 1;
            tvQtdProduto.setText(qtd2 + "");
            tvTotalProduto.setText(format.format(qtd2 * produto.getPreco()));
        }

        return;

    }


    public void delQtd(View view) {
        int qtd = Integer.parseInt(tvQtdProduto.getText().toString());
        if (qtd > 1) {
            int qtd2 = qtd - 1;
            tvQtdProduto.setText(qtd2 + "");
            tvTotalProduto.setText(format.format(qtd2 * produto.getPreco()));
        }

    }

    public void getAdicionais(View view) {
        final ListPopupWindow listPopupWindow = new ListPopupWindow(mContext);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, produto.getAdicionalString());

        listPopupWindow.setAdapter(adapter);
        listPopupWindow.setAnchorView(fabAdc);
        listPopupWindow.setWidth((int) (150 * scale + 0.5f));
        listPopupWindow.setDropDownGravity(ListPopupWindow.POSITION_PROMPT_ABOVE);
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (((android.support.v7.widget.AppCompatCheckedTextView) view).isChecked()) {

                    adicionais.add(produto.getItemProduto().get(position));

                } else {
                    adicionais.remove(produto.getItemProduto().get(position));
                }

            }
        });
        listPopupWindow.setModal(true);
        listPopupWindow.show();
        ListView listView = listPopupWindow.getListView();
        listView.setChoiceMode(listView.CHOICE_MODE_MULTIPLE);

        for (int i = 0; i < produto.getItemProduto().size(); i++) {

            for (ItemProduto adc: adicionais){

                if(adc.getId() == produto.getItemProduto().get(i).getId()){
                    listView.setItemChecked(i,true);
                }
            }
        }


    }

    public void getOpcionais(View view) {
        final ListPopupWindow listPopupWindow = new ListPopupWindow(mContext);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, produto.getAdicionalString());

        listPopupWindow.setAdapter(adapter);
        listPopupWindow.setAnchorView(fabOpc);
        listPopupWindow.setWidth((int) (150 * scale + 0.5f));
        listPopupWindow.setDropDownGravity(ListPopupWindow.POSITION_PROMPT_ABOVE);
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (((android.support.v7.widget.AppCompatCheckedTextView) view).isChecked()) {

                    opcinais.add(produto.getItemProduto().get(position));

                } else {
                    opcinais.remove(produto.getItemProduto().get(position));
                }

            }
        });
        listPopupWindow.setModal(true);
        listPopupWindow.show();
        ListView listView = listPopupWindow.getListView();
        listView.setChoiceMode(listView.CHOICE_MODE_MULTIPLE);

        for (int i = 0; i < produto.getItemProduto().size(); i++) {

            for (ItemProduto adc: opcinais){

                if(adc.getId() == produto.getItemProduto().get(i).getId()){
                    listView.setItemChecked(i,true);
                }
            }
        }


    }
}



