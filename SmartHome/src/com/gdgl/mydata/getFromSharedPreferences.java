package com.gdgl.mydata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gdgl.model.RemoteControl;
import com.gdgl.util.UiUtils;

import android.content.Context;
import android.content.SharedPreferences;

public class getFromSharedPreferences {

	private static SharedPreferences mSharedPreferences = null;
	private static SharedPreferences.Editor mEditor = null;

	public static void setsharedPreferences(Context context) {
		mSharedPreferences = context.getSharedPreferences(
				UiUtils.SharedPreferences_SETTING_INFOS, context.MODE_PRIVATE);
	}

	public static boolean getEnableIPC() {
		return mSharedPreferences.getBoolean(UiUtils.ENABLE_IPC, true);
	}
	
	public static boolean setEnableIPC(boolean enableIPC) {
		mEditor = mSharedPreferences.edit();
		mEditor.putBoolean(UiUtils.ENABLE_IPC, enableIPC);
		return mEditor.commit();
	}
	
	public static String getPwd() {
		return mSharedPreferences.getString(UiUtils.PWD, UiUtils.EMPTY_STR);
	}

	public static String getLoginName() {
		return mSharedPreferences.getString(UiUtils.LOGIN_NAME, UiUtils.EMPTY_STR);
	}

	public static String getGatewayMAC() {
		return mSharedPreferences.getString(UiUtils.GATEWAY_MAC, UiUtils.EMPTY_STR);
	}

	public static String getAliasName() {
		return mSharedPreferences.getString(UiUtils.ALIAS, UiUtils.EMPTY_STR);
	}
	
	public static boolean getIsRemerber() {
		return mSharedPreferences.getBoolean(UiUtils.IS_REMERBER_PWD, false);
	}

	public static boolean getIsAutoLoging() {
		return mSharedPreferences.getBoolean(UiUtils.IS_AUTO_LOGIN, false);
	}

	public static boolean setLogin(AccountInfo accountInfo, boolean isRemerber,
			boolean isAuto) {
		mEditor = mSharedPreferences.edit();

		mEditor.putString(UiUtils.GATEWAY_MAC, accountInfo.getId());
		mEditor.putString(UiUtils.LOGIN_NAME, accountInfo.getAlias());
		if(isRemerber){
			mEditor.putString(UiUtils.PWD, accountInfo.getPassword());
		}else{
			mEditor.putString(UiUtils.PWD, "");
		}
		mEditor.putBoolean(UiUtils.IS_REMERBER_PWD, isRemerber);
		mEditor.putBoolean(UiUtils.IS_AUTO_LOGIN, isAuto);

		return mEditor.commit();
	}
	
	public static boolean setLogin(String name, String pwd, boolean isRemerber){
		mEditor = mSharedPreferences.edit();
		mEditor.putString(UiUtils.LOGIN_NAME, name);
		mEditor.putString(UiUtils.PWD, pwd);
		
		mEditor.putBoolean(UiUtils.IS_REMERBER_PWD, isRemerber);
		return mEditor.commit();
	}

	public static boolean setPwd(String pwd) {
		mEditor = mSharedPreferences.edit();

		mEditor.putString(UiUtils.PWD, pwd);

		return mEditor.commit();
	}

	public static boolean setLoginName(String name) {
		mEditor = mSharedPreferences.edit();

		mEditor.putString(UiUtils.LOGIN_NAME, name);

		return mEditor.commit();
	}
	
	public static boolean setGatewayMAC(String uid) {
		mEditor = mSharedPreferences.edit();

		mEditor.putString(UiUtils.GATEWAY_MAC, uid);

		return mEditor.commit();
	}

	public static boolean setAliasName(String alias) {
		mEditor = mSharedPreferences.edit();

		mEditor.putString(UiUtils.ALIAS, alias);

		return mEditor.commit();
	}
	
	public static String getUUID() {
		return mSharedPreferences.getString(UiUtils.UUID, UiUtils.EMPTY_STR);
	}
	
	public static boolean setUUID(String uuid) {
		mEditor=mSharedPreferences.edit();
		mEditor.putString(UiUtils.UUID, uuid);
		
		return mEditor.commit();
	}
	
	public static String getJoinNetTime() {
		return mSharedPreferences.getString(UiUtils.JOINNETTIME, UiUtils.EMPTY_STR);
	}
	
