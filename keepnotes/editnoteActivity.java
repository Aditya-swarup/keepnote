package com.example.keepnotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class editnoteActivity extends AppCompatActivity {

    EditText medittitleofnote, meditcontentofnote;
    FloatingActionButton msaveeditnote;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    boolean cameFromDetails; // To check if you came from notesdetailsActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editnote);

        // Initialize views
        medittitleofnote = findViewById(R.id.edittitleofnote);
        meditcontentofnote = findViewById(R.id.editcontentofnote);
        msaveeditnote = findViewById(R.id.saveeditnote);

        // Get data from the intent
        Intent data = getIntent();
        if (data == null) {
            // Handle the case when the Intent is null
            finish(); // Close the activity
            return;
        }

        cameFromDetails = data.getBooleanExtra("cameFromDetails", false); // Check if you came from notesdetailsActivity

        String notetitle = data.getStringExtra("title");
        String notecontent = data.getStringExtra("content");

        // Check for potential null values and handle them
        if (notetitle == null || notecontent == null) {
            Toast.makeText(getApplicationContext(), "Note data is missing", Toast.LENGTH_LONG).show();
            finish(); // Close the activity
            return;
        }

        // Set the text of the title and content EditTexts
        medittitleofnote.setText(notetitle);
        meditcontentofnote.setText(notecontent);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        // Set up the Toolbar with back button
        Toolbar toolbar = findViewById(R.id.toolbarofeditnote);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set an OnClickListener for the save button
        msaveeditnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle save operation here
                // For example, you can save the updated note and then navigate to the appropriate parent activity
                // You can also show a Toast message to confirm the save
                String newtitle = medittitleofnote.getText().toString();
                String newcontent = meditcontentofnote.getText().toString();
                if (newtitle.isEmpty() || newcontent.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Something is empty", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    DocumentReference documentReference = firebaseFirestore.collection("notes")
                            .document(firebaseUser.getUid())
                            .collection("mynotes")
                            .document(data.getStringExtra("noteid"));

                    Map<String, Object> note = new HashMap<>();
                    note.put("title", newtitle);
                    note.put("content", newcontent);

                    documentReference.set(note)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(), "Note is Updated", Toast.LENGTH_LONG).show();

                                    // Navigate to the appropriate parent activity
                                    if (cameFromDetails) {
                                        Intent intent = new Intent(editnoteActivity.this, notedetails.class);
                                        intent.putExtra("noteid", data.getStringExtra("noteid"));
                                        startActivity(intent);
                                    } else {
                                        Intent intent = new Intent(editnoteActivity.this, notesActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Failed to update", Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Navigate back to the appropriate parent activity
            if (cameFromDetails) {
                Intent upIntent = new Intent(this, notedetails.class);
                upIntent.putExtra("noteid", getIntent().getStringExtra("noteid"));
                startActivity(upIntent);
            } else {
                Intent upIntent = new Intent(this, notesActivity.class);
                upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(upIntent);
            }
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
