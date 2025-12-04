package com.example.btap20_11_2025_sqlitedtb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btap20_11_2025_sqlitedtb.MainActivity;
import com.example.btap20_11_2025_sqlitedtb.R;
import com.example.btap20_11_2025_sqlitedtb.models.NotesModel;

import java.util.List;

public class NotesAdapter extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private List<NotesModel> noteList;

    public NotesAdapter(MainActivity context, int layout, List<NotesModel> noteList) {
        this.context = context;
        this.layout = layout;
        this.noteList = noteList;
    }
    @Override
    public int getCount() {
        return noteList.size();
    }

    @Override
    public Object getItem(int position) {
        return noteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder{
        TextView textViewNote;
        ImageView imageViewDelete, imageViewEdit;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.textViewNote = (TextView) convertView.findViewById(R.id.textViewNote);
            holder.imageViewDelete = (ImageView) convertView.findViewById(R.id.imageViewDelete);
            holder.imageViewEdit = (ImageView)convertView.findViewById(R.id.imageViewEdit);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        final NotesModel notes = noteList.get(position);
        holder.textViewNote.setText(notes.getNameNote());
        // bat su kien nut capnhat
        holder.imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(context, "Cập nhật" + notes.getNameNote(), Toast.LENGTH_SHORT);
                toast.show();
                context.DialogCapNhatNotes(notes.getNameNote(), notes.getIdNote());

            }
        });
        // bat su kien xoa
        holder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(context, "Xóa" + notes.getNameNote(), Toast.LENGTH_SHORT);
                toast.show();
                context.DialogXoaNotes(notes.getNameNote(), notes.getIdNote());
            }
        });
        return convertView;
    }
}
