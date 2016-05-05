package com.mylibgdxgame.flygame.android;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.mylibgdxgame.flygame.FlyGame;

public class AndroidLauncher extends AndroidApplication {
	protected AdView adView;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		RelativeLayout layout=new RelativeLayout(this);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		View gameView = initializeForView(new FlyGame(), config);
		config.useAccelerometer=false;
		config.useCompass=false;
		config.useWakelock=true;
		layout.addView(gameView);

		adView=new AdView(this);
		adView.setAdSize(AdSize.SMART_BANNER);
		adView.setAdUnitId("ca-app-pub");
		AdRequest.Builder builder = new AdRequest.Builder();
		builder.addTestDevice("samsung-galaxy");

		RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		layout.addView(adView,adParams);
		adView.loadAd(builder.build());

	}
}
