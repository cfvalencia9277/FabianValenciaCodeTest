package com.testcode.fabianvalenciacodetest;

import android.content.ContentProviderOperation;
import android.content.OperationApplicationException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.testcode.fabianvalenciacodetest.data.ContactColumns;
import com.testcode.fabianvalenciacodetest.data.ContactProvider;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by Fabian on 27/10/2016.
 */

public class AddContactActivity extends AppCompatActivity {


    @BindView(R.id.first_name_et)
    EditText firstNameEt;
    @BindView(R.id.last_name_et)
    EditText lastNameEt;
    @BindView(R.id.phone_num_et)
    EditText phoneNumEt;
    @BindView(R.id.birth_date_et)
    EditText birthDateEt;
    @BindView(R.id.zip_code_et)
    EditText zipCodeEt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcontanactivity);
        ButterKnife.bind(this);
        Stetho.initializeWithDefaults(this);
    }
    @OnClick(R.id.cancel_btn)
     void cancel(){
        clearviews();
        this.finish();
    }
    @OnClick(R.id.save_btn)
     void save(){
        Log.e("CLICKED","SAVE");
        savecontac();
    }
    public void clearviews(){
        firstNameEt.setText("");
        lastNameEt.setText("");
        phoneNumEt.setText("");
        birthDateEt.setText("");
        zipCodeEt.setText("");
    }

    public void savecontac(){
        String fn = firstNameEt.getText().toString();
        if(fn.isEmpty()){
            Toast.makeText(this, "Missing first name",
                    Toast.LENGTH_LONG).show();
            return;
        }
        String ln = lastNameEt.getText().toString();
        if(ln.isEmpty()){
            Toast.makeText(this, "Missing last name",
                    Toast.LENGTH_LONG).show();
            return;
        }
        String pn = phoneNumEt.getText().toString();
        if(pn.isEmpty()){
            Toast.makeText(this, "Missing phone num",
                    Toast.LENGTH_LONG).show();
            return;
        }
        String bd = birthDateEt.getText().toString();
        if(bd.isEmpty()){
            Toast.makeText(this, "Missing birth date",
                    Toast.LENGTH_LONG).show();
            return;
        }
        String zc = zipCodeEt.getText().toString();
        if(zc.isEmpty()){
            Toast.makeText(this, "Missing Zip code",
                    Toast.LENGTH_LONG).show();
            return;
        }
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>(1);
        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(ContactProvider.Contacts.CONTENT_URI);
        builder.withValue(ContactColumns.FIRST_NAME,fn);
        builder.withValue(ContactColumns.LAST_NAME, ln);
        builder.withValue(ContactColumns.PHONE_NUMER, pn);
        builder.withValue(ContactColumns.BIRTH_DATE, bd);
        builder.withValue(ContactColumns.ZIP_CODE, zc);
        batchOperations.add(builder.build());
        try{
            getContentResolver().applyBatch(ContactProvider.AUTHORITY, batchOperations);
            clearviews();
            this.finish();
        }
        catch (SQLiteConstraintException e){

        }
        catch (SQLiteException e){
            Log.e("SQLite", "Error ");
        }
        catch(RemoteException | OperationApplicationException e){
            Log.e("DATA", "Error applying batch insert");
        }
        catch (Exception e){
            Log.e("EXCEPTION", "GENERAL");
        }
    }
}
