package br.com.ohexpress.ohex.adapters;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import br.com.ohexpress.ohex.MyApplication;
import br.com.ohexpress.ohex.R;
import br.com.ohexpress.ohex.interfaces.RecyclerViewOnClickListenerHack;
import br.com.ohexpress.ohex.model.ItemPedido;
import br.com.ohexpress.ohex.model.Loja;
import br.com.ohexpress.ohex.model.Pedido;
import br.com.ohexpress.ohex.model.Produto;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.MyViewHolder> {

    private List<Produto> listaProduto ;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private Context context;


    public ProdutoAdapter(Context c, List<Produto> l){

        context=c;
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
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        Uri uri = Uri.parse(listaProduto.get(position).getImgProduto());
        holder.imageProduto.setImageURI(uri);
        holder.tvNomeProduto.setText(listaProduto.get(position).getNome());
        holder.tvDescProduto.setText(listaProduto.get(position).getNomeItemProdutro());
        //holder.tvDescProduto.setText(listaProduto.get(position).getDescricao());
        holder.tvPrecoProduto.setText(""+listaProduto.get(position).getPreco());
        holder.addCesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Pedido pedido = ((MyApplication) context.getApplicationContext()).getMyPedido();
                final Loja loja = new Loja();

                if (pedido.getLoja().getId() == ((MyApplication) context.getApplicationContext()).getMyLoja().getId() || pedido.getLoja().getId() == null) {

                    addProduto(loja, position);

                }else{

                    final Dialog dialog = new Dialog(context);

                    dialog.setContentView(R.layout.dialog_pedido_add_card);
                    dialog.setTitle("Adicionar produto");
                    TextView text = (TextView) dialog.findViewById(R.id.tv_nome_loga_dialog_pedido);
                    text.setText("Sua cesta possui itens de outra loja, para adicionar produtos dessa loja e necessario exclui-los," +
                            "deseja excluir?");
                    Button dialogButtonSim = (Button) dialog.findViewById(R.id.bt_emite_ped_dialog_sim);
                    Button dialogButtonNao = (Button) dialog.findViewById(R.id.bt_emite_ped_dialog_nao);
                    dialogButtonSim.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            ((MyApplication) context.getApplicationContext()).setMyPedido(new Pedido(true));
                            addProduto(loja, position);
                            dialog.dismiss();

                        }
                    });
                    dialogButtonNao.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }
        });



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
        public ImageView addCesta;

        public MyViewHolder(View itemView) {
            super(itemView);

            imageProduto = (SimpleDraweeView) itemView.findViewById(R.id.iv_img_listProduto);
            tvNomeProduto = (TextView) itemView.findViewById(R.id.tv_nomeProduto);
            tvDescProduto = (TextView) itemView.findViewById(R.id.tv_desc_produto);
            tvPrecoProduto = (TextView) itemView.findViewById(R.id.tv_preco_produto);
            addCesta = (ImageView) itemView.findViewById(R.id.iv_add_cesta);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            if(mRecyclerViewOnClickListenerHack != null){
                mRecyclerViewOnClickListenerHack.onClickListener(v,getPosition());


            }

        }
    }

    private void addProduto(Loja loja, int position){

        Pedido pedido = ((MyApplication) context.getApplicationContext()).getMyPedido();

        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setProduto(listaProduto.get(position));
        itemPedido.setQuantidade(1);
        loja.setId(((MyApplication) context.getApplicationContext()).getMyLoja().getId());
        pedido.setLoja(loja);
        pedido.getItem().add(itemPedido);

        Toast.makeText(context, "Item adicionado", Toast.LENGTH_SHORT).show();
    }
}
