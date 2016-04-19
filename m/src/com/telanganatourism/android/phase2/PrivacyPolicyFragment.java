package com.telanganatourism.android.phase2;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.flurry.android.FlurryAgent;

public class PrivacyPolicyFragment extends Fragment {

	public PrivacyPolicyFragment() {
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.privacy_policy_fragment, container, false);

		MainActivity.txt_logo.setVisibility(View.GONE);

		MainActivity.txt_logo1.setVisibility(View.VISIBLE);

		MainActivity.txt_title.setVisibility(View.VISIBLE);

		MainActivity.txt_title.setText(getResources().getString(
				R.string.privacy_policy_sidemenu));

		MainActivity.txt_title.setTextColor(Color.parseColor("#000000"));

		MainActivity.txt_filter.setVisibility(View.GONE);

		MainActivity.txt_location.setVisibility(View.GONE);

		MainActivity.txt_search.setVisibility(View.GONE);

		MainActivity.txt_Edit.setVisibility(View.GONE);

		MainActivity.btn_menu.setBackgroundResource(R.drawable.menu_green);

		MainActivity.headerLayout.setBackgroundColor(Color
				.parseColor("#ffffff"));
		
		WebView matterWebView = (WebView) rootView.findViewById(R.id.matter_web);
		
		matterWebView.getSettings().setJavaScriptEnabled(true);
		matterWebView.loadUrl("file:///android_asset/privacy_policy.htm");

		
		return rootView;
	}


	@Override
	public void onStart() {
		super.onStart();
		FlurryAgent.onStartSession(getActivity(), "6JH538X28S58R9MTR7J8");
	}

	@Override
	public void onStop() {
		super.onStop();
		FlurryAgent.onEndSession(getActivity());
	}

}
