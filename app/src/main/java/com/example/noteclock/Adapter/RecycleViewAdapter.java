package com.example.noteclock.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteclock.R;
import com.example.noteclock.model.Note;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.HomeViewHolder> {

    private List<Note> notes;

    private ItemListener itemListener;

    public RecycleViewAdapter() {
        notes = new ArrayList<>();
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public Note getNote(int position) {
        return notes.get(position);
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_note, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.tvTitle.setText("Tiêu đề: "+note.getTitle());
        holder.tvContent.setText("Nội dung: "+note.getContent());
        holder.tvDate.setText("Ngày: "+note.getDate());
        holder.tvTime.setText("Giờ: "+note.getTime());
        holder.tvTitle.setTextColor(Color.RED);
        holder.tvContent.setTextColor(Color.BLUE);
        holder.tvDate.setTextColor(Color.GREEN);
        holder.tvTime.setTextColor(Color.MAGENTA);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvTitle, tvContent, tvDate, tvTime;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvTime = itemView.findViewById(R.id.tv_time);
            itemView.setOnClickListener(this);
        }
    
        @Override
        public void onClick(View view) {
            if (itemListener != null) {
                itemListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public interface ItemListener {
        void onItemClick(View view, int position);
    }
}