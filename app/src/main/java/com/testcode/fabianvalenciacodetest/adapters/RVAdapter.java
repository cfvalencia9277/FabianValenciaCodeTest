package com.testcode.fabianvalenciacodetest.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.testcode.fabianvalenciacodetest.R;
import com.testcode.fabianvalenciacodetest.data.ContactColumns;
import com.testcode.fabianvalenciacodetest.models.Contacts;
import com.testcode.fabianvalenciacodetest.utils.RecyclerViewCursorAdapter;

import atownsend.swipeopenhelper.BaseSwipeOpenViewHolder;

/**
 * Created by Fabian on 10/27/16.
 */

public class RVAdapter  extends RecyclerViewCursorAdapter<RecyclerView.ViewHolder> {

    private static final String TAG = RVAdapter.class.getSimpleName();
    private final Context mContext;
    private final RVAdapter.ButtonCallbacks callbacks;

    public RVAdapter(Context context, RVAdapter.ButtonCallbacks callbacks) {
        super(null);
        this.mContext =  context;
        this.callbacks = callbacks;
    }
    public  interface ButtonCallbacks {
        void removePosition(Contacts contact);
        void editPosition(Contacts contact);
    }
    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RVAdapter.TestViewHolder(
                    LayoutInflater.from(mContext).inflate(R.layout.viewholderview, parent, false), callbacks);
    }
    @Override
    protected void onBindViewHolder(RecyclerView.ViewHolder holder, Cursor cursor) {
        int fnIndex = cursor.getColumnIndex(ContactColumns.FIRST_NAME);
        String fn = cursor.getString(fnIndex);
        int lnIndex = cursor.getColumnIndex(ContactColumns.LAST_NAME);
        String ln = cursor.getString(lnIndex);
        int pnIndex = cursor.getColumnIndex(ContactColumns.PHONE_NUMER);
        String pn = cursor.getString(pnIndex);
        int bdIndex = cursor.getColumnIndex(ContactColumns.BIRTH_DATE);
        String bd = cursor.getString(bdIndex);
        int zcIndex = cursor.getColumnIndex(ContactColumns.ZIP_CODE);
        String zc = cursor.getString(zcIndex);
        ((TestViewHolder) holder).textView.setText(fn);
        ((TestViewHolder) holder).lastname.setText(ln);
        ((TestViewHolder) holder).phonenum.setText(pn);
        ((TestViewHolder) holder).birthdate.setText(bd);
        ((TestViewHolder) holder).zipcode.setText(zc);
        final Contacts contact = new Contacts().Contact(1,fn,ln,pn,bd,zc);
        ((TestViewHolder) holder).deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbacks.removePosition(contact);
            }
        });
        ((TestViewHolder) holder).editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbacks.editPosition(contact);
            }
        });
    }
    static class TestViewHolder extends BaseSwipeOpenViewHolder {

        public LinearLayout contentView;
        public TextView textView;
        public TextView lastname;
        public TextView phonenum;
        public TextView birthdate;
        public TextView zipcode;
        public TextView deleteButton;
        public TextView editButton;

        public TestViewHolder(final View view, final ButtonCallbacks callbacks) {
            super(view);
            contentView = (LinearLayout) view.findViewById(R.id.content_view);
            textView = (TextView) view.findViewById(R.id.display_text);
            lastname = (TextView) view.findViewById(R.id.last_name_tv);
            phonenum = (TextView) view.findViewById(R.id.phone_tv);
            birthdate = (TextView) view.findViewById(R.id.birthday_tv);
            zipcode = (TextView) view.findViewById(R.id.zip_code_tv);
            deleteButton = (TextView) view.findViewById(R.id.delete_button);
            editButton = (TextView) view.findViewById(R.id.edit_button);
        }
        @NonNull
        @Override public View getSwipeView() {
            return contentView;
        }

        @Override public float getEndHiddenViewSize() {
            return editButton.getMeasuredWidth();
        }

        @Override public float getStartHiddenViewSize() {
            return deleteButton.getMeasuredWidth();
        }

        @Override public void notifyStartOpen() {
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(),R.color.red));
        }
        @Override public void notifyEndOpen() {
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(),R.color.colorPrimaryDark));
        }
    }
}
