package org.jackyonline.idog.fragments;

import java.util.ArrayList;

import org.jackyonline.idog.R;
import org.jackyonline.idog.adapter.DogsFragmentPagerAdapter;
import org.jackyonline.idog.bean.ChannelItem;
import org.jackyonline.idog.bean.ChannelLab;
import org.jackyonline.idog.customui.ColumnHorizontalScrollView;
import org.jackyonline.idog.tools.BaseTools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainFragment extends Fragment {

	/** 于ScrollView有关的变量 */
	/** 用户选择的狗分类列表 */
	private ArrayList<ChannelItem> dogsChannelList;
	LinearLayout mRadioGroup_content;
	LinearLayout ll_more_columns;
	RelativeLayout rl_column;
	private ViewPager mViewPager;
	/** 当前选中的栏目 */
	private int columnSelectIndex = 0;
	/** 左阴影部分 */
	public ImageView shade_left;
	/** 右阴影部分 */
	public ImageView shade_right;
	/** 屏幕宽度 */
	private int mScreenWidth = 0;
	/** Item宽度 */
	private int mItemWidth = 0;
	private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
	/** 自定义HorizontalScrollView */
	private ColumnHorizontalScrollView mColumnHorizontalScrollView;

	// channel type
	public static final int CHANNEL_TYPE_fUNTION = 1;
	public static final int CHANNEL_TYPE_BODY = 2;

	/** 注册广播，提醒fragment改变滚动轴数据 */
	private BroadcastReceiver mReceiver;
	/** 删选广播 */
	public static final String ACTION_UPDATE_SCROLLVIEW = "action_update";
	public static final String CATEGORY_TYPE = "category_type";
	/** 默认情况下是按照功能分类 */
	private int current_type = CHANNEL_TYPE_fUNTION;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		dogsChannelList = new ArrayList<ChannelItem>();
		
		mReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				int temp_category = arg1.getIntExtra(CATEGORY_TYPE, -1);
				if (temp_category == -1) {
					Toast.makeText(getActivity(), "操作异常，分类有误",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (temp_category == current_type) {
					Toast.makeText(getActivity(), "栏目不发生变化", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				// 更新当前栏目类型
				current_type = temp_category;
				setChangelView();
				// TODO 逻辑有待检验
				selectTab(0);
			}
		};
		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION_UPDATE_SCROLLVIEW);
		getActivity().registerReceiver(mReceiver, filter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mScreenWidth = BaseTools.getWindowsWidth(getActivity());
		mItemWidth = mScreenWidth / 5; // 一个Item的宽度为屏幕宽度的7分之1

		// 初始化空间
		View view = inflater.inflate(R.layout.fragment_dogs_list, container,
				false);

		mColumnHorizontalScrollView = (ColumnHorizontalScrollView) view
				.findViewById(R.id.mColumnHorizontalScrollView);
		mRadioGroup_content = (LinearLayout) view
				.findViewById(R.id.mRadioGroup_content);
		rl_column = (RelativeLayout) view.findViewById(R.id.rl_column);
		mViewPager = (ViewPager) view.findViewById(R.id.mViewPager);
		shade_left = (ImageView) view.findViewById(R.id.shade_left);
		shade_right = (ImageView) view.findViewById(R.id.shade_right);

		setChangelView();
		return view;
	}

	/**
	 * 当栏目项发生变化时候调用
	 * */
	private void setChangelView() {
		initColumnData();
		initTabColumn();
		initFragment();
	}

	/** 获取Column栏目 数据 */
	private void initColumnData() {
		if (current_type == CHANNEL_TYPE_BODY) {
			dogsChannelList = ChannelLab.newInstance(getActivity())
					.getChannels(CHANNEL_TYPE_BODY);
		} else {
			dogsChannelList = ChannelLab.newInstance(getActivity())
					.getChannels(CHANNEL_TYPE_fUNTION);
		}
	}

	/**
	 * 初始化Column栏目项
	 */
	private void initTabColumn() {
		mRadioGroup_content.removeAllViews();
		int count = dogsChannelList.size();

		mColumnHorizontalScrollView.setParam(getActivity(), mScreenWidth,
				mRadioGroup_content, shade_left, shade_right, ll_more_columns,
				rl_column);

		for (int i = 0; i < count; i++) {
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					mItemWidth, LayoutParams.WRAP_CONTENT);
			params.leftMargin = 5;
			params.rightMargin = 5;
			// TextView localTextView = (TextView)
			// mInflater.inflate(R.layout.column_radio_item, null);
			TextView columnTextView = new TextView(getActivity());
			columnTextView.setTextAppearance(getActivity(),
					R.style.top_category_scroll_view_item_text);
			// localTextView.setBackground(getResources().getDrawable(R.drawable.top_category_scroll_text_view_bg));
			columnTextView.setBackgroundResource(R.drawable.radio_buttong_bg);
			columnTextView.setGravity(Gravity.CENTER);
			columnTextView.setPadding(6, 5, 6, 5);
			columnTextView.setId(i);
			columnTextView.setText(dogsChannelList.get(i).getName());
			columnTextView.setTextColor(getResources().getColorStateList(
					R.color.top_category_scroll_text_color_day));
			if (columnSelectIndex == i) {
				columnTextView.setSelected(true);
			}
			columnTextView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
						View localView = mRadioGroup_content.getChildAt(i);
						if (localView != v)
							localView.setSelected(false);
						else {
							localView.setSelected(true);
							mViewPager.setCurrentItem(i);
						}
					}
				}
			});
			mRadioGroup_content.addView(columnTextView, i, params);
		}
	}

	/**
	 * 初始化Fragment
	 */
	private void initFragment() {
		fragments.clear();// 清空
		int count = dogsChannelList.size();

		for (int i = 0; i < count; i++) {
			Bundle data = new Bundle();
			data.putString("text", dogsChannelList.get(i).getName());
			data.putInt("id", dogsChannelList.get(i).getId());
			DogsListFragment dogsfragment = new DogsListFragment();
			dogsfragment.setArguments(data);
			fragments.add(dogsfragment);
		}
		DogsFragmentPagerAdapter adapter = new DogsFragmentPagerAdapter(
				getFragmentManager(), fragments);
		mViewPager.setAdapter(adapter);
		mViewPager.setOffscreenPageLimit(3);
		mViewPager.setOnPageChangeListener(pageListener);
	}

	/**
	 * ViewPager切换监听方法
	 * */
	public OnPageChangeListener pageListener = new OnPageChangeListener() {

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			mViewPager.setCurrentItem(position);
			selectTab(position);
		}
	};

	/**
	 * 选择的Column里面的Tab
	 * */
	private void selectTab(int tab_postion) {
		columnSelectIndex = tab_postion;
		for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
			View checkView = mRadioGroup_content.getChildAt(tab_postion);
			int k = checkView.getMeasuredWidth();
			int l = checkView.getLeft();
			int i2 = l + k / 2 - mScreenWidth / 2;
			// rg_nav_content.getParent()).smoothScrollTo(i2, 0);
			mColumnHorizontalScrollView.smoothScrollTo(i2, 0);
			// mColumnHorizontalScrollView.smoothScrollTo((position - 2) *
			// mItemWidth , 0);
		}
		// 判断是否选中
		for (int j = 0; j < mRadioGroup_content.getChildCount(); j++) {
			View checkView = mRadioGroup_content.getChildAt(j);
			boolean ischeck;
			if (j == tab_postion) {
				ischeck = true;
			} else {
				ischeck = false;
			}
			checkView.setSelected(ischeck);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// 注销广播
		getActivity().unregisterReceiver(mReceiver);
	}
}