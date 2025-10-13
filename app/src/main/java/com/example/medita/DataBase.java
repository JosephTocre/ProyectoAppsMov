package com.example.medita;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {

    // Constructor
    public DataBase(Context context, String name, int version) {
        super(context, "dbMedita", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tusuario(idusu INTEGER PRIMARY KEY AUTOINCREMENT, apellidos TEXT, usuario TEXT, clave TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tusuario");
        onCreate(db);
    }

    // Registrar nuevo usuario
    public boolean RegistrarUsuario(String apellidos, String usuario, String clave) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contvalue = new ContentValues();
        contvalue.put("apellidos", apellidos);
        contvalue.put("usuario", usuario);
        contvalue.put("clave", clave);
        long vInsert = db.insert("tusuario", null, contvalue);
        return vInsert != -1;
    }

    // Verificar si el usuario ya existe
    public boolean VerifcarUsuarioIngresado(String usuario) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tusuario WHERE usuario=?", new String[]{usuario});
        boolean disponible = cursor.getCount() == 0;
        cursor.close();
        return disponible;
    }

    // Verificar acceso (login)
    public boolean Verificar_Acceso(String usuario, String clave) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tusuario WHERE usuario=? AND clave=?", new String[]{usuario, clave});
        boolean acceso = cursor.getCount() > 0;
        cursor.close();
        return acceso;
    }
}
