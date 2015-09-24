package com.place.db.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.place.db.dao.IPlaceDAO;
import com.place.db.service.IPlaceManager;
import com.place.entity.PlaceInfo;

@Service
public class PlaceManagerImpl implements IPlaceManager {

	private IPlaceDAO placeDao;

	public IPlaceDAO getPlaceDao() {
		return placeDao;
	}

	@Autowired
	public void setPlaceDao(IPlaceDAO placeDao) {
		this.placeDao = placeDao;
	}

	public String addPlaces(String placeInfos) {
		int flag = 0;
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			JSONArray jsonArray = new JSONArray(placeInfos);
			List<PlaceInfo> places = new ArrayList<PlaceInfo>();
			PlaceInfo placeInfo = null;
			for (int i = 0; i < jsonArray.length(); i++) {
				placeInfo = new PlaceInfo();
				JSONObject item = (JSONObject) jsonArray.get(i);
				placeInfo.setLatitude((String) item.get("latitude"));
				placeInfo.setLongitude((String) item.get("longitude"));
				placeInfo.setProvider((String) item.get("provider"));
				placeInfo.setAccuracy((String) item.get("accuracy"));
				placeInfo.setImei((String) item.get("imei"));
				String time_str = (String) item.get("time");
				try {
					placeInfo.setTime(df.parse(time_str));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				places.add(placeInfo);
			}
			
			flag = placeDao.addPlaces(places);
			System.out.println(flag);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = 0;
		}
		
		JSONArray result = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("resultCode", flag+"");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.put(jsonObject);
		return result.toString();
	}

	public String findPlaceInfoBySql(Object... args) {
		String sql = "from PlaceInfo where imei = ? order by time desc";
		List<PlaceInfo> places = placeDao.findPlaceInfoBySql(sql, args);
		JSONArray result = new JSONArray();
		JSONObject jsonObject = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (PlaceInfo placeInfo : places) {
			jsonObject = new JSONObject();
			try {
				jsonObject.put("latitude", placeInfo.getLatitude());
				jsonObject.put("longitude", placeInfo.getLongitude());
				jsonObject.put("accuracy", placeInfo.getAccuracy());
				jsonObject.put("provider", placeInfo.getProvider());
				jsonObject.put("imei", placeInfo.getImei());
				jsonObject.put("time", df.format(placeInfo.getTime()));
				result.put(jsonObject);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return result.toString();
	}

	public String findPlaceInfoBySqlTOP(Object... args) {
		String sql = "from PlaceInfo where imei = ? order by time desc";
		List<PlaceInfo> places = placeDao.findPlaceInfoBySqlTop(sql, 1, args);
		JSONArray result = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		try {
			if (places == null || places.size() == 0) {
				jsonObject.put("resultCode", "0");
			} else {
				jsonObject.put("resultCode", "1");
				result.put(jsonObject);
				SimpleDateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				for (PlaceInfo placeInfo : places) {
					jsonObject = new JSONObject();
					jsonObject.put("latitude", placeInfo.getLatitude());
					jsonObject.put("longitude", placeInfo.getLongitude());
					jsonObject.put("accuracy", placeInfo.getAccuracy());
					jsonObject.put("provider", placeInfo.getProvider());
					jsonObject.put("imei", placeInfo.getImei());
					jsonObject.put("time", df.format(placeInfo.getTime()));
					result.put(jsonObject);
				}
			}
			result.put(jsonObject);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	
	public String findPlaceInfoGroupByImei(String imeis) {
		String sql = "select id,imei,provider,longitude,latitude,max(time),accuracy from PlaceInfo group by imei having imei in ( " + imeis +" )";
		List<Object []> places = placeDao.findBySql(sql);
		JSONArray result = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		try {
			if (places == null || places.size() == 0) {
				jsonObject.put("resultCode", "0");
			} else {
				jsonObject.put("resultCode", "1");
				result.put(jsonObject);
				SimpleDateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				for (Object[] item : places) {
					jsonObject = new JSONObject();
					jsonObject.put("latitude", item[4]);
					jsonObject.put("longitude", item[3]);
					jsonObject.put("accuracy", item[6]);
					jsonObject.put("provider", item[2]);
					jsonObject.put("imei", item[1]);
					jsonObject.put("time", df.format(item[5]));
					result.put(jsonObject);
				}
			}
			result.put(jsonObject);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	public static void main(String[] args) {
		/*
		 * String json_str =
		 * "[{\"time\":\"2015-09-11 16:09:32\",\"provider\":\"lbs\",\"longitude\":\"121.420583\",\"latitude\":\"31.291642\",\"accuracy\":\"22.0\",\"imei\":\"357458040515669\"}]"
		 * ; try { // JSONObject json = new JSONObject(json_str); JSONArray
		 * jsonArray = new JSONArray(json_str); JSONObject object = (JSONObject)
		 * jsonArray.get(0); String time = (String) object.get("time");
		 * System.out.println(jsonArray.toString());
		 * 
		 * } catch (JSONException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		// 357458040515669
		send();

		long long_arr[] = new long[] { 43539 };
		for (int i = 0; i < long_arr.length; i++) {
			System.out.println(aaa(long_arr[i]));
		}

	}

	public static String aaa(long a) {
		long b = 5662161376l;
		long c = (a + b) % 6988 + 10000001l;
		return c + "";
	}

	private static void send() {
		try {
			URL url = new URL(
					"http://172.16.2.155:8080/PlaceServer/getLocationsBy.do");
			HttpURLConnection httpConnect = (HttpURLConnection) url
					.openConnection();
			httpConnect.setDoInput(true); // 设置输入采用字符流
			httpConnect.setDoOutput(true); // 设置输出采用字符流
			httpConnect.setRequestMethod("POST");
			httpConnect.setUseCaches(false);// 设置缓存
			httpConnect.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			httpConnect.setRequestProperty("Charset", "UTF-8");

			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					httpConnect.getOutputStream()));
			writer.write("imei=357458040515669,357458040515668");
			writer.flush();
			writer.close();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					httpConnect.getInputStream()));
			String readLine = null;
			StringBuffer result = new StringBuffer();
			while ((readLine = reader.readLine()) != null) {
				result.append(readLine);
			}
			reader.close();
			System.out.println(result);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	

}
