// Generated code from Butter Knife. Do not modify!
package com.liaobb.evernote.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class EditNoteTypeActivity$$ViewInjector<T extends com.liaobb.evernote.ui.EditNoteTypeActivity> extends com.liaobb.evernote.ui.ToolbarActivity$$ViewInjector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    super.inject(finder, target, source);

    View view;
    view = finder.findRequiredView(source, 2131624046, "field 'editRootView'");
    target.editRootView = finder.castView(view, 2131624046, "field 'editRootView'");
  }

  @Override public void reset(T target) {
    super.reset(target);

    target.editRootView = null;
  }
}
