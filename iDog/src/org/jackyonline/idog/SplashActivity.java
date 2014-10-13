package org.jackyonline.idog;

import org.jackyonline.idog.base.Constants;

import com.umeng.analytics.MobclickAgent;
import com.wandoujia.ads.sdk.AdListener;
import com.wandoujia.ads.sdk.Ads;
import com.wandoujia.ads.sdk.loader.Fetcher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SplashActivity extends Activity {

	/** 延迟时间 */
	private final int SPLASH_DISPLAY_LENGHT = 600;
	private SharedPreferences mSharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_splash);
		new Handler().postDelayed(new Runnable() {
			public void run() {
				mSharedPreferences = getSharedPreferences(Constants.SETTING,
						Context.MODE_PRIVATE);
				Editor editor = mSharedPreferences.edit();
				Intent jumpIntent;
				if (mSharedPreferences.getBoolean(Constants.FIRST_USE, true)) {
					jumpIntent = new Intent(SplashActivity.this,
							GuideActivity.class);
					editor.putBoolean(Constants.FIRST_USE, false);
					editor.commit();
				} else {
					jumpIntent = new Intent(SplashActivity.this,
							MainActivity.class);
				}
				SplashActivity.this.startActivity(jumpIntent);
				SplashActivity.this.overridePendingTransition(R.anim.alpha_in,
						R.anim.alpha_out);
				SplashActivity.this.finish();
			}

		}, SPLASH_DISPLAY_LENGHT);

		try {
			Ads.init(this, Constants.KEY_ID, Constants.SECRET_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * 预加载应用墙
		 */
		Ads.preLoad(this, Fetcher.AdFormat.appwall, Constants.ADVERTISEMENT_ID);
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
