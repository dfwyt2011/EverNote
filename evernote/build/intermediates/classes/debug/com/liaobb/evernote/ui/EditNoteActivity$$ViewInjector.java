// Generated code from Butter Knife. Do not modify!
package com.liaobb.evernote.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class EditNoteActivity$$ViewInjector<T extends com.liaobb.evernote.ui.EditNoteActivity> extends com.liaobb.evernote.ui.ToolbarActivity$$ViewInjector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    super.inject(finder, target, source);

    View view;
    view = finder.findRequiredView(source, 2131624042, "field 'noteTitleEditText'");
    target.noteTitleEditText = finder.castView(view, 2131624042, "field 'noteTitleEditText'");
    view = finder.findRequiredView(source, 2131624043, "field 'noteContentEditText'");
    target.noteContentEditText = finder.castView(view, 2131624043, "field 'noteContentEditText'");
    view = finder.findRequiredView(source, 2131624044, "field 'createTimeLine'");
    target.createTimeLine = finder.castView(view, 2131624044, "field 'createTimeLine'");
    view = finder.findRequiredView(source, 2131624045, "field 'lastEditorTimeLine'");
    target.lastEditorTimeLine = finder.castView(view, 2131624045, "field 'lastEditorTimeLine'");
    view = finder.findRequiredView(source, 2131624041, "field 'noteTypeSpinner'");
    target.noteTypeSpinner = finder.castView(view, 2131624041, "field 'noteTypeSpinner'");
  }

  @Override public void reset(T target) {
    super.reset(target);

    target.noteTitleEditText = null;
    target.noteContentEditText = null;
    target.createTimeLine = null;
    target.lastEditorTimeLine = null;
    target.noteTypeSpinner = null;
  }
}
