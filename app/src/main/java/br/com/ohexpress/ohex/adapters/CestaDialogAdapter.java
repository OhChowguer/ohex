package br.com.ohexpress.ohex.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.ohexpress.ohex.R;
import br.com.ohexpress.ohex.model.CategoriaProduto;
import br.com.ohexpress.ohex.model.CreditCard;


/**
 * Created by viniciusthiengo on 7/19/15.
 */
public class CestaDialogAdapter extends BaseAdapter {

    private Context mContext;
    private List<CreditCard> mList;
    private LayoutInflater mLayoutInflater;

    public CestaDialogAdapter(Context c, List<CreditCard> l){

        mContext = c;
        mList = l;
        mLayoutInflater = LayoutInflater.from(c);


    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if( convertView == null ){
            convertView = mLayoutInflater.inflate(R.layout.item_dialog_pedido, parent, false);
            holder = new ViewHolder();
            convertView.setTag( holder );

            holder.myRadio = (RadioButton) convertView.findViewById(R.id.rb_dialog_pedido);


            holder.myRadio.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {


                    holder.myRadio.setChecked(false);



                }
            });
 ;
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

       holder.myRadio.setText(mList.get(position).getNomeTitular());


        return convertView;
    }

    public static class ViewHolder{
        RadioButton myRadio;
    }
}