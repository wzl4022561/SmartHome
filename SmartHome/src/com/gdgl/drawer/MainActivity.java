package com.gdgl.drawer;

import com.gdgl.activity.GatewayUpdateDetailDlgFragment;
import com.gdgl.activity.LinkageFragment;
import com.gdgl.activity.ScenesFragment;
import com.gdgl.activity.TimingFragment;
import com.gdgl.libjingle.Libjingle;
import com.gdgl.manager.CallbackManager;
import com.gdgl.manager.Manger;
import com.gdgl.manager.UIListener;
import com.gdgl.mydata.Event;
import com.gdgl.mydata.EventType;
import com.gdgl.mydata.getFromSharedPreferences;
import com.gdgl.network.NetworkConnectivity;
import com.gdgl.reciever.NetWorkChangeReciever;
import com.gdgl.smarthome.R;
import com.gdgl.util.MyApplication;
import com.gdgl.util.MyOKOnlyDlg;
import com.gdgl.util.MyOKOnlyDlg.DialogOutcallback;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements
		NavigationDrawerCallbacks, UIListener, DialogOutcallback {

	public static boolean LOGIN_STATUS = true;

	private Toolbar mToolbar;
	private ActionBar mActionBar;
	private TextView tipsWithoutNet;
	private NavigationDrawerFragment mNavigationDrawerFragment;
	int currentTab = 0;
	private Fragment mfragment;
	private boolean onTopScreen = true;

	NetWorkChangeReciever netWorkChangeReciever;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drawer_activity_main);
		MyApplication.getInstance().addActivity(this);
		MainActivity.LOGIN_STATUS = false;

		tipsWithoutNet = (TextView) findViewById(R.id.checknet);
		setTipText();
		mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
		setSupportActionBar(mToolbar);
		mActionBar = getSupportActionBar();
		mActionBar.setHomeButtonEnabled(true);
		mActionBar.setDisplayShowHomeEnabled(true);
		// getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowTitleEnabled(true);
		mActionBar.setTitle("设备");

		mToolbar.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				switch (item.getItemId()) {
				case R.id.menu_alarm:
					Intent i = new Intent(MainActivity.this,
							AlarmMessageActivity.class);
					startActivity(i);
					break;

				default:
					break;
				}
				return false;
			}
		});

		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.fragment_drawer);
		mNavigationDrawerFragment.setup(R.id.fragment_drawer,
				(DrawerLayout) findViewById(R.id.drawer), mToolbar);

		saveLoginData();

		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		netWorkChangeReciever = NetWorkChangeReciever.getInstance();
		registerReceiver(netWorkChangeReciever, intentFilter);

		netWorkChangeReciever.addObserver(this);
		Libjingle.getInstance().addObserver(this);
		CallbackManager.getInstance().addObserver(this);
	}

	private void setTipText() {
		if (NetworkConnectivity.networkStatus == 0) {
			tipsWithoutNet.setVisibility(View.VISIBLE);
		} else {
			tipsWithoutNet.setVisibility(View.GONE);
		}
	}

	private void saveLoginData() {
		getFromSharedPreferences.setsharedPreferences(MainActivity.this);
		Intent intent = getIntent();
		String name = intent.getStringExtra("name");
		String pwd = intent.getStringExtra("pwd");
		String cloud = intent.getStringExtra("cloud");
		boolean remenber = intent.getBooleanExtra("remenber", true);
		if (remenber) {
			getFromSharedPreferences.setLogin(name, pwd, true);
			getFromSharedPreferences.setUserList(name, pwd);
		} else {
			getFromSharedPreferences.setLogin(name, pwd, false);
			getFromSharedPreferences.setUserList(name, "");
		}
		getFromSharedPreferences.setCloud(cloud);
		getFromSharedPreferences.setCloudList(cloud);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_alarm, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		currentTab = position;
		switch (position) {
		case 0:
			mfragment = new DeviceTabFragment();
			break;
		case 1:
			mfragment = new ScenesFragment();
			break;
		case 2:
			mfragment = new TimingFragment();
			break;
		case 3:
			mfragment = new LinkageFragment();
			break;
		default:
			break;
		}
		FragmentTransaction fragmentTransaction = getSupportFragmentManager()
				.beginTransaction();
		fragmentTransaction.replace(R.id.container, mfragment);
		fragmentTransaction.commit();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		onTopScreen = true;
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		onTopScreen = false;
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		netWorkChangeReciever.deleteObserver(this);
		Libjingle.getInstance().deleteObserver(this);
		CallbackManager.getInstance().deleteObserver(this);
		unregisterReceiver(netWorkChangeReciever);
	}

	@Override
	public void onBackPressed() {
		if (mNavigationDrawerFragment.isDrawerOpen())
			mNavigationDrawerFragment.closeDrawer();
		else
			moveTaskToBack(false);
	}

	@Override
	public void update(Manger observer, Object object) {
		// TODO Auto-generated method stub
		Event event = (Event) object;
		if (EventType.NETWORKCHANGE == event.getType()) {
			if (event.isSuccess() == true) {
				mToolbar.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						setTipText();
					}
				});
			}
		} else if (EventType.MODIFYALIAS == event.getType()) {

			if (event.isSuccess() == true) {
				int status = (Integer) event.getData();
				if (status == 0) {
					mToolbar.post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							MyOKOnlyDlg myOKOnlyDlg = new MyOKOnlyDlg(
									MainActivity.this);
							myOKOnlyDlg.setContent("别名已修改，请重新登录！");
							myOKOnlyDlg.setCannotCanceled();
							myOKOnlyDlg.setDialogCallback(MainActivity.this);
							myOKOnlyDlg.show();
						}
					});
				}
			}
		} else if (EventType.MODIFYPASSWORD == event.getType()) {

			if (event.isSuccess() == true) {
				int status = (Integer) event.getData();
				if (status == 0) {
					mToolbar.post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							getFromSharedPreferences
									.setsharedPreferences(MainActivity.this);
							getFromSharedPreferences.setPwd("");
							MyOKOnlyDlg myOKOnlyDlg = new MyOKOnlyDlg(
									MainActivity.this);
							myOKOnlyDlg.setContent("密码已修改，请重新登录！");
							myOKOnlyDlg.setCannotCanceled();
							myOKOnlyDlg.setDialogCallback(MainActivity.this);
							myOKOnlyDlg.show();
						}
					});
				}
			}
		} else if (EventType.GATEWAYUPDATEBEGINE == event.getType()) {
			if (!GatewayUpdateDetailDlgFragment.UpdateSelf && onTopScreen) {
				GatewayUpdateDetailDlgFragment gatewayUpdateFragment = new GatewayUpdateDetailDlgFragment(
						false);
				gatewayUpdateFragment.show(getSupportFragmentManager(), "");
			}
		}
	}

	@Override
	public void dialogokdo() {
		// TODO Auto-generated method stub
		MyApplication.getInstance().finishSystem();
	}
}