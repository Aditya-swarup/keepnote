package com.example.keepnotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class notedetails extends AppCompatActivity {
    private TextView mtitleofnotedetail, mcontentofnotedetail;
    FloatingActionButton mgotoeditnote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notedetails);
        mtitleofnotedetail = findViewById(R.id.titleofnotedetail);
        mcontentofnotedetail = findViewById(R.id.contentofnotedetail);

        Toolbar toolbar = findViewById(R.id.toolbarofnotedetail);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Retrieve the Intent that started this activity
        Intent data = getIntent();

        // Get the values of "title," "content," and "noteId" extras from the Intent
        String title = data.getStringExtra("title");
        String content = data.getStringExtra("content");
        String noteId = data.getStringExtra("noteid");

        // Set the text of the title and content TextViews
        mtitleofnotedetail.setText(title);
        mcontentofnotedetail.setText(content);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Create an Intent to navigate back to the parent activity
            Intent upIntent = new Intent(this, notesActivity.class); // Replace ParentActivity with the actual parent activity class

            // Add any necessary flags to the intent
            upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Start the parent activity using the intent
            startActivity(upIntent);

            // Finish the current activity (optional)
            finish();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
