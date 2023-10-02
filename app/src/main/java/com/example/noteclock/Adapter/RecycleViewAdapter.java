package com.example.noteclock.Adapter;

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

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.viewHolder>{
    private List<Note> list;

    private ItemListener itemListener;

    public RecycleViewAdapter() {
        list = new ArrayList<>();
    }
    public void setNotes(List<Note> notes) {
        this.list = notes;
        notifyDataSetChanged();
    }

    public Note getNote(int position) {
        return list.get(position);
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_note, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Note note = list.get(position);
        holder.tvTitle.setText("Tiêu đề: "+note.getTieuDe());
        holder.tvContent.setText("Nội dung: "+note.getNoiDung());
        holder.tvDate.setText("Ngày: "+note.getNgay());
        holder.tvTime.setText("Giờ: "+note.getGio());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }
    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tvTitle, tvContent, tvDate, tvTime;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.txt_title);
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
