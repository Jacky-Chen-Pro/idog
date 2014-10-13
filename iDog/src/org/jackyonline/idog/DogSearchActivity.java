package org.jackyonline.idog;

import java.util.ArrayList;
import java.util.List;

import org.jackyonline.idog.adapter.DogsAdapter;
import org.jackyonline.idog.base.Constants;
import org.jackyonline.idog.bean.DogBean;
import org.jackyonline.idog.db.AssetsDatabaseManager;
import org.jackyonline.idog.db.DogsDBManager;

import com.umeng.analytics.MobclickAgent;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class DogSearchActivity extends ActionBarActivity {
	private List<DogBean> mDogs;
	private List<Integer> mDogs_id;
	private static final String TAG = "DogSearchActivity";
	private String mQuery;

	private TextView mTv_search_result;
	private ListView mLv_dogs; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_dog);

		mTv_search_result = (TextView) findViewById(R.id.tv_search_result);
		mLv_dogs = (ListView) findViewById(R.id.lv_dogs);

		Intent intent = getIntent();

		if (intent.getAction().equals(Intent.ACTION_SEARCH)) {
			mQuery = intent.getStringExtra(SearchManager.QUERY);

			DogsDBManager manager = DogsDBManager
					.newInstance(getApplicationContext());
			mDogs = manager.getSearchDogs(mQuery);
		}

		if (mDogs.size() == 0) {
			mTv_search_result.setVisibility(View.VISIBLE);
			mTv_search_result.setText("抱歉，该狗还未问世！^_^");
		} else {
			mLv_dogs.setVisibility(View.VISIBLE);
			mDogs_id = new ArrayList<Integer>();
			for (DogBean bean : mDogs) {
				int num = Integer.parseInt(bean.getNum());
				// num是从1开始的，而数组是从0开始的，注意－1；
				mDogs_id.add(Constants.all_dogs[--num]);
				System.out.println("all_dogs：" + Constants.all_dogs[num]);
			}

			int[] ids = new int[mDogs_id.size()];
			for (int i = 0; i < mDogs_id.size(); i++) {
				ids[i] = mDogs_id.get(i).intValue();
				System.out.println("ids[" + i + "]" + ids[i]);
			}

			DogsAdapter adapter = new DogsAdapter(getLayoutInflater(), mDogs,
					ids);
			mLv_dogs.setAdapter(adapter);

			mLv_dogs.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long id) {

					Intent intent = new Intent(getApplicationContext(),
							DogWebViewActivity.class);
					intent.putExtra("address", mDogs.get(position).getUrl());
					startActivity(intent);
				}
			});
		}

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			if (NavUtils.getParentActivityIntent(this) != null) {
				getActionBar().setDisplayHomeAsUpEnabled(true);
			}
			getActionBar().setTitle("狗狗搜索:" + mQuery + "...");
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		AssetsDatabaseManager.getManager().closeAllDatabase();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			if (NavUtils.getParentActivityIntent(this) != null) {
				NavUtils.navigateUpFromSameTask(DogSearchActivity.this);
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

}
