package com.example.nmevents;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private Context context;
    private List<Event> eventList;

    public EventAdapter(Context context, List<Event> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.eventTitleTextView.setText(event.getName());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EventDetailsActivity.class);
            intent.putExtra("eventId", event.getId());
            context.startActivity(intent);
        });


        holder.editEventIcon.setOnClickListener(v -> {
            Intent intent = new Intent(context, CreateEventActivity.class);
            intent.putExtra("event", event);
            ((Activity) context).startActivityForResult(intent, MainActivity.REQUEST_EVENT_DETAILS);
        });

        holder.deleteEventIcon.setOnClickListener(v -> new AlertDialog.Builder(context)
                .setTitle("Delete Event")
                .setMessage("Are you sure you want to delete this event?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("events").document(event.getId())
                            .delete()
                            .addOnSuccessListener(aVoid -> {
                                if (position < eventList.size()) {
                                    eventList.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, eventList.size());
                                    Toast.makeText(context, "Event deleted", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Event deleted", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(context, "Error deleting event", Toast.LENGTH_SHORT).show();
                            });
                })
                .setNegativeButton(android.R.string.no, null)
                .show());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {

        TextView eventTitleTextView;
        ImageView editEventIcon, deleteEventIcon;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventTitleTextView = itemView.findViewById(R.id.eventTitleTextView);
            editEventIcon = itemView.findViewById(R.id.editEventIcon);
            deleteEventIcon = itemView.findViewById(R.id.deleteEventIcon);
        }
    }
}
