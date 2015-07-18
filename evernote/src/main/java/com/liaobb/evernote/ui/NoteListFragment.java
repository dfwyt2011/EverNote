package com.liaobb.evernote.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liaobb.evernote.R;
import com.liaobb.evernote.adapter.NoteListAdapter;
import com.liaobb.evernote.bean.Note;
import com.liaobb.evernote.bean.NoteType;
import com.liaobb.evernote.common.App;

import org.litepal.crud.DataSupport;

import java.util.Collections;
import java.util.List;

/**
 * Created by liaobb on 2015/7/8.
 */
public class NoteListFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    public static String NOTE_INIT_TYPE = "note_init_type";
    public static String NOTE_LAYOUT_TYPE = "note_layout_type";
    public static String EDIT_NOTE_ID = "edit_note_id";

    private int noteTypeItem;
    private NoteType currentNoteType;
    private List<Note> noteList;
    private NoteListAdapter noteListAdapter;

    private boolean cardLayout;

    private RecyclerView recycleView;
    private FloatingActionButton floatingActionButton;
    private SwipeRefreshLayout refreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        noteTypeItem = bundle.getInt(NOTE_INIT_TYPE);
        cardLayout = bundle.getBoolean(NOTE_LAYOUT_TYPE);
        currentNoteType = (DataSupport.where("notetype = ?", String.valueOf(noteTypeItem)).find(NoteType.class, true)).get(0);
        noteList = currentNoteType.getNoteList();
        reverseNoteList(noteList);

        noteListAdapter = new NoteListAdapter(getActivity(), noteList, currentNoteType.getNoteTypeString());
        noteListAdapter.setCurrentNoteType(noteTypeItem);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note_list_all, container, false);
        return view;
    }

    private int lastVisiableItem;
    private LinearLayoutManager linearLayoutManager;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.addNoteFloatingButton);
        floatingActionButton.setOnClickListener(this);

        recycleView = (RecyclerView) view.findViewById(R.id.notelist_recycleView);
        recycleView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = selectLayoutManager(cardLayout);
        if (layoutManager instanceof LinearLayoutManager) {
            linearLayoutManager = (LinearLayoutManager) layoutManager;
            staggeredGridLayoutManager = null;
        } else {
            linearLayoutManager = null;
            staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
        }

        recycleView.setAdapter(noteListAdapter);

        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    floatingActionButton.setVisibility(View.VISIBLE);
                    if (lastVisiableItem + 1 == noteListAdapter.getItemCount()) {
                        //此处实现上拉加载更多，更新noteListAdapter，从数据库中取下一页的数据出来
                        Log.d(App.TAG, "reload next page note");
                    }
                } else {
                    floatingActionButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (staggeredGridLayoutManager == null) {
                    lastVisiableItem = linearLayoutManager.findLastVisibleItemPosition();
                } else {
                    int[] lastVisiableItemArray = staggeredGridLayoutManager.findLastVisibleItemPositions(null);
                    for (int i = 0; i < lastVisiableItemArray.length; i++) {
                        if (lastVisiableItem < lastVisiableItemArray[i]) {
                            lastVisiableItem = lastVisiableItemArray[i];
                        }
                    }
                }
            }
        });


        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresher);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        refreshLayout.setOnRefreshListener(this);
    }

    public static NoteListFragment newInstance(int noteType, boolean cardLayout) {
        Bundle bundle = new Bundle();
        bundle.putInt(NOTE_INIT_TYPE, noteType);
        bundle.putBoolean(NOTE_LAYOUT_TYPE, cardLayout);
        NoteListFragment noteListFragment = new NoteListFragment();
        noteListFragment.setArguments(bundle);
        return noteListFragment;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), EditNoteActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(NOTE_INIT_TYPE, noteTypeItem);
        bundle.putInt(EDIT_NOTE_ID, -1);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void reloadNoteListAsSelectNoteType(int selectNoteItem) {
        noteTypeItem = selectNoteItem;
        NoteType selectNoteType = (DataSupport.where("notetype = ?", String.valueOf(selectNoteItem)).find(NoteType.class, true)).get(0);
        List<Note> selectNoteList = selectNoteType.getNoteList();
        noteListAdapter.setCurrentNoteType(selectNoteItem);
        noteListAdapter.setCurrentNoteTypeString(selectNoteType.getNoteTypeString());
        reverseNoteList(selectNoteList);
        noteListAdapter.setNoteList(selectNoteList);
    }

    public void clearNoteList() {
        noteListAdapter.clearNoteList();
    }

    public void reloadNewestAddNote() {
        noteListAdapter.addNote(DataSupport.findLast(Note.class));
        recycleView.scrollToPosition(0);
    }

    public void reverseNoteList(List<Note> notes) {
        Collections.reverse(notes);
    }

    public RecyclerView.LayoutManager selectLayoutManager(boolean selectCardLayout) {
        RecyclerView.LayoutManager layoutManager;
        if (selectCardLayout) {
            layoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
            recycleView.setLayoutManager(layoutManager);
        } else {
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recycleView.setLayoutManager(layoutManager);
        }
        cardLayout = selectCardLayout;
        return layoutManager;
    }

    public void setShowNoteListItem(boolean[] showItemIndicator) {
        noteListAdapter.setItemVisiable(showItemIndicator);
    }

    public void setorderNoteListType(int orderListIndicator) {
        noteListAdapter.orderNoteList(orderListIndicator);
    }

    public void filter(String s) {
        noteListAdapter.getFilter().filter(s);
    }

    public void hideFloatActionBut() {
        floatingActionButton.setVisibility(View.GONE);
    }

    public void showFloatActionBut() {
        floatingActionButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRefresh() {
        reloadNoteListAsSelectNoteType(noteTypeItem);
        refreshLayout.setRefreshing(false);
    }
}
