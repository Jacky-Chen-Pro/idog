package org.jackyonline.idog;

import org.jackyonline.idog.base.Constants;
import org.jackyonline.idog.db.AssetsDatabaseManager;
import org.jackyonline.idog.fragments.AboutFragment;
import org.jackyonline.idog.fragments.DogsListFragment;
import org.jackyonline.idog.fragments.MainFragment;

import com.umeng.analytics.MobclickAgent;
import com.wandoujia.ads.sdk.Ads;
import com.wandoujia.ads.sdk.loader.Fetcher;

import android.app.SearchManager;
import android.app.SearchManager.OnDismissListener;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnCloseListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	/** ActionBar的开关条 */
	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerLayout mDrawerLayout;

	/** menu菜单中默认的起始设置 */
	protected boolean category_body_type = false;
	protected boolean category_function = true;
	private int current_category_type = MainFragment.CHANNEL_TYPE_fUNTION;

	public static final String TAG = "MainActivity";

	private RelativeLayout mRl_baike;
	private RelativeLayout mRl_loading;
	private RelativeLayout mRl_advertisement;

	private Fragment mCurrentFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		MainFragment fragmentDogs = new MainFragment();
		FragmentManager fragmentManager = getSupportFragmentManager();

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = new ActionBarDrawerToggle(MainActivity.this,
				mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				getActionBar().setTitle("iDog");
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getActionBar().setTitle("Menu");
			}

		};
		mDrawerToggle.setDrawerIndicatorEnabled(true);
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(R.id.content_frame, fragmentDogs);
		transaction.commit();

		mCurrentFragment = fragmentDogs;

		mRl_baike = (RelativeLayout) findViewById(R.id.rl_baike);
		mRl_baike.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!(mCurrentFragment instanceof MainFragment)) {
					FragmentManager fragmentManager = getSupportFragmentManager();
					fragmentManager.popBackStack();
					mCurrentFragment = new MainFragment();
				}
				mDrawerLayout.closeDrawers();
				invalidateOptionsMenu();
			}
		});

		mRl_loading = (RelativeLayout) findViewById(R.id.rl_loading);
		mRl_loading.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Toast.makeText(getApplicationContext(), "正在思考，如果你有什么好点子，联系我吧！",
						Toast.LENGTH_SHORT).show();
			}
		});

		mRl_advertisement = (RelativeLayout) findViewById(R.id.rl_advertisement);

		mRl_advertisement.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (Ads.isLoaded(Fetcher.AdFormat.appwall,
						Constants.ADVERTISEMENT_ID)) {
					Ads.showAppWall(getApplicationContext(),
							Constants.ADVERTISEMENT_ID);
				}
			}
		});

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		menu.findItem(R.id.item_function).setChecked(true);

		MenuItem searchItem = menu.findItem(R.id.item_search);
		MenuItem aboutItem = menu.findItem(R.id.item_about_app);
		MenuItem bodyTypeItem = menu.findItem(R.id.item_body_type);
		MenuItem functionTypeItem = menu.findItem(R.id.item_function);

		if (mCurrentFragment instanceof MainFragment) {
			searchItem.setVisible(true);
			aboutItem.setVisible(true);
			bodyTypeItem.setVisible(true);
			functionTypeItem.setVisible(true);
		}

		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		final SearchView searchView = (SearchView) MenuItemCompat
				.getActionView(searchItem);
		// 下面这句话要注意找了很久~~晕死，原来是设置组件的启动SearchableActivity是哪一个
		// 如果用的是getComponent()得到的就是当前这个组件了。
		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(new ComponentName(getApplicationContext(),
						DogSearchActivity.class)));
		// searchView.setIconifiedByDefault(true);
		searchView.setIconified(true);
		searchView.setFocusable(false);
		searchView.setOnSearchClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			}
		});
		searchView.setOnCloseListener(new OnCloseListener() {

			@Override
			public boolean onClose() {
				return true;
			}
		});
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// 对菜单上的点击进行控制
		int id = item.getItemId();
		if (id == R.id.item_body_type) {
			category_body_type = !category_body_type;
			item.setChecked(category_body_type);
			current_category_type = MainFragment.CHANNEL_TYPE_BODY;

			// 发送广播提醒界面滚动栏更新
			Intent update = new Intent();
			update.putExtra(MainFragment.CATEGORY_TYPE, current_category_type);
			update.setAction(MainFragment.ACTION_UPDATE_SCROLLVIEW);
			sendBroadcast(update);
		} else if (id == R.id.item_function) {
			category_function = !category_function;
			item.setChecked(category_function);
			current_category_type = MainFragment.CHANNEL_TYPE_fUNTION;

			// 发送广播提醒界面滚动栏更新
			Intent update = new Intent();
			update.putExtra(MainFragment.CATEGORY_TYPE, current_category_type);
			update.setAction(MainFragment.ACTION_UPDATE_SCROLLVIEW);
			sendBroadcast(update);
		} else if (id == R.id.item_about_app) {
			if (mCurrentFragment instanceof MainFragment) {
				// 打开AboutFragment介绍应用和作者
				FragmentManager fragmentManager = getSupportFragmentManager();
				AboutFragment aboutFragment = new AboutFragment();
				FragmentTransaction transaction = fragmentManager
						.beginTransaction();
				transaction.add(R.id.content_frame, aboutFragment);
				transaction.addToBackStack(null);
				transaction.commit();
				mCurrentFragment = aboutFragment;

				invalidateOptionsMenu();
			}
		}

		return true;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

		if (mCurrentFragment instanceof AboutFragment) {
			mCurrentFragment = new MainFragment();
			invalidateOptionsMenu();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		AssetsDatabaseManager.getManager().closeAllDatabase();
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
