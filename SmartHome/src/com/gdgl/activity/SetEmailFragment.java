package com.gdgl.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gc.materialdesign.views.CheckBox;
import com.gc.materialdesign.views.CheckBox.OnCheckListener;
import com.gdgl.drawer.DeviceTabFragment;
import com.gdgl.manager.CGIManager;
import com.gdgl.manager.Manger;
import com.gdgl.manager.UIListener;
import com.gdgl.mydata.Event;
import com.gdgl.mydata.EventType;
import com.gdgl.mydata.getFromSharedPreferences;
import com.gdgl.smarthome.R;
import com.gdgl.util.MyApplicationFragment;

public class SetEmailFragment extends Fragment implements UIListener {
	private View mView;
	public static boolean ENABLE_EMAIL_TXT = true;
	public static boolean ENABLE__EMAIL_VEDIO = true;
	public static boolean ENABLE_EMAIL_PIC = true;
	EditText email_name;
	Button btn_commit_email;

	String E_name;
	String gateway_MAC;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		CGIManager.getInstance().addObserver(SetEmailFragment.this);
		//getFromSharedPreferences.setEmailFlag(1);
		/*if(ENABLE_EMAIL_TXT && ENABLE__EMAIL_VEDIO && ENABLE_EMAIL_PIC) {
			getFromSharedPreferences.setEmailFlag(4);			
		} else if (ENABLE_EMAIL_TXT && ENABLE__EMAIL_VEDIO &&  !ENABLE_EMAIL_PIC) {
				getFromSharedPreferences.setEmailFlag(3);
		} else if(ENABLE_EMAIL_TXT && !ENABLE__EMAIL_VEDIO && ENABLE_EMAIL_PIC){
			getFromSharedPreferences.setEmailFlag(2);
		} else if(ENABLE_EMAIL_TXT && !ENABLE__EMAIL_VEDIO && !ENABLE_EMAIL_PIC) {
			getFromSharedPreferences.setEmailFlag(1);
		} else if(!ENABLE_EMAIL_TXT ) {
			getFromSharedPreferences.setEmailFlag(0);
		} else{
			getFromSharedPreferences.setEmailFlag(-1);
		}*/
		
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mView = inflater.inflate(R.layout.set_email, null);
		initView();
		CGIManager.getInstance().getEmailAddrStatus(gateway_MAC);
		return mView;
	}

	private void initView() {
		// TODO Auto-generated method stub

		getFromSharedPreferences.setsharedPreferences((Context) getActivity());
		E_name = getFromSharedPreferences.getEmailName();
		email_name = (EditText) mView.findViewById(R.id.email_name);
		email_name.setText(E_name);
		/*
		 * if (E_name != null){ email_name.setText(E_name); } else {
		 * email_name.setHint("XXX@XXXX"); }
		 */
		final CheckBox enableEmail = (CheckBox) mView
				.findViewById(R.id.enableEmail);
		final CheckBox enableSendVideo = (CheckBox) mView
				.findViewById(R.id.enableSendVideo);
		final CheckBox enableSendPic = (CheckBox) mView
				.findViewById(R.id.enableSendPic);
		enableEmail.setChecked(getFromSharedPreferences.getEmailEnable());
		enableSendVideo.setChecked(getFromSharedPreferences.getEmailVideoEnable());
		enableSendPic.setChecked(getFromSharedPreferences.getEmailPicEnable());
		if(enableEmail.isCheck()&&enableSendVideo.isCheck()&&enableSendPic.isCheck()){
			getFromSharedPreferences.setEmailFlag(4);
		}else if(enableEmail.isCheck() && enableSendVideo.isCheck() && !enableSendPic.isCheck()){
			getFromSharedPreferences.setEmailFlag(3);
		} else if(enableEmail.isCheck() &&! enableSendVideo.isCheck() && enableSendPic.isCheck()){
			getFromSharedPreferences.setEmailFlag(2);
		} else if(enableEmail.isCheck() &&! enableSendVideo.isCheck() && !enableSendPic.isCheck()){
			getFromSharedPreferences.setEmailFlag(1);
		} else if(!enableEmail.isCheck()) {
			getFromSharedPreferences.setEmailFlag(0);
		}
		
		enableEmail.setOncheckListener(new OnCheckListener() {

			@Override
			public void onCheck(boolean check) {
				// TODO Auto-generated method stub
				ENABLE_EMAIL_TXT = check;
				if(!ENABLE_EMAIL_TXT ) {
					getFromSharedPreferences.setEmailFlag(0);
					enableSendVideo.setChecked(false);
					enableSendPic.setChecked(false);
				}else {
					getFromSharedPreferences.setEmailFlag(1);
				}
				//CGIManager.getInstance().getEmailAddrStatus(gateway_MAC);
				getFromSharedPreferences.setEmailEnable(ENABLE_EMAIL_TXT);
			}
		});
		enableEmail.post(new Runnable() {
			@Override
			public void run() {
				enableEmail.setChecked(ENABLE_EMAIL_TXT);	
			}
		});
		/*final CheckBox enableSendVideo = (CheckBox) mView
				.findViewById(R.id.enableSendVideo);*/
		enableSendVideo.setOncheckListener(new OnCheckListener() {

			@Override
			public void onCheck(boolean check) {
				// TODO Auto-generated method stub
				//ENABLE__EMAIL_VEDIO = check;				
				if (ENABLE_EMAIL_TXT){
					ENABLE__EMAIL_VEDIO = check;
					if(check){
						if(getFromSharedPreferences.getEmailFlag() == 1){
							getFromSharedPreferences.setEmailFlag(3);
						} else if(getFromSharedPreferences.getEmailFlag() == 2){
							getFromSharedPreferences.setEmailFlag(4);
						}
					}else if(getFromSharedPreferences.getEmailFlag() == 4){
						getFromSharedPreferences.setEmailFlag(2);
					} else if(getFromSharedPreferences.getEmailFlag() == 3){
						getFromSharedPreferences.setEmailFlag(1);
					}					
				}else {
					ENABLE__EMAIL_VEDIO = false;
				}
				enableSendVideo.setChecked(ENABLE__EMAIL_VEDIO);
				getFromSharedPreferences.setEmailVideoEnable(ENABLE__EMAIL_VEDIO);
				//enableSendVideo.
			}
		});
		enableSendVideo.post(new Runnable() {
			@Override
			public void run() {
				enableSendVideo.setChecked(ENABLE__EMAIL_VEDIO);
			}
		});
		/*final CheckBox enableSendPic = (CheckBox) mView
				.findViewById(R.id.enableSendPic);*/
		enableSendPic.setOncheckListener(new OnCheckListener() {

			@Override
			public void onCheck(boolean check) {
				// TODO Auto-generated method stub
				//ENABLE_EMAIL_PIC = check;
				if (ENABLE_EMAIL_TXT){
					ENABLE_EMAIL_PIC = check;
					if(check){
						if(getFromSharedPreferences.getEmailFlag() == 1){
							getFromSharedPreferences.setEmailFlag(2);
						} else if(getFromSharedPreferences.getEmailFlag() == 3){
							getFromSharedPreferences.setEmailFlag(4);
						}
					}else if(getFromSharedPreferences.getEmailFlag() == 4){
						getFromSharedPreferences.setEmailFlag(3);
					} else if(getFromSharedPreferences.getEmailFlag() == 2){
						getFromSharedPreferences.setEmailFlag(1);
					}
				}else {
					ENABLE_EMAIL_PIC = false;
				}
				enableSendPic.setChecked(ENABLE_EMAIL_PIC);
				getFromSharedPreferences.setEmailPicEnable(ENABLE_EMAIL_PIC);
			}
		});
		enableSendPic.post(new Runnable() {
			@Override
			public void run() {
				enableSendPic.setChecked(ENABLE_EMAIL_PIC);
			}
		});
		gateway_MAC = getFromSharedPreferences.getGatewayMAC();
		btn_commit_email = (Button) mView.findViewById(R.id.commit_email);
		btn_commit_email.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				E_name = email_name.getText().toString();

				if (null == E_name || E_name.length() <= 0) {
					Toast.makeText(getActivity(), "请输入邮箱名", Toast.LENGTH_SHORT)
							.show();
					email_name.requestFocus();
					return;
				} else {
					CGIManager.getInstance().changeEmailAddress(gateway_MAC,
							E_name, getFromSharedPreferences.getEmailFlag());
					getFromSharedPreferences.setEmailName(E_name.trim());
					//CGIManager.getInstance().getEmailAddrStatus(gateway_MAC);
				}
			}
		});

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		CGIManager.getInstance().deleteObserver(SetEmailFragment.this);
	}

	@Override
	public void update(Manger observer, Object object) {
		// TODO Auto-generated method stub
		final Event event = (Event) object;
		if (EventType.CHANGEEMAILADDRESS == event.getType()) {

			if (event.isSuccess() == true) {
				Toast.makeText(getActivity(), "设置成功", Toast.LENGTH_SHORT)
						.show();
				//MyApplicationFragment.getInstance().removeLastFragment();
			} else {
				// if failed,prompt a Toast
				Toast.makeText(getActivity(), "设置失败，请稍后重试", Toast.LENGTH_SHORT)
						.show();
			}
		}
		if (EventType.GETEMAILADDRESS == event.getType()) {

			if (event.isSuccess() == true) {
				Toast.makeText(getActivity(), "获取邮箱成功", Toast.LENGTH_SHORT)
						.show();
				//MyApplicationFragment.getInstance().removeLastFragment();
			} else {
				// if failed,prompt a Toast
				Toast.makeText(getActivity(), "获取邮箱失败，请稍后重新绑定", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

}
