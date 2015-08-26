package br.com.ohexpress.ohex.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.ohexpress.ohex.R;
import br.com.ohexpress.ohex.model.CategoriaProduto;


/**
 * Created by viniciusthiengo on 7/19/15.
 */
public class CestaMenuAdapter extends BaseAdapter {

    private Context mContext;
    private List<CategoriaProduto> mList;
    private LayoutInflater mLayoutInflater;

    public  CestaMenuAdapter(Context c, List<CategoriaProduto> l){

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
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if( convertView == null ){
            convertView = mLayoutInflater.inflate(R.layout.cesta_menu, parent, false);
            holder = new ViewHolder();
            convertView.setTag( holder );

            holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.tvLabel = (TextView) convertView.findViewById(R.id.tv_label);
            holder.vwDivider = convertView.findViewById(R.id.vw_divider2);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.ivIcon.setImageResource(R.drawable.ic_circle);
        holder.tvLabel.setText( mList.get(position).getNome());

        return convertView;
    }

    public static class ViewHolder{
        ImageView ivIcon;
        TextView tvLabel;
        View vwDivider;
    }
}