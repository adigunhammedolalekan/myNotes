package com.example.root.mynote.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.root.mynote.R;
import com.example.root.mynote.entities.Note;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 11/7/17.
 */

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

    private List<Note> noteList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private INoteClickListener noteClickListener;

    public NoteListAdapter() {}

    public NoteListAdapter(Context context, List<Note> notes, INoteClickListener noteClickListener) {
        noteList = notes;
        mContext = context;
        if(mContext != null) {
            mLayoutInflater = LayoutInflater.from(mContext);
        }
        this.noteClickListener = noteClickListener;
    }

    public void setNoteClickListener(INoteClickListener noteClickListener) {
        this.noteClickListener = noteClickListener;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(mLayoutInflater == null) mLayoutInflater = LayoutInflater.from(parent.getContext());

        return new NoteViewHolder(mLayoutInflater.inflate(R.layout.layout_note, parent, false));
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {

        final Note nextNote = noteList.get(position);
        holder.content.setText(nextNote.getContent());
        holder.title.setText(nextNote.getTitle());
        holder.timeAdded.setText(nextNote.getTimeAdded());

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(noteClickListener != null)
                    noteClickListener.onClick(nextNote);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_note_title_note_layout)
        TextView title;
        @BindView(R.id.tv_note_content_note_layout)
        TextView content;
        @BindView(R.id.tv_note_time_added)
        TextView timeAdded;
        @BindView(R.id.layout_note_root)
        RelativeLayout root;

        public NoteViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
    public interface INoteClickListener {
        void onClick(Note note);
    }
}
