package org.jackyonline.idog.provider;

import org.jackyonline.idog.db.AssetsDatabaseManager;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class DogsInfoProvider extends ContentProvider {
	@SuppressWarnings("unused")
	public static final class DogTable {
		private static final String ENGLISH_NAME = "english_name";
		private static final String CHINESE_NAME = "chinese_name";
		private static final String WEIGHT = "weight";
		private static final String SHOULDER_HEIGHT = "shoulder_height";
		private static final String BODY_TYPE = "body_type";
		private static final String FUNCTION = "function";
		private static final String FEEDING = "feeding";
		private static final String PRICE = "price";
		private static final String CHARACTERISTIC = "characteristic";
		private static final String IQ_RANK = "iq_rank";
		private static final String URL = "url";
		private static final String PIC = "pic";
	}

	public static final String mAuthority = "org.jackyonline.idog";

	private static final UriMatcher sUriMatcher;
	private static final String TAG = "DogsInfoProvider";

	static {
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(mAuthority, "dogs", 1);
	}

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		return 0;
	}

	@Override
	public String getType(Uri arg0) {
		return null;
	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1) {
		return null;
	}

	@Override
	public boolean onCreate() {

		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		AssetsDatabaseManager.initManager(getContext());
		AssetsDatabaseManager adManager = AssetsDatabaseManager.getManager();
		SQLiteDatabase db = adManager.getDatabase("dog_info.db");

		switch (sUriMatcher.match(uri)) {
		case 1:

			break;

		default:
			break;
		}

		Cursor cursor = db.query("dogs", projection, selection, selectionArgs,
				null, null, null, null);
		db.close();
		Log.i(TAG, "------------正在进行搜索-------------");
		return cursor;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		return 0;
	}

}
