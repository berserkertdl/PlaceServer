package com.place.db.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.place.db.service.IPlaceManager;

@Controller
public class TestController {

	private IPlaceManager placeManager;

	public IPlaceManager getPlaceManager() {
		return placeManager;
	}

	@Autowired
	public void setPlaceManager(IPlaceManager placeManager) {
		this.placeManager = placeManager;
	}

	@RequestMapping(value = "/login/{user}", method = RequestMethod.GET)
	public ModelAndView rest(HttpServletRequest request,
			HttpServletResponse response, @PathVariable("user") String user,
			ModelMap modelMap) {
		modelMap.put("loginUser", user);
		return new ModelAndView("/login/hello", modelMap);
	}

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String registPost() {
		return "/welcome";
	}

	@RequestMapping(value = "/location", method = RequestMethod.GET)
	public ModelAndView saveLocation(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws IOException {
		placeManager
				.addPlaces("[{\"time\":\"2015-09-11 16:09:32\",\"provider\":\"lbs\",\"longitude\":\"121.420583\",\"latitude\":\"31.291642\",\"accuracy\":\"22.0\",\"imei\":\"357458040515669\"}]");
		PrintWriter writer = response.getWriter();
		writer.write("saveLocation");
		writer.flush();
		return null;
	}

	@RequestMapping(value = "/locations.do", method = RequestMethod.POST)
	public ModelAndView saveLocations(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws IOException {
		String location_info = request.getParameter("location_info");
		String result = "";
		if (null == location_info || "".equals(location_info)) {
			try {
				JSONArray resultJson = new JSONArray();
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("resultCode", "0");
				resultJson.put(jsonObject);
				result = resultJson.toString();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		} else {
			result = placeManager.addPlaces(location_info);
		}
		PrintWriter writer = response.getWriter();
		writer.write(result + "");
		writer.flush();
		return null;
	}

	@RequestMapping(value = "/getlocations", method = RequestMethod.POST)
	public ModelAndView getLocations(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws IOException {
		response.setCharacterEncoding("UTF-8");
		String imei = request.getParameter("imei");
		String result = placeManager.findPlaceInfoBySql(imei);
		PrintWriter writer = response.getWriter();
		writer.write(result);
		writer.flush();
		return null;
	}
	
	@RequestMapping(value = "/getCurrentLocation", method = RequestMethod.POST)
	public ModelAndView getCurrentLocation(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws IOException {
		response.setCharacterEncoding("UTF-8");
		String imei = request.getParameter("imei");
		String result = placeManager.findPlaceInfoBySqlTOP(imei);
		PrintWriter writer = response.getWriter();
		writer.write(result);
		writer.flush();
		return null;
	}
	
	@RequestMapping(value = "/getLocationsBy", method = RequestMethod.POST)
	public ModelAndView getLocationsBy(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws IOException {
		response.setCharacterEncoding("UTF-8");
		String imei = request.getParameter("imei");
		String result = placeManager.findPlaceInfoGroupByImei(imei);
		PrintWriter writer = response.getWriter();
		writer.write(result);
		writer.flush();
		return null;
	}
	
	

	public static void main(String[] args) {

		URL url = null;
		String url_str = "http://121.43.224.29:8080/PlaceServer/locations.do";
		String result = "";
		try {
			url = new URL(url_str);
			java.net.HttpURLConnection urlConn = (java.net.HttpURLConnection) url
					.openConnection();
			urlConn.setDoInput(true); // 设置输入采用字符流
			urlConn.setDoOutput(true); // 设置输出采用字符流
			urlConn.setRequestMethod("POST");
			urlConn.setUseCaches(false);// 设置缓存
			urlConn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			urlConn.setRequestProperty("Charset", "UTF-8");
			urlConn.connect();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					urlConn.getOutputStream()));
			writer.write("location_info="
					+ "[{\"time\":\"2015-09-11 16:09:32\",\"provider\":\"lbs\",\"longitude\":\"121.420583\",\"latitude\":\"31.291642\",\"accuracy\":\"22.0\",\"imei\":\"357458040515669\"}]");
			writer.flush();
			writer.close();
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()
					));
			String readLine = null;
			while ((readLine = reader.readLine()) != null) {
				result += readLine;
			}
			result = result.trim();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("result:" + result);

	}
}
