package com.example.hitlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HitListAdapter extends RecyclerView.Adapter<HitListAdapter.ViewHolder> {
    private List<Person> people;

    public HitListAdapter(List<Person> people) {
        this.people = people;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameTextView.setText(people.get(position).name);
    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    // Method to get a person at a specific position
    public Person getPersonAtPosition(int position) {
        return people.get(position);
    }

    // Method to remove a person at a specific position
    public void removePersonAtPosition(int position) {
        people.remove(position);
        notifyItemRemoved(position);
    }

    // Method to add a person to the list
    public void addPerson(Person person) {
        people.add(person);
        notifyItemInserted(people.size() - 1);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;

        public ViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(android.R.id.text1);
        }
    }
}
