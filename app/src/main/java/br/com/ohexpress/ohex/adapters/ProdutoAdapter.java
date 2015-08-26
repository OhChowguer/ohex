package br.com.ohexpress.ohex.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import br.com.ohexpress.ohex.R;
import br.com.ohexpress.ohex.interfaces.RecyclerViewOnClickListenerHack;
import br.com.ohexpress.ohex.model.Loja;
import br.com.ohexpress.ohex.model.Produto;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.MyViewHolder> {

    private List<Produto> listaProduto ;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;


    public ProdutoAdapter(Context c, List<Produto> l){
        listaProduto = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = mLayoutInflater.inflate(R.layout.item_produto_cv, viewGroup, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        Uri uri = Uri.parse(listaProduto.get(position).getImgProduto());
        holder.imageProduto.setImageURI(uri);
        holder.tvNomeProduto.setText(listaProduto.get(position).getNome());
        holder.tvDescProduto.setText(listaProduto.get(position).getNomeItemProdutro());
        //holder.tvDescProduto.setText(listaProduto.get(position).getDescricao());
        holder.tvPrecoProduto.setText(""+listaProduto.get(position).getPreco());



    }

    public void removeItem(int position){

        listaProduto.remove(position);
        notifyItemRemoved(position);


    }

    @Override
    public int getItemCount() {
        return listaProduto.size();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        mRecyclerViewOnClickListenerHack = r;
    }

    public class  MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public SimpleDraweeView imageProduto;
        public TextView tvNomeProduto;
        public TextView tvDescProduto;
        public TextView tvPrecoProduto;

        public MyViewHolder(View itemView) {
            super(itemView);

            imageProduto = (SimpleDraweeView) itemView.findViewById(R.id.iv_img_listProduto);
            tvNomeProduto = (TextView) itemView.findViewById(R.id.tv_nomeProduto);
            tvDescProduto = (TextView) itemView.findViewById(R.id.tv_desc_produto);
            tvPrecoProduto = (TextView) itemView.findViewById(R.id.tv_preco_produto);
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
