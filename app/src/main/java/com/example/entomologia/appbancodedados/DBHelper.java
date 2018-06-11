package com.example.entomologia.appbancodedados;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

public class DBHelper {

    //nome do banco de dados
    private static final String DATABASE_NAME = "bancodedados.db";
    //versão do banco de dados
    private static final int DATABASE_VERSION = 1;
    //nome da tabela no banco de dados
    private static final String TABLE_NAME = "contato";

    private Context context;
    private SQLiteDatabase db;

    private SQLiteStatement insertStmt;
    //insere os dados na tabela (espaços depois de into e antes de ()
    private static final String INSERT = "insert into " + TABLE_NAME + " (nome, endereco, empresa) values (?, ?, ?)";

    //construtor do DBHelper
    public DBHelper (Context context) {
        this.context = context;
        OpenHelper openHelper = new OpenHelper(this.context);
        this.db = openHelper.getWritableDatabase();
        this.insertStmt = this.db.compileStatement(INSERT);

    }

    //insere os dados no banco
    public long insert (String nome, String endereco, String empresa){
        this.insertStmt.bindString(1, nome);
        this.insertStmt.bindString(2, endereco);
        this.insertStmt.bindString(3, empresa);
        return  this.insertStmt.executeInsert();
    }

    //apaga os registros
    public void deleteAll(){
        this.db.delete(TABLE_NAME, null, null);
    }

    //obtem as informações do banco de dados
    public List<Contato> queryGetAll(){
       List<Contato> list = new ArrayList<Contato>();
        //trata as exceções
        try{
            Cursor cursor = this.db.query(TABLE_NAME, new String[] {"nome", "endereco", "empresa"},
            null, null, null, null, null, null);

            int nregistros = cursor.getCount();
            if (nregistros != 0){
                cursor.moveToFirst();
                //retorna os dados enquanto não for nulo
                do {
                    Contato contato = new Contato(cursor.getString(0), cursor.getString(1), cursor.getString(2));
                    list.add(contato);
                } while (cursor.moveToNext());

                //se o cursor for diferente de null e não está fechado, fecha o cursor
                if (cursor != null && ! cursor.isClosed())
                    cursor.close();
                return list;
            } else
                return null;
       } catch (Exception err){
            return null;
        }
    }

    private static class OpenHelper extends SQLiteOpenHelper{
        OpenHelper(Context context){
            super (context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        //cria de fato o banco de dados
        public void onCreate(SQLiteDatabase db){
            String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, " +
                    "endereco TEXT, empresa TEXT);";
            db.execSQL(sql);
        }

        public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
