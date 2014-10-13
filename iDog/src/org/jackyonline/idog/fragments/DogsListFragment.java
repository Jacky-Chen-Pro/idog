package org.jackyonline.idog.fragments;

import java.util.ArrayList;
import java.util.List;

import org.jackyonline.idog.DogWebViewActivity;
import org.jackyonline.idog.R;
import org.jackyonline.idog.adapter.DogsAdapter;
import org.jackyonline.idog.base.Constants;
import org.jackyonline.idog.bean.DogBean;
import org.jackyonline.idog.db.AssetsDatabaseManager;
import org.jackyonline.idog.db.DogsDBManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class DogsListFragment extends Fragment {

	/** ��Ŀ���� */
	private String mColumnName;
	/** ��ĿID */
	private int mColumnId;

	/** ��Dog�����Ϣ */
	private List<DogBean> mDogs;
	private ListView mLv_dogs;
	private int mDogs_id[];

	public static final String TAG = "DogsFragment";

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle args = getArguments();
		mColumnName = args != null ? args.getString("text") : "";
		mColumnId = args != null ? args.getInt("id", 0) : 0;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.list_dogs_fragment, null);

		mDogs = new ArrayList<DogBean>();
		mLv_dogs = (ListView) view.findViewById(R.id.lv_dogs);

		// Handle about the database
		mDogs = DogsDBManager.newInstance(getActivity()).getDogs(mColumnId,
				mColumnName);

		setDogsImageId();
		DogsAdapter adapter = new DogsAdapter(inflater, mDogs, mDogs_id);
		mLv_dogs.setAdapter(adapter);

		mLv_dogs.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {

				Intent intent = new Intent(getActivity(),
						DogWebViewActivity.class);
				intent.putExtra("address", mDogs.get(position).getUrl());
				startActivity(intent);
			}
		});

		return view;
	}

	private void setDogsImageId() {
		if (mColumnName != null) {
			if (mColumnName.equals("ȫ��")) {
				mDogs_id = Constants.all_dogs;
			} else if (mColumnName.equals("��С��")) {
				mDogs_id = Constants.xsmall;
			} else if (mColumnName.equals("С��Ȯ")) {
				mDogs_id = Constants.small;
			} else if (mColumnName.equals("����Ȯ")) {
				mDogs_id = Constants.medium;
			} else if (mColumnName.equals("����Ȯ")) {
				mDogs_id = Constants.large;
			} else if (mColumnName.equals("������")) {
				mDogs_id = Constants.xlarge;
			} else if (mColumnName.equals("��ͥȮ")) {
				mDogs_id = Constants.home;
			} else if (mColumnName.equals("���Ȯ")) {
				mDogs_id = Constants.toy;
			} else if (mColumnName.equals("����Ȯ")) {
				mDogs_id = Constants.work;
			} else if (mColumnName.equals("����Ȯ")) {
				mDogs_id = Constants.terrier;
			} else if (mColumnName.equals("����Ȯ")) {
				mDogs_id = Constants.shepherd;
			} else if (mColumnName.equals("����Ȯ")) {
				mDogs_id = Constants.hunting;
			} else if (mColumnName.equals("ǹ��Ȯ")) {
				mDogs_id = Constants.hunting_gun;
			}
		}
	}
	
}
