package org.jackyonline.idog.db;

import java.util.ArrayList;
import java.util.List;

import org.jackyonline.idog.bean.DogBean;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Handle the event about database
 * 
 * @author Jacky_Chen
 * 
 */
public class DogsDBManager {

	private List<DogBean> mDogs;
	private String mQuery;
	private String mParam;
	private Context mContext;

	private static DogsDBManager dogsDBManager;

	private DogsDBManager(Context context) {
		this.mContext = context;
	}

	/**
	 * 根据狗名获取某条狗的基本信息
	 * 
	 * @param dogName
	 * @return
	 */
	public List<DogBean> getSearchDogs(String dogName) {
		List<DogBean> dogs = new ArrayList<DogBean>();
		
		AssetsDatabaseManager.initManager(mContext);
		AssetsDatabaseManager adManager = AssetsDatabaseManager.getManager();
		SQLiteDatabase db = adManager.getDatabase("dog_info.db");

		Cursor cursor = db.rawQuery(
				"select * from dogs where chinese_name like ?",
				new String[] { "%" + dogName + "%" });
		
		while (cursor.moveToNext()) {
			DogBean dog = new DogBean();
			dog.setNum(cursor.getString(0));
			dog.setEnglish_name(cursor.getString(1));
			dog.setChinese_name(cursor.getString(2));
			dog.setWeight(cursor.getString(3));
			dog.setShoulder_height(cursor.getString(4));
			dog.setBody_type(cursor.getString(5));
			dog.setFunction(cursor.getString(6));
			dog.setFeeding(cursor.getString(7));
			dog.setPrice(cursor.getString(8));
			dog.setCharacteristic(cursor.getString(9));
			dog.setIq(cursor.getString(10));
			dog.setUrl(cursor.getString(11));
			dog.setPic(cursor.getString(12));
			dogs.add(dog);
		}
		cursor.close();
		db.close();
		return dogs;
	}

	public static DogsDBManager newInstance(Context context) {
		if (dogsDBManager == null) {
			dogsDBManager = new DogsDBManager(context.getApplicationContext());
		}

		return dogsDBManager;
	}

	/**
	 * 根据栏目id和栏目名称获取对应的狗狗列表
	 * 
	 * @param mColumnId
	 * @param mColumnName
	 * @return
	 */
	public List<DogBean> getDogs(int mColumnId, String mColumnName) {
		mDogs = new ArrayList<DogBean>();
		switch (mColumnId) {
		case 1:
			mQuery = "select * from dogs";
			mParam = "";
			break;
		case 2:
			if (mColumnName.equals("家庭犬")) {
				mQuery = "select * from dogs where function=?";
				mParam = "home";
			} else {
				mQuery = "select * from dogs where body_type=?";
				mParam = "xsmall";
			}
			break;
		case 3:
			if (mColumnName.equals("玩具犬")) {
				mQuery = "select * from dogs where function=?";
				mParam = "toy";
			} else {
				mQuery = "select * from dogs where body_type=?";
				mParam = "small";
			}
			break;
		case 4:
			if (mColumnName.equals("工作犬")) {
				mQuery = "select * from dogs where function=?";
				mParam = "work";
			} else {
				mQuery = "select * from dogs where body_type=?";
				mParam = "medium";
			}
			break;
		case 5:
			if (mColumnName.equals("梗类犬")) {
				mQuery = "select * from dogs where function=?";
				mParam = "terrier";
			} else {
				mQuery = "select * from dogs where body_type=?";
				mParam = "large";
			}
			break;
		case 6:
			if (mColumnName.equals("牧羊犬")) {
				mQuery = "select * from dogs where function=?";
				mParam = "shepherd";
			} else {
				mQuery = "select * from dogs where body_type=?";
				mParam = "xlarge";
			}
			break;
		case 7:
			if (mColumnName.equals("狩猎犬")) {
				mQuery = "select * from dogs where function=?";
				mParam = "hunting";
			} else {
				mQuery = "select * from dogs";
				mParam = "";
			}
			break;
		case 8:
			if (mColumnName.equals("枪猎犬")) {
				mQuery = "select * from dogs where function=?";
				mParam = "hunting_gun";
			} else {
				mQuery = "select * from dogs";
				mParam = "";
			}
			break;
		default:
			mQuery = "select * from dogs";
			mParam = "";
		}

		AssetsDatabaseManager.initManager(mContext);
		AssetsDatabaseManager adManager = AssetsDatabaseManager.getManager();
		SQLiteDatabase db = adManager.getDatabase("dog_info.db");

		Cursor cursor;
		if (mParam.equals("")) {
			cursor = db.rawQuery(mQuery, null);
		} else {
			cursor = db.rawQuery(mQuery, new String[] { mParam });
		}
		while (cursor.moveToNext()) {
			DogBean dog = new DogBean();
			dog.setNum(cursor.getString(0));
			dog.setEnglish_name(cursor.getString(1));
			dog.setChinese_name(cursor.getString(2));
			dog.setWeight(cursor.getString(3));
			dog.setShoulder_height(cursor.getString(4));
			dog.setBody_type(cursor.getString(5));
			dog.setFunction(cursor.getString(6));
			dog.setFeeding(cursor.getString(7));
			dog.setPrice(cursor.getString(8));
			dog.setCharacteristic(cursor.getString(9));
			dog.setIq(cursor.getString(10));
			dog.setUrl(cursor.getString(11));
			dog.setPic(cursor.getString(12));
			mDogs.add(dog);
		}

		return mDogs;
	}
}
