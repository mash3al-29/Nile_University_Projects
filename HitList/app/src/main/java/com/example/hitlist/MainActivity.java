package com.example.hitlist;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HitListAdapter adapter;
    private HitListDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("The List");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Add dividers between list items
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        // Database setup
        db = Room.databaseBuilder(getApplicationContext(), HitListDatabase.class, "hitlist_db").allowMainThreadQueries().build();

        // Load data
        List<Person> people = db.personDao().getAll();
        adapter = new HitListAdapter(people);
        recyclerView.setAdapter(adapter);

        // Implement swipe-to-delete
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false; // No drag-and-drop functionality
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Person person = adapter.getPersonAtPosition(position);
                deletePerson(person);
                adapter.removePersonAtPosition(position);
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                    @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                    int actionState, boolean isCurrentlyActive) {
                View itemView = viewHolder.itemView;
                float itemHeight = itemView.getBottom() - itemView.getTop();
                float deleteButtonWidth = itemHeight;  // The width of the delete button is set to the height of the item

                if (dX > deleteButtonWidth) {
                    // Draw the red delete button
                    Paint paint = new Paint();
                    paint.setColor(Color.RED);
                    RectF background = new RectF(itemView.getRight() - deleteButtonWidth, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                    c.drawRect(background, paint);

                    // Draw the "Delete" text
                    Paint textPaint = new Paint();
                    textPaint.setColor(Color.WHITE);
                    textPaint.setTextSize(48);
                    textPaint.setTextAlign(Paint.Align.CENTER);
                    float textHeight = textPaint.descent() - textPaint.ascent();
                    float textOffset = (textHeight / 2) - textPaint.descent();
                    c.drawText("Delete", background.centerX(), background.centerY() + textOffset, textPaint);

                    // If the swipe is beyond the delete button width, lock the swipe
                    if (dX > itemView.getWidth() / 3) {
                        dX = itemView.getWidth() / 3;
                    }
                }

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            showAddNameDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAddNameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New Name");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = input.getText().toString();
                if (!name.isEmpty()) {
                    addPerson(name);
                } else {
                    Toast.makeText(MainActivity.this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void addPerson(String name) {
        Person person = new Person();
        person.name = name;

        db.personDao().insert(person);
        adapter.addPerson(person);
        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
    }

    private void deletePerson(Person person) {
        db.personDao().delete(person);
    }
}
