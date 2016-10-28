package com.testcode.fabianvalenciacodetest;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.github.fabtransitionactivity.SheetLayout;
import com.testcode.fabianvalenciacodetest.adapters.RVAdapter;
import com.testcode.fabianvalenciacodetest.adapters.TestAdapter;
import com.testcode.fabianvalenciacodetest.data.ContactProvider;
import com.testcode.fabianvalenciacodetest.models.Contacts;

import atownsend.swipeopenhelper.SwipeOpenItemTouchHelper;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements TestAdapter.ButtonCallbacks,SheetLayout.OnFabAnimationEndListener, LoaderManager.LoaderCallbacks<Cursor>,RVAdapter.ButtonCallbacks {

    private TestAdapter adapter;
    private SwipeOpenItemTouchHelper helper;
    @BindView(R.id.bottom_sheet)
    SheetLayout mSheetLayout;
    @BindView(R.id.fab)
    ImageView mFab;
    RVAdapter rvaAdapter;
    RecyclerView recyclerView;

    private static final int CONTACT_LOADER = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        rvaAdapter = new RVAdapter(getApplicationContext(),this);
        adapter = new TestAdapter(this, false, this);
        helper = new SwipeOpenItemTouchHelper(new SwipeOpenItemTouchHelper.SimpleCallback(
                SwipeOpenItemTouchHelper.START | SwipeOpenItemTouchHelper.END));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(rvaAdapter);
        helper.attachToRecyclerView(recyclerView);
        helper.setCloseOnAction(false);
        if (savedInstanceState != null) {
            helper.restoreInstanceState(savedInstanceState);
        }
        ButterKnife.bind(this);
        Stetho.initializeWithDefaults(this);
        mSheetLayout.setFab(mFab);
        mSheetLayout.setFabAnimationEndListener(this);
        getSupportLoaderManager().initLoader(CONTACT_LOADER,null,this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        helper.onSaveInstanceState(outState);
    }

    @Override
    public void removePosition(int position) {
        adapter.removePosition(position);
    }

    @Override
    public void editPosition(int position) {
        Toast.makeText(MainActivity.this, "Edit position: " + position, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.fab)
    void onFabClick() {
        mSheetLayout.expandFab();
    }

    @Override
    public void onFabAnimationEnd() {
        Intent intent = new Intent(this, AddContactActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivityForResult(intent, 101);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101){
            mSheetLayout.contractFab();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cl = new CursorLoader(this, ContactProvider.Contacts.CONTENT_URI, null,
         null, null, null);
         return cl;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        rvaAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        rvaAdapter.swapCursor(null);
    }


    @Override
    public void removePosition(Contacts contact) {

    }

    @Override
    public void editPosition(Contacts contact) {

    }
}
