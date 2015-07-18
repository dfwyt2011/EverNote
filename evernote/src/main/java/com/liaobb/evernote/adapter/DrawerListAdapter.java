package com.liaobb.evernote.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.liaobb.evernote.R;
import com.liaobb.evernote.bean.NoteType;

import java.util.List;

/**
 * Created by liaobb on 2015/7/8.
 */
public class DrawerListAdapter extends BaseAdapter {
    private Context context;
    private List<NoteType> noteTypes;

    public DrawerListAdapter(Context context, List<NoteType> noteTypes) {
        this.context = context;
        this.noteTypes = noteTypes;
    }

    @Override
    public int getCount() {
        return noteTypes.size();
    }

    @Override
    public Object getItem(int position) {
        return noteTypes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.drawer_list_item_layout, parent, false);
            holder = new Holder();
            holder.textView = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.textView.setText(noteTypes.get(position).getNoteTypeString());

        return convertView;
    }

    public void refreshDrawerList(List<NoteType> newDataTypes) {
        noteTypes.clear();
        noteTypes.addAll(newDataTypes);
        notifyDataSetChanged();
    }

    static class Holder {
        TextView textView;
    }
}
