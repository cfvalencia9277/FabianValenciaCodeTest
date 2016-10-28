package com.testcode.fabianvalenciacodetest.data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by Fabian on 10/27/16.
 */
@ContentProvider(authority = ContactProvider.AUTHORITY,database = ContactsDatabase.class)
public class ContactProvider {


    public static final String AUTHORITY = "com.testcode.fabianvalenciacodetest";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://"+AUTHORITY);

    interface Path{
        String CONTACTS = "Contacts";

    }

    private static Uri buildUri(String ... paths){
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for(String path: paths){
            builder.appendPath(path);
        }
        return builder.build();
    }

    @TableEndpoint(table = ContactsDatabase.MOVIES)public static class Contacts{
        @ContentUri(
                path = Path.CONTACTS,
                type = "vnd.android.cursor,dir/Contacts",
                defaultSort = ContactColumns.FIRST_NAME)
        public static final Uri CONTENT_URI = buildUri(Path.CONTACTS);
    }
    @InexactContentUri(
            name = "CONTACT_ID",
            path = Path.CONTACTS+"/#",
            type = "vnd.android.cursor,dir/Contacts",
            whereColumn = ContactColumns._ID,
            pathSegment = 1)
    public static Uri ContactWithId(long id){
        return buildUri(Path.CONTACTS, String.valueOf(id));
    }
}
