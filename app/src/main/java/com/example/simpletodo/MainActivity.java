package com.example.simpletodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

//import org.apache.commons.io.FileUtils;

//import java.io.File;

//import org.apache.commons.io.FileUtils;

public class MainActivity extends AppCompatActivity {

    ArrayList items;
    ArrayAdapter<String> itemsAdapter;
    ListView LVItems;
    // private Object FileUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // obtain a reference to the ListView created with the layout
        LVItems = (ListView) findViewById(R.id.LVItems);
        // initialize the items list


        //items = new ArrayList<>();
        try {
            readItems();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // initialize the adapter using the items list
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        // wire the adapter to the view
        LVItems.setAdapter(itemsAdapter);

        // add some mock items to the list
     //   items.add("First todo item");
     //   items.add("Second todo item");


        setupListViewListener();
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItems);
        String ItemText = (String) etNewItem.getText().toString();
        itemsAdapter.add(ItemText);
        etNewItem.setText("");

        Toast.makeText(getApplicationContext(), "Item added to list", Toast.LENGTH_SHORT).show();

    }


    private void setupListViewListener() {
        Log.i("MainActivity", "Setting up listener on list view");
        LVItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("MainActivity", "Item removed from list: " + position);
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;

            }
        });
    }

    private File getDataFile() {
        return new File(getFilesDir(), "todo.txt");
    }

    private void readItems() throws IOException {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading File", e);
            items = new ArrayList<>();
        }
    }
        private void writeItems(){
            try {
                FileUtils.writeLines(getDataFile(), items);

            } catch (IOException e) {
                Log.e("MainActivity", "Error writing File", e);
            }

        }
    }


