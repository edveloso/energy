package com.example.clienteenergycalc;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "energy_calc";

	// Contacts table name
	private static final String TABELA_APARELHOS = "aparelhos";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NOME = "nome";
	private static final String KEY_CONSUMO = "consumo";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CRIA_TABELA_APARELHOS = "CREATE TABLE " + TABELA_APARELHOS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NOME
				+ " TEXT," + KEY_CONSUMO + " DOUBLE" + ")";
		db.execSQL(CRIA_TABELA_APARELHOS);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABELA_APARELHOS);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new contact
	void addAparelho(Aparelho aparelho) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NOME, aparelho.getNome());
		values.put(KEY_CONSUMO, aparelho.getConsumo());

		// Inserting Row
		db.insert(TABELA_APARELHOS, null, values);
		db.close(); // Closing database connection
	}

	// Getting single contact
	Aparelho getAparelho(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABELA_APARELHOS, new String[] { KEY_ID,
				KEY_NOME, KEY_CONSUMO }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Aparelho aparelho = new Aparelho(cursor.getString(1),
				Double.parseDouble(cursor.getString(2)));
		// return aparelho
		return aparelho;
	}

	// Getting APARELHOS
	public List<Aparelho> getAllAparelhos() {
		List<Aparelho> aparelhoList = new ArrayList<Aparelho>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABELA_APARELHOS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Aparelho aparelho = new Aparelho();
				aparelho.setID(Integer.parseInt(cursor.getString(0)));
				aparelho.setNome(cursor.getString(1));
				aparelho.setConsumo(cursor.getDouble(2));
				// Adding contact to list
				aparelhoList.add(aparelho);
			} while (cursor.moveToNext());
		}

		// return lista aparelhos
		return aparelhoList;

	}

	// atualizar aparelho
	public int updateAparelho(Aparelho aparelho) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NOME, aparelho.getNome());
		values.put(KEY_CONSUMO, aparelho.getConsumo());

		// updating row
		return db.update(TABELA_APARELHOS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(aparelho.getID()) });
	}

	// apagando aparelho
	public void apagaAparelho(Aparelho aparelho) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABELA_APARELHOS, KEY_ID + " = ?",
				new String[] { String.valueOf(aparelho.getID()) });
		db.close();
	}

	// Getting contacts Count
	public int getTotalAparelho() {
		String countQuery = "SELECT  * FROM " + TABELA_APARELHOS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	// PEGANDO TODOS OS APARELHOS DO BANCO PARA ADICIONAR NO SPINNER DA TELA
	// INICIAL
	public List<Aparelho> getNomeAparelhos() {
		List<Aparelho> itens = new ArrayList<Aparelho>();
		// Select All Query
		String selectQuery = "SELECT ID,NOME, avg(consumo) as CONSUMO FROM "
				+ TABELA_APARELHOS + " GROUP BY NOME";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				itens.add(new Aparelho(cursor.getString(1), cursor.getDouble(2)));
			} while (cursor.moveToNext());
		}

		// closing connection
		cursor.close();
		db.close();

		// returning labels
		return itens;
	}

}
