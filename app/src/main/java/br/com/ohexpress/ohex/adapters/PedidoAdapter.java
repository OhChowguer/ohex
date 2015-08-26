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
import br.com.ohexpress.ohex.model.Pedido;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.MyViewHolder> {

    private List<Pedido> listaPedido = new ArrayList<Pedido>(0);
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;


    public PedidoAdapter(Context c, List<Pedido> l){
        listaPedido = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = mLayoutInflater.inflate(R.layout.item_pedido_cv, viewGroup, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        //Uri uri = Uri.parse(listaPedido.get(position).getId()+"");
        holder.tvNomeLojaPedido.setText(listaPedido.get(position).getLoja().getNome());
        //holder.tvStatusPedido.setText(listaPedido.get(position).getItem().size()+"");
        //holder.imagePedido.setImageURI(uri);


    }

    public void removeItem(int position){

        listaPedido.remove(position);
        notifyItemRemoved(position);


    }

    @Override
    public int getItemCount() {
        return listaPedido.size();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        mRecyclerViewOnClickListenerHack = r;
    }

    public class  MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public SimpleDraweeView imagePedido;
        public TextView tvNomeLojaPedido;
        public  TextView tvStatusPedido;

        public MyViewHolder(View itemView) {
            super(itemView);

           // imagePedido = (SimpleDraweeView) itemView.findViewById(R.id.iv_img_lista_pedido);
            tvNomeLojaPedido = (TextView) itemView.findViewById(R.id.tv_nome_loja_lista_pedido);
            tvStatusPedido = (TextView) itemView.findViewById(R.id.tv_status_lista_pedido);
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
