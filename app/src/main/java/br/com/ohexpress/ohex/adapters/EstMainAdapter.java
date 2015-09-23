package br.com.ohexpress.ohex.adapters;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import br.com.ohexpress.ohex.ConfiguracaoActivity;
import br.com.ohexpress.ohex.MyApplication;
import br.com.ohexpress.ohex.R;
import br.com.ohexpress.ohex.interfaces.RecyclerViewOnClickListenerHack;
import br.com.ohexpress.ohex.model.Usuario;
import br.com.ohexpress.ohex.util.Constant;


public class EstMainAdapter extends RecyclerView.Adapter<EstMainAdapter.MyViewHolder> {

    private List<String> lista;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private Context context;
    private Usuario user;



    public EstMainAdapter(Context c, List<String> l){

        context =c;
        lista = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        user = ((MyApplication)context.getApplicationContext()).getUser();

        if (viewType==0) {
            View v = mLayoutInflater.inflate(R.layout.card_item_pedidos_est, viewGroup, false);
            MyViewHolder mvh = new MyViewHolder(v,viewType);
            return mvh;
        } else if (viewType==1) {
            View v = mLayoutInflater.inflate(R.layout.card_item_pedidos_est, viewGroup, false);
            MyViewHolder mvh = new MyViewHolder(v,viewType);
            return mvh;
        } else if (viewType==2) {
            View v = mLayoutInflater.inflate(R.layout.card_item_pedidos_est, viewGroup, false);
            MyViewHolder mvh = new MyViewHolder(v,viewType);
            return mvh;
        } else if (viewType==3) {
            View v = mLayoutInflater.inflate(R.layout.card_item_pedidos_est, viewGroup, false);
            MyViewHolder mvh = new MyViewHolder(v,viewType);
            return mvh;
        } else{

            View v = mLayoutInflater.inflate(R.layout.card_item_pedidos_est, viewGroup, false);
            MyViewHolder mvh = new MyViewHolder(v,viewType);
            return mvh;
        }

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {



        if (position == 0){
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.fab_material_green_500));
            holder.nome.setText("Aceito");
            holder.numero.setText("12");
        }else if (position == 1){
            holder.nome.setText("Espera");
            holder.numero.setText("02");
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.fab_material_blue_500));
        }else if (position == 2){
            holder.nome.setText("Andamento");
            holder.numero.setText("42");
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.fab_material_red_500));
        }else if (position == 5){
            holder.nome.setText("");
            holder.numero.setText("Incluir Pedido");
            holder.numero.setTextSize(25);
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.fab_material_indigo_500));
        }else{
            holder.nome.setText("");
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.md_white_1000));
        }
        /*if (position == 0){
            holder.nome.setText(user.getNome()+"");
        }else if (position == 1){
            holder.email.setText(user.getEmail()+"");
            holder.login.setText(user.getLogin()+"");
            holder.nomeComp.setText(user.getNome()+" "+user.getSnome()+"");
        }else if (position == 3){
            holder.beLog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AccountManager mAccountManager= AccountManager.get(context);
                    Account[] accs = mAccountManager.getAccountsByType(Constant.ACCOUNT_TYPE);
                    Account account = accs[0];
                    mAccountManager.removeAccount(account, new AccountManagerCallback<Boolean>() {
                        @Override
                        public void run(AccountManagerFuture<Boolean> future) {
                            ((MyApplication) context.getApplicationContext()).setUser(new Usuario());
                            ((ConfiguracaoActivity) context).fechar();

                        }
                    }, null);
                }
            });
        }*/


    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    public void removeItem(int position){

        //listaLojas.remove(position);
        notifyItemRemoved(position);


    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        mRecyclerViewOnClickListenerHack = r;
    }

    public class  MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public  TextView nome;
        public  TextView numero;
        public  TextView nomeComp;
        public  TextView login;
        public  Button   beLog;
        public RelativeLayout layout;

        public MyViewHolder(View itemView, int viewType ) {
            super(itemView);

            layout = (RelativeLayout) itemView.findViewById(R.id.rl_item_pedidos_est);
            nome = (TextView) itemView.findViewById(R.id.tv_name_card);
            numero = (TextView) itemView.findViewById(R.id.tv_number_pedido_aceito);

            /*if (viewType == 0){
                nome = (TextView) itemView.findViewById(R.id.tv_nome_user);
            }else if (viewType ==1){
                email = (TextView) itemView.findViewById(R.id.tv_email_cfg);
                nomeComp = (TextView) itemView.findViewById(R.id.tv_nome_cfg);
                login = (TextView) itemView.findViewById(R.id.tv_user_cfg);
            }else if (viewType == 3){
                beLog = (Button) itemView.findViewById(R.id.bt_logout);
            }*/
        }


        @Override
        public void onClick(View v) {

            if(mRecyclerViewOnClickListenerHack != null){
                mRecyclerViewOnClickListenerHack.onClickListener(v,getPosition());


            }

        }
    }



}
