package org.insbaixcamp.proyectofinal;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> {

    public List<ToDo> toDos;

    public ToDoAdapter(List<ToDo> toDos){
        this.toDos = toDos;
    }

    @Override
    public ToDoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);
        return new ToDoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ToDoViewHolder holder, int position) {
        ToDo item = toDos.get(position);
        holder.textViewItem.setText(item.getMessage());
        holder.textViewUser.setText(item.getUser());
    }

    @Override
    public int getItemCount() {
        return toDos.size();
    }

    public class ToDoViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewItem;
        public TextView textViewUser;

        public ToDoViewHolder(View view) {
            super(view);
            textViewItem = (TextView) view.findViewById(R.id.txtItem);
            textViewUser = (TextView) view.findViewById(R.id.txtUser);
        }
    }


}
