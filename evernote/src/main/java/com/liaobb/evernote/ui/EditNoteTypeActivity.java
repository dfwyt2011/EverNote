package com.liaobb.evernote.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.liaobb.evernote.R;
import com.liaobb.evernote.bean.NoteType;
import com.liaobb.evernote.common.NoteUtils;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import de.greenrobot.event.EventBus;

public class EditNoteTypeActivity extends ToolbarActivity {
    @InjectView(R.id.edit_root_view)
    LinearLayout editRootView;

    private MenuItem doneMenuItem;

    private List<MaterialEditText> exitNoteTypeEditText;
    private List<MaterialEditText> readyAddNoteTypeEditText;

    private List<NoteType> noteTypes;

    private static int exitItem = 0;
    private static int readyAddItem = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initEditTextView();
    }

    private void initEditTextView() {
        exitNoteTypeEditText = new ArrayList<MaterialEditText>();
        readyAddNoteTypeEditText = new ArrayList<MaterialEditText>();
        noteTypes = DataSupport.findAll(NoteType.class, true);
        for (int i = 0; i < NoteUtils.MAX_NOTE_TYPE_COUNT; i++) {
            MaterialEditText view = (MaterialEditText) getLayoutInflater().inflate(R.layout.edit_layout, null);
            if (i >= noteTypes.size()) {
                view.setHint(getString(R.string.add_note_type_item));
                readyAddNoteTypeEditText.add(view);
                view.addTextChangedListener(new SimpleTextWatcher(view, readyAddItem));
            } else {
                if (i == 0) {
                    view.setEnabled(false);
                    view.setFocusable(false);
                } else {
                    view.addTextChangedListener(new SimpleTextWatcher(view, exitItem));
                }
                view.setText(noteTypes.get(i).getNoteTypeString());
                //view.setSelection(lists.get(i).length());
                exitNoteTypeEditText.add(view);
            }
            editRootView.addView(view);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_note_type, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        doneMenuItem = menu.getItem(0);
        doneMenuItem.setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                for (int i = 0; i < noteTypes.size(); i++) {
                    NoteType noteType = noteTypes.get(i);
                    if (!noteType.getNoteTypeString().equals(exitNoteTypeEditText.get(i).getText().toString())) {
                        if (TextUtils.isEmpty(exitNoteTypeEditText.get(i).getText().toString())) {
                            noteType.setNoteTypeString(getString(R.string.not_define_note_type));
                        } else {
                            noteType.setNoteTypeString(exitNoteTypeEditText.get(i).getText().toString());
                        }
                        noteType.save();
                    }
                }
                for (MaterialEditText editText : readyAddNoteTypeEditText) {
                    int noteTypeLength = noteTypes.size();
                    if (!TextUtils.isEmpty(editText.getText().toString())) {
                        NoteType newNoteType = new NoteType();
                        newNoteType.setNoteType(noteTypeLength);
                        noteTypeLength++;
                        newNoteType.setNoteTypeString(editText.getText().toString());
                        newNoteType.save();
                    }
                }
                EventBus.getDefault().post(NoteUtils.NOTE_TYPE_UPDATE_EVENT);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected String getToolbarTitle() {
        return getString(R.string.edit_note_type_title);
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_edit_note_type;
    }

    class SimpleTextWatcher implements TextWatcher {
        private MaterialEditText view;
        private int type;

        public SimpleTextWatcher(MaterialEditText view, int type) {
            this.type = type;
            this.view = view;
        }


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (doneMenuItem == null)
                return;
            if (TextUtils.isEmpty(view.getText().toString())) {
                if (type == exitItem) {
                    view.setHint(getString(R.string.delete_note_type_hint));
                } else if (type == readyAddItem) {
                    view.setHint(getString(R.string.add_note_type_item));
                }
            } else {
                doneMenuItem.setVisible(true);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

}
