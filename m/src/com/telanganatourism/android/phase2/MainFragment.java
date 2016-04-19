package com.telanganatourism.android.phase2;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.flurry.android.FlurryAgent;

public class MainFragment extends Fragment {

	// private TabHost mTabHost;
	// private ViewPager mViewPager;
	// private GestureDetectorCompat gDetector;
	// static HorizontalScrollView hs;
	// static int screenWidth;
	// // private PagerSlidingTabStrip tabs;
	// private ViewPager pager;
	// // private MyPagerAdapter adapter;
	// static String category = "";
	// static String keyword = "";
	// GridView mGridView;
	// Gallery gallery;
	// private LinearLayout mDotsLayout;
	//
	// static TextView mDotsText[];
	// private int mDotsCount;
	// LinearLayout ly1, ly2, ly3, ly4, ly5, ly6, ly7, ly8, ly9;
	// TextView t1, t2, t3, t4, t5, t6, t7, t8, t9;
	// //AQuery androidAQuery;
	// private final Handler handler = new Handler();
	private Drawable oldBackground = null;
	private int currentColor = 0xFF666666;
	//
	// //Utility util;
	// // ViewPagerAdapterS adapter;
	// //ArrayList<ItemsObj> item_details_arrays;
	// // MainMenuAdapter mainMenuAdapter;
	//
	// static String[] MENUES = new String[] { "Food", "Entertainment",
	// "Garments", "Electronics", "Brands", "Deals", "Toiltes",
	// "Emergency", "Layout" };
	//
	// static Integer[] mThumbIds = { R.drawable.ic_launcher,
	// R.drawable.menu_icon_white,
	// R.drawable.ic_launcher};
	// ViewPager viewPager;

	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private ViewFlipper mViewFlipper;
	private AnimationListener mAnimationListener;
	private Context mContext;
	// TextView mDotsText[];
	// private int mDotsCount;
	// private LinearLayout mDotsLayout;
	Runnable runnable;
	private final Handler handler = new Handler();

	ImageView mImageView;
	RelativeLayout attrLayout;

