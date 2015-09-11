package br.com.ohexpress.ohex.adapters;

import android.content.Context;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import br.com.ohexpress.ohex.MyApplication;
import br.com.ohexpress.ohex.R;

import br.com.ohexpress.ohex.interfaces.RecyclerViewOnClickListenerHack;
import br.com.ohexpress.ohex.interfaces.UserService;
import br.com.ohexpress.ohex.model.Loja;
import br.com.ohexpress.ohex.model.Usuario;
import br.com.ohexpress.ohex.util.Constant;
import br.com.ohexpress.ohex.util.ServerUtil;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LojasAdapter extends RecyclerView.Adapter<LojasAdapter.MyViewHolder> {


    private List<Loja> listaLojas = new ArrayList<Loja>(0);
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private Context context;


    public LojasAdapter(Context c, List<Loja> l){
        context =c;
        listaLojas = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = mLayoutInflater.inflate(R.layout.item_loja_cv2, viewGroup, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        Uri uri = Uri.parse(listaLojas.get(position).getImgLoja());
        holder.tvNomeLoja.setText(listaLojas.get(position).getNome());
        holder.tvCategoriaLoja.setText(listaLojas.get(position).getCategoria().get(0).getNome());
        holder.tvEnderecoLoja.setText(listaLojas.get(position).getEndereco().getBairro()+", "+
                        listaLojas.get(position).getEndereco().getCidade()+" - "+
                        listaLojas.get(position).getEndereco().getEstado());
        holder.tvDistanciaLoja.setText(listaLojas.get(position).getDistNumber() / 1000 + "");
        holder.imageLoja.setImageURI(uri);

        final Usuario user = ((MyApplication) context.getApplicationContext()).getUser();

        if (user.getId() == null){
            //fabFav.dismiss();
            holder.heartFav.setImageResource(R.drawable.ic_heart_24_white);
        }

        else if (user.findFav(listaLojas.get(position).getId())){
            //fabFav.setButtonColor(getResources().getColor(R.color.grey));
            holder.heartFav.setImageResource(R.drawable.ic_heart_24_wellow);
            holder.heartFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    addFav(1,holder,listaLojas.get(position),user);

                }
            });

        }else{
            //fabFav.setButtonColor(getResources().getColor(R.color.accent));
            holder.heartFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    addFav(2,holder,listaLojas.get(position),user);

                }
            });


        }


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
        public ImageView heartFav;

        public MyViewHolder(View itemView) {
            super(itemView);

            imageLoja = (SimpleDraweeView) itemView.findViewById(R.id.iv_img_listLoja);
            tvNomeLoja = (TextView) itemView.findViewById(R.id.tv_item_pedido);
            tvCategoriaLoja = (TextView) itemView.findViewById(R.id.tv_catLoja);
            tvEnderecoLoja = (TextView) itemView.findViewById(R.id.tv_endLoja);
            tvDistanciaLoja = (TextView) itemView.findViewById(R.id.tv_distancia_loja);
            heartFav = (ImageView) itemView.findViewById(R.id.iv_heart_loja);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            if(mRecyclerViewOnClickListenerHack != null){
                mRecyclerViewOnClickListenerHack.onClickListener(v,getPosition());


            }

        }
    }

    public void addFav(final int act, final MyViewHolder holder, final Loja loja, final Usuario usuario){

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Constant.SERVER_URL).build();

        UserService userService = restAdapter.create(UserService.class);

        userService.actionfavoritas(usuario.getToken(), loja.getId(),
                new Callback<String>() {


                    @Override
                    public void success(String callback, Response response) {


                        if (callback != null) {

                            Toast.makeText(context, callback, Toast.LENGTH_SHORT).show();
                            ServerUtil serverUtil = new ServerUtil();
                            serverUtil.getUser(usuario, context);
                            if (act == 1){

                                holder.heartFav.setImageResource(R.drawable.ic_heart_24_white);
                                holder.heartFav.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        addFav(2,holder,loja,usuario);

                                    }
                                });

                            }else{
                                Toast.makeText(context, callback, Toast.LENGTH_SHORT).show();
                                //fabFav.setButtonColor(getResources().getColor(R.color.grey));
                                holder.heartFav.setImageResource(R.drawable.ic_heart_24_wellow);
                                holder.heartFav.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {



                                        addFav(1,holder,loja,usuario);

                                    }
                                });
                            }

                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {

                        Toast.makeText(context, "Erro =S (" + error + ")", Toast.LENGTH_SHORT).show();


                    }
                }

        );


        return ;
    }

}
