package com.liaobb.evernote.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.liaobb.evernote.R;
import com.liaobb.evernote.bean.Note;
import com.liaobb.evernote.common.NoteUtils;
import com.liaobb.evernote.ui.EditNoteActivity;
import com.liaobb.evernote.ui.NoteListFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by liaobb on 2015/7/9.
 */
public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteListViewHolder> implements Filterable {
    private Context context;
    private List<Note> allNoteList = new ArrayList<Note>();
    private List<Note> noteList;

    private int currentNoteType;

    private boolean[] showItemIndicator = {true, true, true};
    //private int orderListIndicator = 0;

    public void setCurrentNoteType(int currentNoteType) {
        this.currentNoteType = currentNoteType;
    }

    public void setCurrentNoteTypeString(String currentNoteTypeString) {
        this.currentNoteTypeString = currentNoteTypeString;
    }

    private String currentNoteTypeString;

    public NoteListAdapter(Context context, List<Note> noteList, String currentNoteTypeString) {
        this.context = context;
        this.noteList = noteList;
        initAllNoteList();

        this.currentNoteTypeString = currentNoteTypeString;
    }

    @Override
    public NoteListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoteListViewHolder(LayoutInflater.from(context).inflate(R.layout.notes_item_layout, parent, false));
    }

    /*private OnCardViewItemClickListener cardViewItemClickListener;

    public interface OnCardViewItemClickListener {
        void onCardItemClick(Note currentNote);
    }

    public void setCardViewItemClickListener(OnCardViewItemClickListener cardViewItemClickListener) {
        this.cardViewItemClickListener = cardViewItemClickListener;
    }*/


    @Override
    public void onBindViewHolder(final NoteListViewHolder holder, final int position) {
        final Note currentNote = noteList.get(position);

        holder.titleTextView.setText(currentNote.getNoteTitle());
        holder.contentTextView.setText(currentNote.getNoteContent());

        initShowItems(holder, currentNote);
        /*holder.typeTextView.setText(currentNoteTypeString);
        holder.createTimeTextView.setText(context.getString(R.string.create_time_line_default) + NoteUtils.changeToGraceTimeFormat(context, currentNote.getCreateTime()));
        holder.lastEditorTimeTextView.setText(context.getString(R.string.editor_time_line_default) + NoteUtils.changeToGraceTimeFormat(context, currentNote.getLastEditorTime()));*/

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditNoteActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(NoteListFragment.NOTE_INIT_TYPE, currentNoteType);
                bundle.putInt(NoteListFragment.EDIT_NOTE_ID, currentNote.getNoteId());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.delete_note_hint);
                DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                if (currentNote.isSaved()) {
                                    currentNote.delete();
                                }
                                noteList.remove(position);
                                allNoteList.remove(position);
                                //NoteListAdapter.this.notifyItemRemoved(position);
                                NoteListAdapter.this.notifyDataSetChanged();
                                Toast.makeText(context, context.getString(R.string.delete_note_success), Toast.LENGTH_SHORT).show();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                            default:
                                break;
                        }
                    }
                };
                builder.setPositiveButton(R.string.sure, listener);
                builder.setNegativeButton(R.string.cancel, listener);
                builder.show();
                return true;
            }
        });
    }

    private void initShowItems(NoteListViewHolder holder, Note currentNote) {
        if (showItemIndicator[0]) {
            holder.typeTextView.setVisibility(View.VISIBLE);
            holder.typeTextView.setText(currentNoteTypeString);
        } else {
            holder.typeTextView.setVisibility(View.GONE);
        }
        if (showItemIndicator[1]) {
            holder.createTimeTextView.setVisibility(View.VISIBLE);
            holder.createTimeTextView.setText(context.getString(R.string.create_time_line_default) + NoteUtils.changeToGraceTimeFormat(context, currentNote.getCreateTime()));
        } else {
            holder.createTimeTextView.setVisibility(View.GONE);
        }
        if (showItemIndicator[2]) {
            holder.lastEditorTimeTextView.setVisibility(View.VISIBLE);
            holder.lastEditorTimeTextView.setText(context.getString(R.string.editor_time_line_default) + NoteUtils.changeToGraceTimeFormat(context, currentNote.getLastEditorTime()));
        } else {
            holder.lastEditorTimeTextView.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return noteList.size();
    }

    private void initAllNoteList() {
        allNoteList.clear();
        allNoteList.addAll(noteList);
    }

    public void setNoteList(List<Note> newNoteList) {
        noteList.clear();
        noteList.addAll(newNoteList);
        initAllNoteList();
        notifyDataSetChanged();
    }

    public void setNoteListNotInitAllNoteList(List<Note> newNoteList) {
        noteList.clear();
        noteList.addAll(newNoteList);
        notifyDataSetChanged();
    }

    public void clearNoteList() {
        noteList.clear();
        allNoteList.clear();
        notifyDataSetChanged();
    }

    public void addNote(Note note) {
        noteList.add(0, note);
        allNoteList.add(0, note);
        notifyItemInserted(0);
    }

    /*public void remove(int position) {
        noteList.remove(position);
        notifyItemRemoved(position);
    }*/

    public void setItemVisiable(boolean[] showItemIndicator) {
        this.showItemIndicator = showItemIndicator;
        notifyDataSetChanged();
    }

    public void orderNoteList(int orderIndicator) {
        //this.orderListIndicator = orderIndicator;
        Comparator<Note> comparator = null;
        switch (orderIndicator) {
            case NoteUtils.NOTE_LIST_CREATE_TIME_ORDER:
                comparator = new Comparator<Note>() {
                    @Override
                    public int compare(Note note1, Note note2) {
                        //return note1.getCreateTime() - note2.getCreateTime();
                        if (note1.getCreateTime() > note2.getCreateTime()) {
                            return -1;
                        } else {
                            return 1;
                        }
                    }
                };
                break;
            case NoteUtils.NOTE_LIST_LAST_EDITTIME_ORDER:
                comparator = new Comparator<Note>() {
                    @Override
                    public int compare(Note note1, Note note2) {
                        if (note1.getLastEditorTime() > note2.getLastEditorTime()) {
                            return -1;
                        } else {
                            return 1;
                        }
                    }
                };
                break;
            case NoteUtils.NOTE_LIST_TITLE_ORDER:
                comparator = new Comparator<Note>() {
                    @Override
                    public int compare(Note note1, Note note2) {
                        return note1.getNoteTitle().compareTo(note2.getNoteTitle());
                    }
                };
                break;
            default:
                break;
        }
        Collections.sort(noteList, comparator);
        initAllNoteList();
        /*for (Note note : noteList) {
            Log.d(App.TAG, "note title:" + note.getNoteTitle() + " create:" + note.getCreateTime()
                    + " last:" + note.getLastEditorTime());
        }*/
        notifyDataSetChanged();
    }

    class NoteListViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView titleTextView;
        TextView typeTextView;
        TextView contentTextView;
        TextView createTimeTextView;
        TextView lastEditorTimeTextView;


        public NoteListViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.note_card);
            titleTextView = (TextView) itemView.findViewById(R.id.note_title_text);
            typeTextView = (TextView) itemView.findViewById(R.id.note_type_text);
            contentTextView = (TextView) itemView.findViewById(R.id.note_content_text);
            createTimeTextView = (TextView) itemView.findViewById(R.id.note_create_time_text);
            lastEditorTimeTextView = (TextView) itemView.findViewById(R.id.note_last_edit_time_text);
        }
    }

    @Override
    public Filter getFilter() {
        return new NoteFilter(this, allNoteList);
    }

    private static class NoteFilter extends Filter {

        private final NoteListAdapter adapter;

        //private final List<Note> originalList;

        private final List<Note> filteredList;

        private final List<Note> allNoteList;

        private NoteFilter(NoteListAdapter adapter, List<Note> allNoteList) {
            super();
            this.adapter = adapter;
            //this.originalList = originalList;
            this.filteredList = new ArrayList<>();
            this.allNoteList = allNoteList;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredList.clear();
            final FilterResults results = new FilterResults();
            if (constraint.length() == 0) {
                filteredList.addAll(allNoteList);
            } else {
                for (Note note : allNoteList) {
                    if (note.getNoteTitle().contains(constraint) || note.getNoteContent().contains(constraint)) {
                        filteredList.add(note);
                    }
                }
            }
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //adapter.setNoteList((ArrayList<Note>) results.values);
            adapter.setNoteListNotInitAllNoteList((ArrayList<Note>) results.values);
        }
    }
}