	public MainFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.dashboard, container, false);

		// Button button = (Button)v.findViewById(R.id.button1);

		// button.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_LONG).show();
		// }
		// });
		// mImageView = (ImageView) v.findViewById(R.id.attr_img);
		// mImageView.setOnClickListener(this);

		mViewFlipper = (ViewFlipper) v.findViewById(R.id.view_flipper);
		// final GestureDetector detector = new GestureDetector(
		// new MyGestureDetector());
		// mViewFlipper.setOnTouchListener(new OnTouchListener() {
		// public boolean onTouch(final View view, final MotionEvent event) {
		//
		// System.out.println("touch detected ");
		// detector.onTouchEvent(event);
		// return true;
		// }
		// });

		mViewFlipper.startFlipping();

		// mDotsLayout = (LinearLayout) v.findViewById(R.id.image_count);
		// mDotsCount = mViewFlipper.getChildCount();
		// mDotsText = new TextView[mDotsCount];
		// here we set the dots

		// for (int i = 0; i < mDotsCount; i++) {
		//
		// mDotsText[i] = new TextView(getActivity());
		// mDotsText[i].setText(".");
		// mDotsText[i].setTextSize(45);
		// mDotsText[i].setTypeface(null, Typeface.BOLD);
		// mDotsText[i].setTextColor(android.graphics.Color.GRAY);
		// // mDotsLayout.addView(mDotsText[i]);
		// mDotsText[i].setId(i);
		//
		// mDotsText[i].setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		//
		// mViewFlipper.setDisplayedChild(v.getId());
		//
		// for (int i = 0; i < mDotsCount; i++) {
		// mDotsText[i].setTextColor(Color.GRAY);
		// }
		//
		// mDotsText[v.getId()].setTextColor(Color.WHITE);
		// // gallery.setSelection(v.getId());
		// }
		// });
		//
		// mDotsText[mViewFlipper.getDisplayedChild()]
		// .setTextColor(Color.WHITE);
		// }

		// runnable = new Runnable() {
		// public void run() {
		//
		// for (int j = 0; j < mDotsCount; j++) {
		// mDotsText[j].setTextColor(Color.GRAY);
		// }
		//
		// System.out.println("current view "
		// + mViewFlipper.getDisplayedChild());
		// mDotsText[mViewFlipper.getDisplayedChild()]
		// .setTextColor(Color.CYAN);
		//
		// handler.postDelayed(runnable, 0);
		// }
		// };

		// changeColor(Color.parseColor("#ffffff"));
		// MainActivity.roo_lay.setBackgroundColor(Color.parseColor("#ffffff"));
		// MainActivity.mTitleTextView.setText("Layout");
		// //
		// MainActivity.imageButton.setBackgroundResource(R.drawable.menu_icon);
		// MainActivity.mTitleTextView.setTextColor(Color.parseColor("#ffffff"));
		// MainActivity.log_img.setVisibility(View.VISIBLE);
		// MainActivity.mTitleTextView.setVisibility(View.GONE);
		// MainActivity.search_txT
		// .setBackgroundResource(android.R.drawable.ic_search_category_default);
		// MainActivity.search_txT.setVisibility(View.GONE);
		return v;
	}

	// @Override
	// public void onCreate(Bundle instance) {
	// super.onCreate(instance);
	// }
	//
	// @Override
	// public void onActivityCreated(@Nullable Bundle savedInstanceState) {
	// // TODO Auto-generated method stub
	// super.onActivityCreated(savedInstanceState);
	// }

	// public void onClick(View v) {
	// Intent mainIntent = new Intent(getActivity(), DetailScreen1.class);
	// startActivity(mainIntent);
	// }
	// class MyGestureDetector extends SimpleOnGestureListener {
	// @Override
	// public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
	// float velocityY) {
	// try {
	//
	// // right to left swipe
	// if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
	// && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
	//
	// mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(
	// getActivity(), R.anim.left_in1));
	// mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(
	// getActivity(), R.anim.left_out1));
	// mViewFlipper.showNext();
	//
	// // for (int i = 0; i < mDotsCount; i++) {
	// // mDotsText[i].setTextColor(Color.GRAY);
	// // }
	// // mDotsText[mViewFlipper.getDisplayedChild()]
	// // .setTextColor(Color.WHITE);
	//
	// return true;
	// } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
	// && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
	// mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(
	// getActivity(), R.anim.right_in1));
	// mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(
	// getActivity(), R.anim.right_out1));
	// mViewFlipper.showPrevious();
	//
	// // for (int i = 0; i < mDotsCount; i++) {
	// // mDotsText[i].setTextColor(Color.GRAY);
	// // }
	// // mDotsText[mViewFlipper.getDisplayedChild()]
	// // .setTextColor(Color.WHITE);
	//
	// return true;
	// }
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// return false;
	// }
	// }

	private void getDots() {

		// mDotsCount=mDotsCount;
		// System.out.println(" on create dots count " +
		// viewPager.getChildCount()
		// + " dots num " + mDotsCount);
		// mDotsText = new TextView[SplashActivity.img_count];
		//
		// // here we set the dots
		// // j=0;
		// mDotsLayout.removeAllViews();
		// for (int i = 0; i < SplashActivity.img_count; i++) {
		// // j++;
		// mDotsText[i] = new TextView(getActivity());
		// mDotsText[i].setText("-");
		// mDotsText[i].setTextSize(45);
		// mDotsText[i].setTypeface(null, Typeface.BOLD);
		// mDotsText[i].setTextColor(android.graphics.Color.RED);
		// mDotsLayout.addView(mDotsText[i]);
		// mDotsText[i].setId(i);
		//
		// /*mDotsText[i].setOnClickListener(new OnClickListener() {
		// // int j=mDotsText[i].getId();
		// @Override
		// public void onClick(View v) {
		// viewPager.setCurrentItem(v.getId());
		// for (int i = 0; i < mDotsCount; i++) {
		// mDotsText[i].setTextColor(Color.GRAY);
		// }
		// mDotsText[v.getId()].setTextColor(Color.BLUE);
		// // gallery.setSelection(v.getId());
		// }
		// });*/
		//
		// // mDotsText[viewPager.getCurrentItem()].setTextColor(Color.BLUE);
		// }
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	private void changeColor(int newColor) {

		// tabs.setIndicatorColor(newColor);

		// change ActionBar color just if an ActionBar is available
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

			Drawable colorDrawable = new ColorDrawable(newColor);
			Drawable bottomDrawable = getResources().getDrawable(
					R.drawable.actionbar_bottom);
			LayerDrawable ld = new LayerDrawable(new Drawable[] {
					colorDrawable, bottomDrawable });

			if (oldBackground == null) {

				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
					ld.setCallback(drawableCallback);
				} else {
					getActivity().getActionBar().setBackgroundDrawable(ld);
				}
			} else {
				TransitionDrawable td = new TransitionDrawable(new Drawable[] {
						oldBackground, ld });
				// workaround for broken ActionBarContainer drawable handling on
				// pre-API 17 builds
				// https://github.com/android/platform_frameworks_base/commit/a7cc06d82e45918c37429a59b14545c6a57db4e4
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
					td.setCallback(drawableCallback);
				} else {
					getActivity().getActionBar().setBackgroundDrawable(td);
				}
				td.startTransition(200);
			}

			oldBackground = ld;

			// http://stackoverflow.com/questions/11002691/actionbar-setbackgrounddrawable-nulling-background-from-thread-handler
			getActivity().getActionBar().setDisplayShowTitleEnabled(false);
			getActivity().getActionBar().setDisplayShowTitleEnabled(true);

		}

		currentColor = newColor;

	}

	private Drawable.Callback drawableCallback = new Drawable.Callback() {
		@TargetApi(Build.VERSION_CODES.HONEYCOMB)
		@SuppressLint("NewApi")
		@Override
		public void invalidateDrawable(Drawable who) {
			getActivity().getActionBar().setBackgroundDrawable(who);
		}

		@Override
		public void scheduleDrawable(Drawable who, Runnable what, long when) {
			handler.postAtTime(what, when);
		}

		@Override
		public void unscheduleDrawable(Drawable who, Runnable what) {
			handler.removeCallbacks(what);
		}
	};

	static class ViewHolder {
		ImageView mImageView;
		TextView mTextView;
	}

	private class MainMenuListing extends AsyncTask<Object, Void, Boolean> {

		ProgressDialog myProgressDialog;

		protected void onPreExecute() {
			super.onPreExecute();
			myProgressDialog = new ProgressDialog(getActivity());
			myProgressDialog.setMessage("please wait ...");
			myProgressDialog.setCancelable(false);
			myProgressDialog.show();
		}

		@Override
		protected Boolean doInBackground(Object... params) {

			try {

			} catch (Exception e) {

			}

			return null;
		}

		@Override
		protected void onPostExecute(Boolean success) {
			super.onPostExecute(success);

			try {

			} catch (Exception e) {
				if (myProgressDialog.isShowing())
					myProgressDialog.dismiss();
			}

		}
	}

	@Override
	public void onPause() {
		super.onPause();
		if (handler != null) {
			handler.removeCallbacks(runnable);
		}
	}

	// Also, don't forget to rerun onResume()

	@Override
	public void onResume() {
		super.onResume(); // Always call the superclass method first
		handler.postDelayed(runnable, 1000);
	}

	public boolean isTabletDevice(Resources resources) {
		int screenLayout = resources.getConfiguration().screenLayout
				& Configuration.SCREENLAYOUT_SIZE_MASK;
		boolean isScreenLarge = (screenLayout == Configuration.SCREENLAYOUT_SIZE_LARGE);
		boolean isScreenXlarge = (screenLayout == Configuration.SCREENLAYOUT_SIZE_XLARGE);
		return (isScreenLarge || isScreenXlarge);
	}

	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// Intent intent = new Intent(getActivity(), DetailScreen1.class);
	//
	// startActivity(intent);
	// }
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