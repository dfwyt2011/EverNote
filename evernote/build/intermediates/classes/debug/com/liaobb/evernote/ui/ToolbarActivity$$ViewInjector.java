// Generated code from Butter Knife. Do not modify!
package com.liaobb.evernote.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class ToolbarActivity$$ViewInjector<T extends com.liaobb.evernote.ui.ToolbarActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624040, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131624040, "field 'toolbar'");
  }

  @Override public void reset(T target) {
    target.toolbar = null;
  }
}
