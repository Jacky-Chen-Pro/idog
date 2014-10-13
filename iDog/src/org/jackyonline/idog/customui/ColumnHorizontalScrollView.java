package org.jackyonline.idog.customui;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

/**
 * �Զ���HorizontalScrollView�������ж�����������ӰЧ��
 * 
 * @author Jacky_Chen
 * 
 */
public class ColumnHorizontalScrollView extends HorizontalScrollView {

	/** �������岼�� */
	private View ll_content;
	/** ���������Ŀѡ�񲼾� */
	private View ll_more;
	/** �����϶������� */
	private View rl_column;
	/** ����ӰͼƬ */
	private ImageView leftImage;
	/** ����ӰͼƬ */
	private ImageView rightImage;
	/** ��Ļ��� */
	private int mScreenWidth = 0;
	/** ����Ļactivity */
	private Activity activity;

	public ColumnHorizontalScrollView(Context context) {
		super(context);
	}

	public ColumnHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ColumnHorizontalScrollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		
		shade_showOrHide();
		
		if(!activity.isFinishing() && ll_content !=null && leftImage!=null && rightImage!=null && ll_more!=null && rl_column !=null){
			if(ll_content.getWidth() <= mScreenWidth){
				leftImage.setVisibility(View.GONE);
				rightImage.setVisibility(View.GONE);
			}
		}else{
			return;
		}
		if(l ==0){
			leftImage.setVisibility(View.GONE);
			rightImage.setVisibility(View.VISIBLE);
			return;
		}
		if(ll_content.getWidth() - l + ll_more.getWidth() + rl_column.getLeft() == mScreenWidth){
			leftImage.setVisibility(View.VISIBLE);
			rightImage.setVisibility(View.GONE);
			return;
		}
		leftImage.setVisibility(View.VISIBLE);
	   rightImage.setVisibility(View.VISIBLE);
	}
	
	/** 
	 * ���븸�಼���е���Դ�ļ�
	 * */
	public void setParam(Activity activity, int mScreenWidth,View paramView1,ImageView paramView2, ImageView paramView3 ,View paramView4,View paramView5){
		this.activity = activity;
		this.mScreenWidth = mScreenWidth;
		ll_content = paramView1;
		leftImage = paramView2;
		rightImage = paramView3;
		ll_more = paramView4;
		rl_column = paramView5;
	}
	/**
	 * �ж�������Ӱ����ʾ����Ч��
	 */
	public void shade_showOrHide() {
		if (activity.isFinishing() && ll_content != null) {
			measure(0, 0);
			// ���������С����Ļ��ȵĻ�����������Ӱ������
			if (mScreenWidth >= getMeasuredWidth()) {
				leftImage.setVisibility(View.GONE);
				rightImage.setVisibility(View.GONE);
			}
		} else {
			return;
		}

		// ��������������ʱ�������Ӱ���أ��ұ���ʾ
		if (getLeft() == 0) {
			leftImage.setVisibility(View.GONE);
			rightImage.setVisibility(View.VISIBLE);
			return;
		}

		// ������������ұ�ʱ�������Ӱ��ʾ���ұ�����
		if (getRight() == getMeasuredWidth() - mScreenWidth) {
			leftImage.setVisibility(View.VISIBLE);
			rightImage.setVisibility(View.GONE);
			return;
		}

		// ����˵�����м�λ�ã�������Ӱ����ʾ
		leftImage.setVisibility(View.VISIBLE);
		rightImage.setVisibility(View.VISIBLE);
	}

}