	public static boolean setJoinNetTime(String time) {
		mEditor=mSharedPreferences.edit();
		mEditor.putString(UiUtils.JOINNETTIME, time);
		
		return mEditor.commit();
	}
	
	public static boolean setRegion(String mSet) {
		mEditor = mSharedPreferences.edit();
		mEditor.putString(UiUtils.REGION, mSet);
		return mEditor.commit();
	}

	public static String getRegion() {
		String region = mSharedPreferences.getString(UiUtils.REGION, null);
		return region;
	}

	public static String getCommonUsed() {
		String commonused = mSharedPreferences.getString(UiUtils.COMMONUSED,
				null);
		return commonused;
	}

	public static boolean setCommonUsed(String comm) {
		mEditor = mSharedPreferences.edit();
		mEditor.putString(UiUtils.COMMONUSED, comm);
		return mEditor.commit();
	}

	public static int getSceneId() {
		int sceneId = mSharedPreferences.getInt(UiUtils.SCENE, 0);

		mEditor = mSharedPreferences.edit();
		mEditor.putInt(UiUtils.SCENE, sceneId + 1);
		mEditor.commit();
		return sceneId + 1;
	}

	public static List<RemoteControl> getRemoteControl() {
		List<RemoteControl> mList = new ArrayList<RemoteControl>();
		RemoteControl rc;
		String controlString = mSharedPreferences.getString(
				UiUtils.REMOTE_CONTROL, "");
		if (null == controlString || controlString.trim().equals("")) {
			return null;
		}
		String[] RemoteControlNode = controlString.split("##");
		for (String string : RemoteControlNode) {
			String[] controls;
			if (!string.trim().equals("")) {
				controls = string.trim().split("@@");
				rc = new RemoteControl();
				rc.Index = controls[0];
				rc.Name = controls[1];
				rc.IsLearn = controls[2];
				mList.add(rc);
			}
		}
		return mList;

	}
	
	public static List<RemoteControl> getTvKongtiaoRemoteControl(int type) {
		List<RemoteControl> mList = new ArrayList<RemoteControl>();
		RemoteControl rc;
		String controlString="";
		if(1==type){
			controlString = mSharedPreferences.getString(
					UiUtils.KONGTIAO, "");
		}else if(2==type){
			controlString = mSharedPreferences.getString(
					UiUtils.TV, "");
		}
		
		if (null == controlString || controlString.trim().equals("")) {
			return null;
		}
		String[] RemoteControlNode = controlString.split("##");
		for (String string : RemoteControlNode) {
			String[] controls;
			if (!string.trim().equals("")) {
				controls = string.trim().split("@@");
				rc = new RemoteControl();
				rc.Index = controls[0];
				rc.Name = controls[1];
				rc.IsLearn = controls[2];
				mList.add(rc);
			}
		}
		return mList;

	}
	
	public static void addTvKongtiaoRemoteControlList(List<RemoteControl> rc,int type) {
		if (null == rc) {
			return;
		}
		String controls = "";
		for (RemoteControl remoteControl : rc) {
			if (null != remoteControl) {
				controls += remoteControl.Index + "@@" + remoteControl.Name
						+ "@@" + remoteControl.IsLearn + "##";
			}
		}

		mEditor = mSharedPreferences.edit();
		if(1==type){
			mEditor.putString(UiUtils.KONGTIAO, controls);
		}else if(2==type){
			mEditor.putString(UiUtils.TV, controls);
		}
		mEditor.commit();
	}
	
	public static int getRemoteControlId() {
		List<RemoteControl> mList = getRemoteControl();
		if (null == mList || mList.size() == 0) {
			return 1;
		}
		int maxId = 0;
		for (RemoteControl rr : mList) {
			maxId = maxId > Integer.parseInt(rr.Index) ? maxId : Integer
					.parseInt(rr.Index);
		}
		return maxId + 1;

	}

	public static void addRemoteControlList(List<RemoteControl> rc) {
		if (null == rc) {
			return;
		}
		String controls = "";
		for (RemoteControl remoteControl : rc) {
			if (null != remoteControl) {
				controls += remoteControl.Index + "@@" + remoteControl.Name
						+ "@@" + remoteControl.IsLearn + "##";
			}
		}

		mEditor = mSharedPreferences.edit();
		mEditor.putString(UiUtils.REMOTE_CONTROL, controls);
		mEditor.commit();
	}
	
