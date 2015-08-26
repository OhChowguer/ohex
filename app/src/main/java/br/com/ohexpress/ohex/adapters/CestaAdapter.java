package br.com.ohexpress.ohex.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.ohexpress.ohex.R;
import br.com.ohexpress.ohex.interfaces.RecyclerViewOnClickListenerHack;
import br.com.ohexpress.ohex.model.ItemPedido;
import br.com.ohexpress.ohex.model.Loja;

public class CestaAdapter extends RecyclerView.Adapter<CestaAdapter.MyViewHolder> {

    private List<ItemPedido> listaItens = new ArrayList<ItemPedido>(0);
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private DecimalFormat precision;
    private DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.GERMAN);





    public CestaAdapter(Context c, List<ItemPedido> l){
        listaItens = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');
        precision = new DecimalFormat("###0.00",otherSymbols);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = mLayoutInflater.inflate(R.layout.item_cesta_act, viewGroup, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Uri uri = Uri.parse(listaItens.get(position).getProduto().getImgProduto());
        holder.tvNomeItensCesta.setText(listaItens.get(position).getProduto().getNome());
        holder.tvPrecoProdCesta.setText(precision.format(listaItens.get(position).getProduto().getPreco() * listaItens.get(position).getQuantidade()));
        holder.tvQtdProdCesta.setText(listaItens.get(position).getQuantidade()+"");
        holder.imageItemCesta.setImageURI(uri);


    }

    public void removeItem(int position){

        listaItens.remove(position);
        notifyItemRemoved(position);


    }

    @Override
    public int getItemCount() {
        return listaItens.size();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        mRecyclerViewOnClickListenerHack = r;
    }

    public class  MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public SimpleDraweeView imageItemCesta;
        public TextView tvNomeItensCesta;
        public TextView tvPrecoProdCesta;
        public TextView tvQtdProdCesta;

        public MyViewHolder(View itemView) {
            super(itemView);

            imageItemCesta = (SimpleDraweeView) itemView.findViewById(R.id.iv_img_itensCesta);
            tvNomeItensCesta = (TextView) itemView.findViewById(R.id.tv_nome_produto_itens_cesta);
            tvPrecoProdCesta = (TextView) itemView.findViewById(R.id.tv_preco_produto_cesta);
            tvQtdProdCesta = (TextView) itemView.findViewById(R.id.tv_qtd_item_cesta);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            if(mRecyclerViewOnClickListenerHack != null){
                mRecyclerViewOnClickListenerHack.onClickListener(v,getPosition());


            }

        }
    }
}
