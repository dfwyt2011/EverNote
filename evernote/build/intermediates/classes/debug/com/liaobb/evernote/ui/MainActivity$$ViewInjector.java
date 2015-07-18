// Generated code from Butter Knife. Do not modify!
package com.liaobb.evernote.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class MainActivity$$ViewInjector<T extends com.liaobb.evernote.ui.MainActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624040, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131624040, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131624047, "field 'mDrawerLayout'");
    target.mDrawerLayout = finder.castView(view, 2131624047, "field 'mDrawerLayout'");
    view = finder.findRequiredView(source, 2131624050, "field 'mDrawerListView'");
    target.mDrawerListView = finder.castView(view, 2131624050, "field 'mDrawerListView'");
    view = finder.findRequiredView(source, 2131624049, "field 'drawerRootView'");
    target.drawerRootView = view;
    view = finder.findRequiredView(source, 2131624052, "field 'exitAppBut' and method 'onExitApp'");
    target.exitAppBut = finder.castView(view, 2131624052, "field 'exitAppBut'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onExitApp();
        }
      });
    view = finder.findRequiredView(source, 2131624051, "field 'editNoteTypeBut' and method 'onEditNoteType'");
    target.editNoteTypeBut = finder.castView(view, 2131624051, "field 'editNoteTypeBut'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onEditNoteType();
        }
      });
  }

  @Override public void reset(T target) {
    target.toolbar = null;
    target.mDrawerLayout = null;
    target.mDrawerListView = null;
    target.drawerRootView = null;
    target.exitAppBut = null;
    target.editNoteTypeBut = null;
  }
}
