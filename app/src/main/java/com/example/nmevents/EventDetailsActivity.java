package com.example.nmevents;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class EventDetailsActivity extends AppCompatActivity {

    private TextView titleTextView;
    private TextView dateTextView;
    private TextView locationTextView;
    private TextView descriptionTextView;
    private Button buttonEdit;
    private Button buttonDelete;

    private Event event;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        titleTextView = findViewById(R.id.eventDetailTitleTextView);
        dateTextView = findViewById(R.id.eventDetailDateTextView);
        locationTextView = findViewById(R.id.eventDetailLocationTextView);
        descriptionTextView = findViewById(R.id.eventDetailDescriptionTextView);
        buttonEdit = findViewById(R.id.editEventButton);
        buttonDelete = findViewById(R.id.deleteEventButton);

        db = FirebaseFirestore.getInstance();

        event = (Event) getIntent().getSerializableExtra("event");
        if (event != null) {
            titleTextView.setText(event.getName());
            dateTextView.setText(event.getDate());
            locationTextView.setText(event.getLocation());
            descriptionTextView.setText(event.getDescription());
        } else {
            String eventId = getIntent().getStringExtra("eventId");
            loadEventDetails(eventId);
        }

        buttonEdit.setOnClickListener(v -> {
            Intent intent = new Intent(EventDetailsActivity.this, CreateEventActivity.class);
            intent.putExtra("event", event);
            startActivity(intent);
        });

        buttonDelete.setOnClickListener(v -> deleteEvent());
    }

    private void loadEventDetails(String eventId) {
        db.collection("events").document(eventId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        event = documentSnapshot.toObject(Event.class);
                        if (event != null) {
                            event.setId(documentSnapshot.getId());
                            titleTextView.setText(event.getName());
                            dateTextView.setText(event.getDate());
                            locationTextView.setText(event.getLocation());
                            descriptionTextView.setText(event.getDescription());
                        }
                    } else {
                        Toast.makeText(EventDetailsActivity.this, "Event not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(EventDetailsActivity.this, "Failed to load event details", Toast.LENGTH_SHORT).show();
                });
    }

    private void deleteEvent() {
        if (event != null && event.getId() != null) {
            db.collection("events").document(event.getId())
                    .delete()
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(EventDetailsActivity.this, "Event deleted", Toast.LENGTH_SHORT).show();
                        setResult(MainActivity.RESULT_EVENT_UPDATED);
                        finish();
                    })
                    .addOnFailureListener(e -> Toast.makeText(EventDetailsActivity.this, "Error deleting event", Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(this, "Error: Event ID is null", Toast.LENGTH_SHORT).show();
        }
    }
}
