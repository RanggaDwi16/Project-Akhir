package com.example.tugasakhiruas;

public class Constants {

    // Items terkait database
    public static final String DB_NAME = "PERSON_INFO_DB";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "PERSON_INFO_TABLE";

    public static final String C_ID = "ID";
    public static final String C_IMAGE = "IMAGE";
    public static final String C_NAMA = "NAMA";
    public static final String C_TANGGAL = "TANGGAL";
    public static final String C_ADD_TIMESTAMP = "ADD_TIMESTAMP";
    public static final String C_UPDATE_TIMESTAMP = "UPDATE_TIMESTAMP";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + C_IMAGE + " TEXT,"
            + C_NAMA + " TEXT,"
            + C_TANGGAL + " DATE,"
            + C_ADD_TIMESTAMP + " TEXT,"
            + C_UPDATE_TIMESTAMP + " TEXT"
            + ");";

    }