	public static ArrayList<HashMap<String, String>> getUserList(){
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		String userListString = mSharedPreferences.getString(UiUtils.USERLIST,UiUtils.EMPTY_STR);
		if(userListString.equals("")){
			return list;
		}
		try {
			JSONArray jsonArray = new JSONArray(userListString);
			for(int i=0; i<jsonArray.length(); i++){
				
				HashMap<String, String> map = new HashMap<String, String>();
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				map.put("use", jsonObject.getString("use"));
				map.put("pwd", jsonObject.getString("pwd"));
				list.add(map);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static void setUserList(String use, String pwd){
		ArrayList<HashMap<String, String>> list = getUserList();
		int current = -1;
		if(list.size() == 0){
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("use", use);
			map.put("pwd", pwd);
			list.add(map);
		}else{
			for(int i=0; i<list.size(); i++){
				if(list.get(i).get("use") != null && list.get(i).get("use").equals(use)){
					current = i;	
					break;
				}
			}
			if(current == -1){
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("use", use);
				map.put("pwd", pwd);
				list.add(map);
			}else{
				list.get(current).put("pwd", pwd);
			}
		}
		if(list.size() == 0){
			return;
		}
		commitStrStr(UiUtils.USERLIST, hashMapListToJson(list));
	}
	
	public static void removeUserList(String use, String pwd){
		ArrayList<HashMap<String, String>> list = getUserList();
		for(int i=0; i<list.size(); i++){
			if(list.get(i).get("use") != null && list.get(i).get("use").equals(use)){
				list.remove(i);
			}
		}
		commitStrStr(UiUtils.USERLIST, list.toString());
	}
	
	public static void commitStrStr(String key, String value){
		mEditor = mSharedPreferences.edit();
		mEditor.putString(key, value);
		mEditor.commit();
	}
	
	public static ArrayList<HashMap<String, String>> getCloudList(){
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		String cloudListString = mSharedPreferences.getString(UiUtils.CLOUDLIST,UiUtils.EMPTY_STR);
		if(cloudListString.equals("") || cloudListString == null){
			return list;
		}
		try {
			JSONArray jsonArray = new JSONArray(cloudListString);
			for(int i=0; i<jsonArray.length(); i++){
				HashMap<String, String> map = new HashMap<String, String>();
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				map.put("cloud", jsonObject.getString("cloud"));
				list.add(map);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static void setCloudList(String cloud){
		ArrayList<HashMap<String, String>> list = getCloudList();
		int current = -1;
		if(list.size() == 0 && !cloud.equals("")){
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("cloud", cloud);
			list.add(map);
		}else{
			for(int i=0; i<list.size(); i++){
				if(list.get(i).get("cloud") != null && list.get(i).get("cloud").equals(cloud)){
					current = i;	
					break;
				}
			}
			if(current == -1 && !cloud.equals("")){
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("cloud", cloud);
				list.add(map);
			}
		}
		if(list.size() == 0){
			return;
		}
		commitStrStr(UiUtils.CLOUDLIST, hashMapListToJson(list));
	}
	
	public static void removeCloudList(String cloud){
		ArrayList<HashMap<String, String>> list = getCloudList();
		for(int i=0; i<list.size(); i++){
			if(list.get(i).get("cloud") != null && list.get(i).get("cloud").equals(cloud)){
				list.remove(i);
				break;
			}
		}
		commitStrStr(UiUtils.CLOUDLIST, list.toString());
	}
	
	public static String getCloud(){
		return mSharedPreferences.getString(UiUtils.CLOUD,UiUtils.EMPTY_STR);
	}
	
	public static void setCloud(String cloud){
		commitStrStr(UiUtils.CLOUD, cloud);
	}
	
	public static String hashMapListToJson(ArrayList<HashMap<String, String>> list) {
		String string = "[";
		for(HashMap<String, String> map : list){
			string += "{";  
	        for (Iterator it = map.entrySet().iterator(); it.hasNext();) {  
	            Entry e = (Entry) it.next();  
	            string += "\"" + e.getKey() + "\":";  
	            string += "\"" + e.getValue() + "\",";  
	        }  
	        string = string.substring(0, string.lastIndexOf(","));  
	        string += "},"; 
		}
		string = string.substring(0, string.lastIndexOf(","));  
		string += "]"; 
        return string;  
    }  


}
