package br.com.ohexpress.ohex.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import br.com.ohexpress.ohex.R;
import br.com.ohexpress.ohex.interfaces.RecyclerViewOnClickListenerHack;
import br.com.ohexpress.ohex.model.Produto;

public class PedidoEstProdAdapter extends RecyclerView.Adapter<PedidoEstProdAdapter.MyViewHolder> {

    private List<Produto> listaProduto = new ArrayList<Produto>(0);
    private LayoutInflater mLayoutInflater;
    private Context context;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;


    public PedidoEstProdAdapter(Context c, List<Produto> lista){
        listaProduto = lista;
        context = c;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v =  mLayoutInflater.inflate(R.layout.item_pedido_est_prod, viewGroup, false);
        MyViewHolder mvh = new MyViewHolder(v);



        return mvh;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder,  final int position) {

        holder.nomeProduto.setText(listaProduto.get(position).getNome());
        holder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.fab_material_blue_500));


        if (listaProduto.get(position).getItemProduto().size()>0){
            holder.imgCenter.setVisibility(View.VISIBLE);


        }

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


        public TextView nomeProduto;
        public RelativeLayout relativeLayout;
        public ImageView imgCenter;
        public MyViewHolder(View itemView) {
            super(itemView);

            nomeProduto = (TextView) itemView.findViewById(R.id.tv_nome_prod);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.ll_info_produto);
            imgCenter = (ImageView)  itemView.findViewById(R.id.iv_centro);
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
