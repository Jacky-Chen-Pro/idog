package org.jackyonline.idog;

import org.jackyonline.idog.tools.StringUtils;

import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnKeyListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class DogWebViewActivity extends Activity {

	private ProgressBar mPb_loading;
	
	private String url;
	public static final int BACKKEY = 0;
	private WebView webView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_webview_dog);

		
		webView = (WebView) findViewById(R.id.wb_dog);
		mPb_loading = (ProgressBar) findViewById(R.id.pb_loading);
		WebSettings webSet = webView.getSettings();
		webSet.setJavaScriptEnabled(true);
		Bundle bundle = getIntent().getExtras();
		url = bundle.getString("address");
		
		if (StringUtils.isEmpty(url)) {
			url = "http://www.baidu.com";
		}
		
		webView.loadUrl(url);
		webView.setWebViewClient(new MyWebViewClient());
		webView.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					if (keyCode == KeyEvent.KEYCODE_BACK) {
						if (webView.canGoBack()) {
							webView.goBack();
							return true;
						}
					}
				}
				return false;
			}
		});
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			if(NavUtils.getParentActivityIntent(this) != null) {
				getActionBar().setDisplayHomeAsUpEnabled(true);
			}
			getActionBar().setTitle(R.string.wiki);
		}
	}

	/**
	 * WebView¼àÌý¿Ø¼þ
	 * 
	 * @author Jacky_Chen
	 * 
	 */
	private class MyWebViewClient extends WebViewClient {

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			mPb_loading.setVisibility(View.VISIBLE);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			mPb_loading.setVisibility(View.GONE);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			if (NavUtils.getParentActivityIntent(this) != null) {
				NavUtils.navigateUpFromSameTask(DogWebViewActivity.this);
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
