package br.com.ohexpress.ohex.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.software.shell.fab.ActionButton;
import com.software.shell.fab.ActionButton.Animations;

import java.util.ArrayList;
import java.util.List;

import br.com.ohexpress.ohex.R;
import br.com.ohexpress.ohex.interfaces.RecyclerViewOnClickListenerHack;
import br.com.ohexpress.ohex.model.Pedido;
import br.com.ohexpress.ohex.util.PedidosUtil;

public class PedidoEstAdapter extends RecyclerView.Adapter<PedidoEstAdapter.MyViewHolder> {

    private List<Pedido> listaPedido = new ArrayList<Pedido>(0);
    private LayoutInflater mLayoutInflater;
    private Context ct;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;


    public PedidoEstAdapter(Context c, List<Pedido> l){
        listaPedido = l;
        ct = c;
        mLayoutInflater = (LayoutInflater) ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v =  mLayoutInflater.inflate(R.layout.item_pedido_est, viewGroup, false);
        MyViewHolder mvh = new MyViewHolder(v);



        return mvh;
    }

    @Override
    public int getItemViewType(int position) {


            switch (listaPedido.get(position).getStatus()) {
                case 1: return 1;
                case 2: return 2;
                case 3: return 3;
                case 4: return 4;
                case 5: return 5;

            }

        return 1;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,  final int position) {
        //final  int pos = position;

                if (listaPedido.get(position).getStatus() == 1){
                    holder.tvIdTetPedido.setText(listaPedido.get(position).getId()+"1");
                    holder.fabL.setImageResource(R.drawable.ic_busca);
                    holder.fabL.setButtonColor(ct.getResources().getColor(R.color.fab_material_orange_500));
                    holder.fabR.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PedidosUtil pUtil = new PedidosUtil();

                           pUtil.setStatusPedidos(listaPedido.get(position).getId(), 2, ct);


                        }
                    });
                    holder.fabL.setShowAnimation(Animations.JUMP_FROM_DOWN);
                    holder.fabR.setButtonColor(ct.getResources().getColor(R.color.fab_material_blue_500));

                }else if (listaPedido.get(position).getStatus() == 2){
                    holder.tvIdTetPedido.setText(listaPedido.get(position).getId()+"2");

                    holder.fabL.setImageResource(R.drawable.ic_qrcode);
                    holder.fabL.setButtonColor(ct.getResources().getColor(R.color.fab_material_blue_500));
                    holder.fabL.setShowAnimation(Animations.SCALE_UP);
                    holder.fabL.setHideAnimation(Animations.SCALE_DOWN);
                    holder.fabR.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PedidosUtil pUtil = new PedidosUtil();

                             pUtil.setStatusPedidos(listaPedido.get(position).getId(), 3, ct);


                        }
                    });
                    holder.fabR.setImageResource(R.drawable.ic_busca);
                    holder.fabR.setButtonColor(ct.getResources().getColor(R.color.fab_material_yellow_500));

                }else if (listaPedido.get(position).getStatus() == 3){
                    holder.tvIdTetPedido.setText(listaPedido.get(position).getId()+"3");

                    holder.fabL.setImageResource(R.drawable.ic_cart);
                    holder.fabL.setButtonColor(ct.getResources().getColor(R.color.fab_material_green_500));
                    holder.fabL.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PedidosUtil pUtil = new PedidosUtil();

                             pUtil.setStatusPedidos(listaPedido.get(position).getId(),4, ct);


                        }
                    });
                    holder.fabR.setImageResource(R.drawable.ic_busca);
                    holder.fabR.setButtonColor(ct.getResources().getColor(R.color.fab_material_red_500));

                }else if (listaPedido.get(position).getStatus() == 4){
                    holder.tvIdTetPedido.setText(listaPedido.get(position).getId()+"4");

                    holder.fabL.setImageResource(R.drawable.ic_circle);
                    holder.fabL.setButtonColor(ct.getResources().getColor(R.color.fab_material_red_500));
                    holder.fabL.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PedidosUtil pUtil = new PedidosUtil();

                             pUtil.setStatusPedidos(listaPedido.get(position).getId(), 5, ct);


                        }
                    });
                    holder.fabR.setImageResource(R.drawable.ic_busca);
                    holder.fabR.setButtonColor(ct.getResources().getColor(R.color.fab_material_green_500));

                }else if (listaPedido.get(position).getStatus() == 5){

                }

        //Uri uri = Uri.parse(listaPedido.get(position).getId()+"");
        holder.tvItemPedido.setText(listaPedido.get(position).getItem().get(0).getQuantidade() +"x "+listaPedido.get(position).getItem().get(0).getProduto().getNome() );

        holder.tvIdPedido.setText(listaPedido.get(position).getId()+"");
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
        //public SimpleDraweeView imagePedido;
        public ActionButton fabL;
        public ActionButton fabR;

        public TextView tvItemPedido;
        public  TextView tvIdPedido;
        public  TextView tvIdTetPedido;

        public MyViewHolder(View itemView) {
            super(itemView);

            fabL = (ActionButton) itemView.findViewById(R.id.fab_left_est);
            fabR = (ActionButton) itemView.findViewById(R.id.fab_right_est);
            //imagePedido = (SimpleDraweeView) itemView.findViewById(R.id.iv_img_lista_pedido);
            tvItemPedido = (TextView) itemView.findViewById(R.id.tv_item1_est_ped);
            tvIdPedido = (TextView) itemView.findViewById(R.id.tv_item3_ped);
            tvIdTetPedido = (TextView) itemView.findViewById(R.id.tv_item4_ped);
            //tvStatusPedido = (TextView) itemView.findViewById(R.id.tv_status_lista_pedido);
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
