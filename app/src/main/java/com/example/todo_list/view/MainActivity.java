package com.example.todo_list.view;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo_list.database.PortalDB;
import com.example.todo_list.R;
import com.example.todo_list.model.Task;
import com.example.todo_list.controller.TodoAdapter;
import com.example.todo_list.model.TodoItem;

import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity implements TodoAdapter.OnTodoItemClickListener {
    private List<TodoItem> items;
    private TodoAdapter adapter;
    private RecyclerView recyclerView;
    ImageView addNote;
    EditText input;
    public List<TodoItem> items2;
    public TodoAdapter adapter2;
    public RecyclerView recyclerView2;
    private static final String CHANNEL_ID = "toto";
    public String username ;
    PortalDB db = new PortalDB(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items = new ArrayList<>();
        input = findViewById(R.id.inp);
        addNote = findViewById(R.id.add_item);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TodoAdapter(items, this, this);
        recyclerView.setAdapter(adapter);

        items2 = new ArrayList<>();
        recyclerView2 = findViewById(R.id.recycler_views);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        adapter2 = new TodoAdapter(items2, this, this);
        recyclerView2.setAdapter(adapter2);

        Intent intent = getIntent();
        if (intent.hasExtra("USERNAME")) {
            username = intent.getStringExtra("USERNAME");
        }
        else {
            username="m";
        }
        String type = "incompleted";
        for (int i = 0; i < db.RetrieveTask(username,type).size(); i++) {
            if (db.RetrieveTask(username,type).get(i).toString() != null) {
                addItem(db.RetrieveTask(username,type).get(i).toString());
            }
        }
        String type2 = "completed";
        for (int i = 0; i < db.RetrieveTask(username,type2).size(); i++) {
            if (db.RetrieveTask(username,type2).get(i).toString() != null) {
                addItem2(db.RetrieveTask(username,type2).get(i).toString());
            }
        }
        ImageView btn = findViewById(R.id.clearAllBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.DeleteAll(username);
                items.clear();
                adapter.notifyDataSetChanged();
            }
        });
        Button logout = findViewById(R.id.logoutbtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), sign_in.class);
                startActivity(intent);
            }
        });
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = input.getText().toString();
                if (txt == null || txt.length() == 0) {
                    String msg = "Please Enter An Item";
                    Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    addItem(txt);
                    Task task = new Task(txt, username, "incompleted");
                    boolean checker = db.addNewTask(task);
                    if (checker) {
                        String msg = txt + " is Added";
                        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
                        toast.show();
                        adapter.notifyDataSetChanged();
                        input.setText("");
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    } else {
                        String msg = txt + "Error";
                        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }
            }
        });
    }
    @Override
    public void onItemClick(int position) {
        TodoItem item = items.get(position);
        item.setChecked(!item.isChecked());
        adapter.notifyItemChanged(position);
    }
    @Override
    public void onItemLongClick(int position) {
        deleteItem(position);
    }
    public void addItem(String text) {
        TodoItem item = new TodoItem(text);
        items.add(item);
        adapter.notifyItemInserted(items.size() - 1);
    }
    public void addItem2(String text) {
        TodoItem newItem = new TodoItem(text);
        newItem.setChecked2(true); // set isChecked2 to true by default
        db.updateStat(username,text);
        items2.add(newItem);
        adapter2.notifyItemInserted(items2.size() - 1);
    }
    public void deleteItem(int position) {
        String taskTitle = items.get(position).getText();
        db.RemoveTask(username, taskTitle);
        items.remove(position);
        adapter.notifyDataSetChanged();
    }
    public void showNotification(String title, String task, String description) {
        // Check if device is running Android Oreo or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create a notification channel with the given ID and name
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Button Clicked Notification", NotificationManager.IMPORTANCE_DEFAULT);
            // Set the description for the notification channel
            channel.setDescription(description);
            // Enable the notification light and set its color
            channel.enableLights(true);
            channel.setLightColor(Color.BLUE);
            // Get the NotificationManager and create the notification channel
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        // Load the large icon for the notification
        Bitmap largeIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.koko);
        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setLargeIcon(largeIcon) // Set the large icon for the notification
                .setSmallIcon(R.drawable.koko) // Set the small icon for the notification
                .setContentTitle(title) // Set the title for the notification
                .setContentText(task) // Set the text for the notification
                .setPriority(NotificationCompat.PRIORITY_DEFAULT); // Set the notification priority

        // Get the NotificationManager and display the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
        }
        notificationManager.notify(0, builder.build());
}

}
