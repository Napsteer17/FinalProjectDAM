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
        extends RecyclerView.Adapter<SkateAdapter.ViewHolderPersonajes>
        implements View.OnClickListener{

    ArrayList<SkateArticles> listaPersonajes;
    private View.OnClickListener listener;

    public SkateAdapter(ArrayList<SkateArticles> listaPersonajes) {
        this.listaPersonajes = listaPersonajes;
    }

    @Override
    public ViewHolderPersonajes onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout=0;
        if (Utilities.visualizacion==Utilities.LIST){
            layout=R.layout.item_list_skate;
        }else {
            layout=R.layout.item_grid_skate;
        }

        View view= LayoutInflater.from(parent.getContext()).inflate(layout,null,false);

        view.setOnClickListener(this);

        return new ViewHolderPersonajes(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderPersonajes holder, int position) {
        holder.etiNombre.setText(listaPersonajes.get(position).getNombre());

        if (Utilities.visualizacion==Utilities.LIST){
            holder.etiInformacion.setText(listaPersonajes.get(position).getInfo());
        }

        holder.foto.setImageResource(listaPersonajes.get(position).getFoto());
    }

    @Override
    public int getItemCount() {
        return listaPersonajes.size();
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

    public class ViewHolderPersonajes extends RecyclerView.ViewHolder {

        TextView etiNombre,etiInformacion;
        ImageView foto;

        public ViewHolderPersonajes(View itemView) {
            super(itemView);
            etiNombre= (TextView) itemView.findViewById(R.id.idNombre);
            if (Utilities.visualizacion==Utilities.LIST){
                etiInformacion= (TextView) itemView.findViewById(R.id.idInfo);
            }
            foto= (ImageView) itemView.findViewById(R.id.idImagen);
        }
    }
}