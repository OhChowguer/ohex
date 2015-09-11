package br.com.ohexpress.ohex.adapters;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.ohexpress.ohex.R;
import br.com.ohexpress.ohex.interfaces.RecyclerViewOnClickListenerHack;
import br.com.ohexpress.ohex.model.Loja;
import br.com.ohexpress.ohex.model.Pedido;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.MyViewHolder> {

    private List<Pedido> listaPedido = new ArrayList<Pedido>(0);
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private DecimalFormat precision;
    private DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.GERMAN);
    private Context context;


    public PedidoAdapter(Context c, List<Pedido> l){
        context = c;
        listaPedido = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');
        precision = new DecimalFormat("###0.00",otherSymbols);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = mLayoutInflater.inflate(R.layout.item_pedido_cv, viewGroup, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        int status=listaPedido.get(position).getStatus();

        //Uri uri = Uri.parse(listaPedido.get(position).getId()+"");
        holder.tvNomeLojaPedido.setText(listaPedido.get(position).getLoja().getNome());
        //holder.tvStatusPedido.setText(listaPedido.get(position).getItem().size()+"");
        //holder.imagePedido.setImageURI(uri);
        if(status==2){

            holder.icoItemPedido.setImageResource(R.drawable.ic_checkbox_marked_circle);
            holder.tvStatusPedido.setText("Aceito");
            holder.corPedido.setBackgroundColor(context.getResources().getColor(R.color.fab_material_green_500));

        }
        else if(status==3){
            holder.icoItemPedido.setImageResource(R.drawable.ic_alert_circle);
            holder.tvStatusPedido.setText("Finalizado");
            holder.corPedido.setBackgroundColor(context.getResources().getColor(R.color.fab_material_blue_500));

        }else if(status==4){
            holder.icoItemPedido.setImageResource(R.drawable.ic_close_circle);
            holder.tvStatusPedido.setText("Fechado");
            holder.corPedido.setBackgroundColor(context.getResources().getColor(R.color.fab_material_red_900));

        }
        holder.totalPedido.setText(precision.format(listaPedido.get(position).somaVlrItens()));


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
        public  TextView totalPedido;
        public FrameLayout corPedido;
        public TextView tvStatusPedido;
        public ImageView icoItemPedido;

        public MyViewHolder(View itemView) {
            super(itemView);

            icoItemPedido = (ImageView) itemView.findViewById(R.id.iv_ico_item_pedido);
            tvNomeLojaPedido = (TextView) itemView.findViewById(R.id.tv_nome_loja_lista_pedido);
            totalPedido = (TextView) itemView.findViewById(R.id.tv_total_pedido);
            tvStatusPedido = (TextView) itemView.findViewById(R.id.tv_status_item_pedido);
            corPedido = (FrameLayout) itemView.findViewById(R.id.fl_item_pedido_status);
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
