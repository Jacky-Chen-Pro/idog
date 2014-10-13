package org.jackyonline.idog.bean;

import java.util.ArrayList;

import org.jackyonline.idog.fragments.MainFragment;

import android.content.Context;

public class ChannelLab {

	private static ChannelLab sChannelLab;
	private Context mAppContext;

	private ArrayList<ChannelItem> mChannelItems;

	private ChannelLab(Context c) {
		this.mAppContext = c;
		mChannelItems = new ArrayList<ChannelItem>();
	}

	public static ChannelLab newInstance(Context context) {
		if (sChannelLab == null) {
			sChannelLab = new ChannelLab(context);
		}
		sChannelLab = new ChannelLab(context);
		return sChannelLab;
	}

	public ArrayList<ChannelItem> getChannels(int type) {
		if (mChannelItems != null)
			mChannelItems.clear();
		if (type == MainFragment.CHANNEL_TYPE_BODY) {
			mChannelItems.add(new ChannelItem(1, "ȫ��", 1, 1));
			mChannelItems.add(new ChannelItem(2, "��С��", 2, 1));
			mChannelItems.add(new ChannelItem(3, "С��Ȯ", 3, 1));
			mChannelItems.add(new ChannelItem(4, "����Ȯ", 4, 1));
			mChannelItems.add(new ChannelItem(5, "����Ȯ", 5, 1));
			mChannelItems.add(new ChannelItem(6, "������", 6, 1));
		} else if (type == MainFragment.CHANNEL_TYPE_fUNTION) {
			mChannelItems.add(new ChannelItem(1, "ȫ��", 1, 1));
			mChannelItems.add(new ChannelItem(2, "��ͥȮ", 2, 1));
			mChannelItems.add(new ChannelItem(3, "���Ȯ", 3, 1));
			mChannelItems.add(new ChannelItem(4, "����Ȯ", 4, 1));
			mChannelItems.add(new ChannelItem(5, "����Ȯ", 5, 1));
			mChannelItems.add(new ChannelItem(6, "����Ȯ", 6, 1));
			mChannelItems.add(new ChannelItem(7, "����Ȯ", 7, 1));
			mChannelItems.add(new ChannelItem(8, "ǹ��Ȯ", 8, 1));
		}
		return mChannelItems;
	}

	public ChannelItem getChannel(int id) {
		for (ChannelItem item : mChannelItems) {
			if (item.getId() == id) {
				return item;
			}
		}
		return null;
	}
}
