package com.testcode.fabianvalenciacodetest;

import android.content.AsyncQueryHandler;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import com.facebook.stetho.Stetho;
import com.github.fabtransitionactivity.SheetLayout;
import com.testcode.fabianvalenciacodetest.adapters.RVAdapter;
import com.testcode.fabianvalenciacodetest.data.ContactColumns;
import com.testcode.fabianvalenciacodetest.data.ContactProvider;
import com.testcode.fabianvalenciacodetest.models.Contacts;
import atownsend.swipeopenhelper.SwipeOpenItemTouchHelper;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements SheetLayout.OnFabAnimationEndListener, LoaderManager.LoaderCallbacks<Cursor>,RVAdapter.ButtonCallbacks {

    private SwipeOpenItemTouchHelper helper;
    @BindView(R.id.bottom_sheet)
    SheetLayout mSheetLayout;
    @BindView(R.id.fab)
    ImageView mFab;
    RVAdapter rvaAdapter;
    RecyclerView recyclerView;
    AsyncQueryHandler queryHandler;
    Contacts contact;

    private static final int CONTACT_LOADER = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        rvaAdapter = new RVAdapter(this);
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
        queryHandler = new AsyncQueryHandler(getContentResolver()) {
            @Override
            protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
                super.onQueryComplete(token, cookie, cursor);
                contact = createContactModel(cursor);
                editContactAct(contact);
            }
            @Override
            protected void onDeleteComplete(int token, Object cookie, int result) {
                super.onDeleteComplete(token, cookie, result);
                Log.e("DELETE","ITEM");
            }
        };
    }
    public Contacts createContactModel(Cursor cursor){
        int fnIndex = cursor.getColumnIndexOrThrow("First_Name");
        int lnIndex = cursor.getColumnIndexOrThrow("Last_Name");
        int pnIndex = cursor.getColumnIndexOrThrow("Phone_Number");
        int bdIndex = cursor.getColumnIndexOrThrow("Birth_Date");
        int zcIndex = cursor.getColumnIndexOrThrow("Zip_Code");
        cursor.moveToFirst();
        String fn= cursor.getString(fnIndex);
        String ln= cursor.getString(lnIndex);
        String pn= cursor.getString(pnIndex);
        String bd = cursor.getString(bdIndex);
        String zc = cursor.getString(zcIndex);
        return new Contacts(1,fn,ln,pn,bd,zc);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        helper.onSaveInstanceState(outState);
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
        // get contact delete it and reload loader
        Log.e("DELETE",contact.getFirst_name());
        String[] mArray = {contact.getFirst_name()};
        try {
            queryHandler.startDelete(2,null,ContactProvider.Contacts.CONTENT_URI,
                    ContactColumns.FIRST_NAME+"=?",mArray);
        }catch (SQLiteConstraintException e){
            Log.e("EXIST", "EXIST");
        }
        getSupportLoaderManager().restartLoader(CONTACT_LOADER,null,this);
    }

    @Override
    public void editPosition(Contacts contact) {
        // update contact with new data and reload loader
        Log.e("Update",contact.getFirst_name());
        String[] mArray = {contact.getFirst_name()};
        try {
            queryHandler.startQuery(1,null,ContactProvider.Contacts.CONTENT_URI,null,
                    ContactColumns.FIRST_NAME+"=?",mArray,null);
        }catch (SQLiteConstraintException e){
            Log.e("EXIST", "EXIST");
        }
    }
    public void editContactAct(Contacts contact){
        Intent intent = new Intent(this, AddContactActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("contact",contact);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(CONTACT_LOADER,null,this);
    }
}
