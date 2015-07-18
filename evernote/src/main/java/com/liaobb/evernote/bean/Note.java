package com.liaobb.evernote.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by liaobb on 2015/7/10.
 */
public class Note extends DataSupport {

    private int noteId;
    private NoteType noteType;
    private String noteTitle;
    private String noteContent;
    private long lastEditorTime;
    private long createTime;

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getLastEditorTime() {
        return lastEditorTime;
    }

    public void setLastEditorTime(long lastEditorTime) {
        this.lastEditorTime = lastEditorTime;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public NoteType getNoteType() {
        return noteType;
    }

    public void setNoteType(NoteType noteType) {
        this.noteType = noteType;
    }
}
