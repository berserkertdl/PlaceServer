package com.place.db.dao;

import java.util.List;

import com.place.entity.PlaceInfo;


public interface IPlaceDAO {

	public int addPlaces(List<PlaceInfo> placeInfos);
	
	public List<PlaceInfo> findPlaceInfoBySql(String sql, Object ...args);
	
	public List<PlaceInfo> findPlaceInfoBySqlTop(String sql,int maxResults, Object ...args);
	
}
