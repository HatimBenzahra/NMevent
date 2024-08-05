package com.example.nmevents;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class CreateEventActivity extends AppCompatActivity {

    private EditText eventTitleEditText, eventDescriptionEditText, eventLocationEditText;
    private Button eventDateButton, eventTimeButton, createEventButton;
    private int year, month, day, hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        eventTitleEditText = findViewById(R.id.eventTitleEditText);
        eventDescriptionEditText = findViewById(R.id.eventDescriptionEditText);
        eventLocationEditText = findViewById(R.id.eventLocationEditText);
        eventDateButton = findViewById(R.id.eventDateButton);
        eventTimeButton = findViewById(R.id.eventTimeButton);
        createEventButton = findViewById(R.id.createEventButton);

        eventDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateEventActivity.this,
                        (view, year, monthOfYear, dayOfMonth) -> eventDateButton.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year), year, month, day);
                datePickerDialog.show();
            }
        });

        eventTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR_OF_DAY);
                minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateEventActivity.this,
                        (view, hourOfDay, minute) -> eventTimeButton.setText(hourOfDay + ":" + minute), hour, minute, false);
                timePickerDialog.show();
            }
        });

        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = eventTitleEditText.getText().toString().trim();
                String description = eventDescriptionEditText.getText().toString().trim();
                String location = eventLocationEditText.getText().toString().trim();
                String date = eventDateButton.getText().toString().trim();
                String time = eventTimeButton.getText().toString().trim();

                if (title.isEmpty() || description.isEmpty() || location.isEmpty() || date.equals("Sélectionner la Date") || time.equals("Sélectionner l'Heure")) {
                    Toast.makeText(CreateEventActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Code pour créer l'événement ici

                Toast.makeText(CreateEventActivity.this, "Événement créé avec succès", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
