package org.jackyonline.idog.adapter;

import java.util.List;

import org.jackyonline.idog.R;
import org.jackyonline.idog.bean.DogBean;
import org.jackyonline.idog.tools.StringUtils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DogsAdapter extends BaseAdapter{
	
	private LayoutInflater inflater;
	private List<DogBean> dogs;
	private int[] dogs_id;
	
	public DogsAdapter(LayoutInflater inflater, List<DogBean> dogs, int[] dogs_id){
		this.inflater = inflater;
		this.dogs = dogs;
		this.dogs_id = dogs_id;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dogs.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder mHolder;
		View view = convertView;
		if (view == null) {
			view = inflater.inflate(R.layout.item_dog, null);
			mHolder = new ViewHolder();
			mHolder.tv_num = (TextView) view.findViewById(R.id.tv_num);
			mHolder.tv_english_name = (TextView) view
					.findViewById(R.id.tv_english_name);
			mHolder.tv_chinese_name = (TextView) view
					.findViewById(R.id.tv_chinese_name);
			mHolder.tv_weight = (TextView) view
					.findViewById(R.id.tv_weight);
			mHolder.tv_shoulder_height = (TextView) view
					.findViewById(R.id.tv_shoulder_height);
			mHolder.tv_body_type = (TextView) view
					.findViewById(R.id.tv_body_type);
			mHolder.tv_function = (TextView) view
					.findViewById(R.id.tv_function);
			mHolder.tv_price = (TextView) view.findViewById(R.id.tv_price);
			mHolder.tv_characteristic = (TextView) view
					.findViewById(R.id.tv_characteristic);
			mHolder.tv_iq = (TextView) view.findViewById(R.id.tv_iq);
			mHolder.iv_dog = (ImageView) view.findViewById(R.id.iv_dog);

			view.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) view.getTag();
		}
		
		if(dogs.size()>0) {
			DogBean dog = dogs.get(position);	
			mHolder.tv_num.setText(dog.getNum());
			mHolder.tv_english_name.setText(dog.getEnglish_name());
			mHolder.tv_chinese_name.setText(dog.getChinese_name());
			mHolder.tv_weight.setText(dog.getWeight());
			mHolder.tv_shoulder_height.setText(dog.getShoulder_height());
			mHolder.tv_body_type.setText(get_body_type(dog.getBody_type()));
			mHolder.tv_function.setText(get_function(dog.getFunction()));
			mHolder.tv_price.setText(dog.getPrice());
			mHolder.tv_characteristic.setText(dog.getCharacteristic());
			mHolder.tv_iq.setText(dog.getIq());
			mHolder.iv_dog.setImageResource(dogs_id[position]);
		}
		
		return view;
	}
	
	
	
	/**
	 * 获取狗的大小
	 * 
	 * @param type
	 * @return
	 */
	public String get_body_type(String type) {
		if (!StringUtils.isEmpty(type)) {
			if (type.trim().equals("xsmall")) {
				return "超小型";
			} else if (type.trim().equals("small")) {
				return "小型犬";
			} else if (type.trim().equals("medium")) {
				return "中型犬";
			} else if (type.trim().equals("large")) {
				return "大型犬";
			} else if (type.trim().equals("xlarge")) {
				return "超大型";
			}

			return "未知型";
		}

		return "类型无记录";
	}

	/**
	 * 获取狗的功能类型
	 * @param function
	 * @return
	 */
	public String get_function(String function) {
		if (!StringUtils.isEmpty(function)) {
			if (function.trim().equals("home")) {
				return "家庭犬";
			} else if (function.trim().equals("toy")) {
				return "玩具犬";
			} else if (function.trim().equals("work")) {
				return "工作犬";
			} else if (function.trim().equals("terrier")) {
				return "梗类犬";
			} else if (function.trim().equals("shepherd")) {
				return "牧羊犬";
			} else if (function.trim().equals("hunting")) {
				return "狩猎犬";
			} else if (function.trim().equals("hunting_gun")) {
				return "猎枪犬";
			}

			return "未知犬种";
		}

		return "犬种无记录";
	}
	
	private static class ViewHolder {
		TextView tv_num;
		TextView tv_chinese_name;
		TextView tv_english_name;
		ImageView iv_dog;
		TextView tv_weight;
		TextView tv_shoulder_height;
		TextView tv_body_type;
		TextView tv_function;
		TextView tv_price;
		TextView tv_characteristic;
		TextView tv_iq;
	}

}
