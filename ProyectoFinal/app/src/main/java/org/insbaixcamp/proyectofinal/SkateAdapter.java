package org.insbaixcamp.proyectofinal;

/**
 * Created by Sergio on 11/05/2018.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class SkateAdapter
        extends RecyclerView.Adapter<SkateAdapter.ViewHolderSkates>
        implements View.OnClickListener{

    ArrayList<SkateArticles> listaSkates;
    private View.OnClickListener listener;

    public SkateAdapter(ArrayList<SkateArticles> listaSkates) {
        this.listaSkates = listaSkates;
    }

    @Override
    public ViewHolderSkates onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout=0;
        if (Utilities.visualizacion==Utilities.LIST){
            layout=R.layout.item_list_skate;
        }else {
            layout=R.layout.item_grid_skate;
        }

        View view= LayoutInflater.from(parent.getContext()).inflate(layout,null,false);

        view.setOnClickListener(this);

        return new ViewHolderSkates(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderSkates holder, int position) {
        holder.etName.setText(listaSkates.get(position).getNombre());

        if (Utilities.visualizacion==Utilities.LIST){
            holder.etInfo.setText(listaSkates.get(position).getInfo());
        }

        holder.foto.setImageResource(listaSkates.get(position).getFoto());
    }

    @Override
    public int getItemCount() {
        return listaSkates.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolderSkates extends RecyclerView.ViewHolder {

        TextView etName,etInfo;
        ImageView foto;

        public ViewHolderSkates(View itemView) {
            super(itemView);
            etName= (TextView) itemView.findViewById(R.id.idNombre);
            if (Utilities.visualizacion==Utilities.LIST){
                etInfo= (TextView) itemView.findViewById(R.id.idInfo);
            }
            foto= (ImageView) itemView.findViewById(R.id.idImagen);
        }
    }
}