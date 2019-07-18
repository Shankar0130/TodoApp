package com.shankaryadav.www.todoapp;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    private Context context;
    List<Nodes> list;


    public TodoAdapter(Context context, List<Nodes> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from (viewGroup.getContext ());

        View view = inflater.inflate (R.layout.item,viewGroup,false);


        return new TodoViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder todoViewHolder, int i) {

        todoViewHolder.title.setText (list.get (i).getTitle ());
        todoViewHolder.descrip.setText (list.get (i).getDescription ());

    }

    @Override
    public int getItemCount() {
        return list.size ();
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder{

        TextView title,descrip;
        public TodoViewHolder(@NonNull View itemView) {
            super (itemView);
            title = itemView.findViewById (R.id.titlename);
            descrip = itemView.findViewById (R.id.descriptionname);

            itemView.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    Nodes nodes = list.get (getAdapterPosition ());

                    Intent intent = new Intent (context,CheckTodoActivity.class);

                    intent.putExtra ("id",nodes.id);
                    intent.putExtra ("title",nodes.title);
                    intent.putExtra ("desc",nodes.description);

                    context.startActivity (intent);
                }
            });
        }
    }
}
