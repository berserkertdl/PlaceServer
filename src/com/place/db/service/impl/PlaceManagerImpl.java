package com.place.db.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

	public int addPlaces(String placeInfos) {
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

		return flag;
	}

	public String findPlaceInfoBySql(Object... args) {
		String sql = "select * from place_info where imei = ? order by time desc";
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

	public static void main(String[] args) {
		String json_str = "[{\"time\":\"2015-09-11 16:09:32\",\"provider\":\"lbs\",\"longitude\":\"121.420583\",\"latitude\":\"31.291642\",\"accuracy\":\"22.0\",\"imei\":\"357458040515669\"}]";
		try {
			// JSONObject json = new JSONObject(json_str);
			JSONArray jsonArray = new JSONArray(json_str);
			JSONObject object = (JSONObject) jsonArray.get(0);
			String time = (String) object.get("time");
			System.out.println(jsonArray.toString());

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
