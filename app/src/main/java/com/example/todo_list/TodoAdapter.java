package com.example.todo_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo_list.R;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    public List<TodoItem> items;
    public OnTodoItemClickListener listener;
    public MainActivity activity;

    public interface OnTodoItemClickListener {
        void onItemClick(int position);
        void onItemLongClick(int position);
    }

    public TodoAdapter(List<TodoItem> items, MainActivity activity, OnTodoItemClickListener listener) {
        this.items = items;
        this.listener = listener;
        this.activity = activity;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_item, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        TodoItem item = items.get(position);
        holder.checkBox.setChecked(item.isChecked());

        if (activity.items2.contains(item)) {
            holder.checkBox.setChecked(item.isChecked2()); //use isChecked2 for item2 list
        } else {
            holder.checkBox.setChecked(item.isChecked()); // use isChecked for items list
        }

        holder.textView.setText(item.getText());

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            item.setChecked(isChecked);
            if (isChecked) {
                Toast.makeText(buttonView.getContext(), item.getText()+" Done", Toast.LENGTH_SHORT).show();
                activity.addItem2(item.getText());
                activity.deleteItem(position);
                activity.showNotification("Task","Task "+ item.getText() +" Done","Notification about Task Done");
            }
        });


        holder.itemView.setOnLongClickListener(v -> {
            if (activity.items2.contains(item)) {


            } else {
                listener.onItemLongClick(position);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class TodoViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkBox;
        public TextView textView;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox);
            textView = itemView.findViewById(R.id.texts);
        }
    }
}