package com.neocaptainnemo.notesapp13december.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.neocaptainnemo.notesapp13december.R;
import com.neocaptainnemo.notesapp13december.domain.Note;
import com.neocaptainnemo.notesapp13december.ui.adapter.AdapterItem;
import com.neocaptainnemo.notesapp13december.ui.adapter.HeaderAdapterItem;
import com.neocaptainnemo.notesapp13december.ui.adapter.NoteAdapterItem;

import java.util.ArrayList;
import java.util.Collection;

public class NotesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_NOTE = 1;

    private final ArrayList<AdapterItem> data = new ArrayList<>();

    private OnClick onClick;

    public void setData(Collection<AdapterItem> notes) {
        data.clear();
        data.addAll(notes);
    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position) instanceof NoteAdapterItem) {
            return TYPE_NOTE;
        }

        if (data.get(position) instanceof HeaderAdapterItem) {
            return TYPE_HEADER;
        }

        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_NOTE) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
            return new NoteViewHolder(itemView);
        }

        if (viewType == TYPE_HEADER) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header, parent, false);
            return new HeaderViewHolder(itemView);
        }

        throw new IllegalStateException("Cannot create this type of view holder");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof NoteViewHolder) {

            NoteViewHolder noteViewHolder = (NoteViewHolder) holder;

            NoteAdapterItem note = (NoteAdapterItem) data.get(position);

            noteViewHolder.getTitle().setText(note.getTitle());
            noteViewHolder.getMessage().setText(note.getMessage());
            noteViewHolder.getDate().setText(note.getTime());
        }

        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;

            String header = ((HeaderAdapterItem) data.get(position)).getHeader();

            headerViewHolder.header.setText(header);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public OnClick getOnClick() {
        return onClick;
    }

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    interface OnClick {
        void onClick(Note note);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;

        private final TextView message;

        private final TextView date;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);

            message = itemView.findViewById(R.id.content);

            date = itemView.findViewById(R.id.date);

            itemView.findViewById(R.id.card).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AdapterItem item = data.get(getAdapterPosition());

                    if (item instanceof NoteAdapterItem) {
                        if (getOnClick() != null) {
                            getOnClick().onClick(((NoteAdapterItem) item).getNote());
                        }
                    }
                }
            });
        }

        public TextView getTitle() {
            return title;
        }

        public TextView getMessage() {
            return message;
        }

        public TextView getDate() {
            return date;
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final TextView header;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);

            header = itemView.findViewById(R.id.header);
        }
    }
}
