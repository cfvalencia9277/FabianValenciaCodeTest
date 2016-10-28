package com.testcode.fabianvalenciacodetest.data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by Fabian on 10/27/16.
 */
@Database(version = ContactsDatabase.VERSION)
public class ContactsDatabase {
    private ContactsDatabase(){}
    public  static final int VERSION = 2;
    @Table(ContactColumns.class) public static final String MOVIES = "Contacts";
}
