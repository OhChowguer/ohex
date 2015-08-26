package br.com.ohexpress.ohex.adapters;

import android.content.Context;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import br.com.ohexpress.ohex.R;

import br.com.ohexpress.ohex.interfaces.RecyclerViewOnClickListenerHack;
import br.com.ohexpress.ohex.model.Loja;

public class LojasAdapter extends RecyclerView.Adapter<LojasAdapter.MyViewHolder> {

    private List<Loja> listaLojas = new ArrayList<Loja>(0);
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;


    public LojasAdapter(Context c, List<Loja> l){
        listaLojas = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = mLayoutInflater.inflate(R.layout.item_loja_cv, viewGroup, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Uri uri = Uri.parse(listaLojas.get(position).getImgLoja());
        holder.tvNomeLoja.setText(listaLojas.get(position).getNome());
        holder.tvCategoriaLoja.setText(listaLojas.get(position).getCategoria().get(0).getNome());
        holder.tvEnderecoLoja.setText(listaLojas.get(position).getEndereco().getBairro()+", "+
                        listaLojas.get(position).getEndereco().getCidade()+" - "+
                        listaLojas.get(position).getEndereco().getEstado());
        holder.tvDistanciaLoja.setText(listaLojas.get(position).getDistText());
        holder.imageLoja.setImageURI(uri);


    }

    public void removeItem(int position){

        listaLojas.remove(position);
        notifyItemRemoved(position);


    }

    @Override
    public int getItemCount() {
        return listaLojas.size();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        mRecyclerViewOnClickListenerHack = r;
    }

    public class  MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public SimpleDraweeView imageLoja;
        public TextView tvNomeLoja;
        public  TextView tvCategoriaLoja;
        public  TextView tvEnderecoLoja;
        public  TextView tvDistanciaLoja;

        public MyViewHolder(View itemView) {
            super(itemView);

            imageLoja = (SimpleDraweeView) itemView.findViewById(R.id.iv_img_listLoja);
            tvNomeLoja = (TextView) itemView.findViewById(R.id.tv_item_pedido);
            tvCategoriaLoja = (TextView) itemView.findViewById(R.id.tv_catLoja);
            tvEnderecoLoja = (TextView) itemView.findViewById(R.id.tv_endLoja);
            tvDistanciaLoja = (TextView) itemView.findViewById(R.id.tv_distancia_loja);
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
