package com.example.btap20_11_2025_sqlitedtb.models;

import java.io.Serializable;

public class NotesModel implements Serializable {
    private  int idNote;
    private String NameNote;

    public NotesModel(int idNote, String nameNote) {
        this.idNote = idNote;
        NameNote = nameNote;
    }

    public int getIdNote() {
        return idNote;
    }

    public void setIdNote(int idNote) {
        this.idNote = idNote;
    }

    public String getNameNote() {
        return NameNote;
    }

    public void setNameNote(String nameNote) {
        NameNote = nameNote;
    }
}
