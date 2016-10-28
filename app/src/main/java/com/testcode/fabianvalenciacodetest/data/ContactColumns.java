package com.testcode.fabianvalenciacodetest.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.Unique;

/**
 * Created by Fabian on 10/27/16.
 */

public class ContactColumns {

    @DataType(DataType.Type.INTEGER)@PrimaryKey @AutoIncrement
    public static final String _ID = "_id";
    @DataType(DataType.Type.TEXT)@NotNull
    public static final String FIRST_NAME = "First_Name";
    @DataType(DataType.Type.TEXT)@NotNull
    public static final String LAST_NAME = "Last_Name";
    @DataType(DataType.Type.TEXT)@NotNull
    public static final String PHONE_NUMER = "Phone_Number";
    @DataType(DataType.Type.TEXT)@NotNull
    public static final String BIRTH_DATE = "Birth_Date";
    @DataType(DataType.Type.TEXT)@NotNull
    public static final String ZIP_CODE = "Zip_Code";
}
