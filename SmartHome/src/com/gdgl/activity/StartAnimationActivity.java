package com.gdgl.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.view.Display;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.gdgl.network.NetworkConnectivity;
import com.gdgl.smarthome.R;
import com.testin.agent.TestinAgent;

public class StartAnimationActivity extends Activity {

	// private AnimationDrawable animationDrawable;
	ImageView loadlogo, loadcircle;
	// private Intent serviceIntent;
	private final static long SPLASH_DELAY_MILLIS = 3200;
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TestinAgent.init(this, "5a08bd1e5b7f8b7ad2aa7be32ebac763", "testing");
		setContentView(R.layout.startanimation);
		
		loadlogo = (ImageView) findViewById(R.id.load_logo);
		loadcircle = (ImageView) findViewById(R.id.load_circle);

		AnimationSet animationSet1 = new AnimationSet(true);
		AlphaAnimation alphaAnimation1 = new AlphaAnimation(0, 1);
		alphaAnimation1.setDuration(1000);
		animationSet1.addAnimation(alphaAnimation1);
		AlphaAnimation alphaAnimation2 = new AlphaAnimation(1, 0);
		alphaAnimation2.setDuration(1000);
		alphaAnimation2.setStartOffset(2000);
		animationSet1.addAnimation(alphaAnimation2);
		animationSet1.setFillBefore(false);
		animationSet1.setFillAfter(true);
		loadcircle.startAnimation(animationSet1);

		AnimationSet animationSet2 = new AnimationSet(true);
		AlphaAnimation alphaAnimation3 = new AlphaAnimation(0, 1);
		alphaAnimation3.setDuration(2000);
		animationSet2.addAnimation(alphaAnimation3);
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int screenHeight = size.y;
		// Log.i("translateAnimation", "screenHeight="+screenHeight);
		float density = getResources().getDisplayMetrics().density;
		// Log.i("translateAnimation", "density="+density);
		TranslateAnimation translateAnimation = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 0f, Animation.ABSOLUTE,
				-(screenHeight / 2 - (int) (density * 112 + 0.5)));
		translateAnimation.setDuration(1000);
		translateAnimation
				.setInterpolator(new AccelerateDecelerateInterpolator());
		translateAnimation.setStartOffset(2000);
		animationSet2.addAnimation(translateAnimation);
		animationSet2.setFillAfter(true);
		loadlogo.startAnimation(animationSet2);

		new Thread(new Runnable() {

			@Override
			public void run() {
				Looper.prepare();
				NetworkConnectivity.networkStatus = NetworkConnectivity.getInstance()
						.getConnecitivityNetwork();
			}
		}).start();

		// image.setBackgroundResource(R.drawable.startanimation);
		// animationDrawable = (AnimationDrawable) image.getBackground();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				// animationDrawable.stop();
				// animationDrawable = null; // key point of resolving out of
				// memory这句话为解决内存溢出的关键，开始没有添加
				goLogin();
			}
		}, SPLASH_DELAY_MILLIS);

	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		TestinAgent.onStart(StartAnimationActivity.this);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		TestinAgent.onStop(this);
	}
	private void goLogin() {
		Intent intent = new Intent(StartAnimationActivity.this,
				LoginActivity.class);
//		intent.putExtra("networkStatus", NetworkConnectivity.networkStatus);
		StartAnimationActivity.this.startActivity(intent);
		StartAnimationActivity.this.finish();
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
	}

	// @Override
	// public void onWindowFocusChanged(boolean hasFocus) {
	// if (animationDrawable != null) {
	// animationDrawable.start();
	// }
	// super.onWindowFocusChanged(hasFocus);
	// }

}
