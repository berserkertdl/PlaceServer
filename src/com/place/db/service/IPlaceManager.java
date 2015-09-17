package com.place.db.service;

import java.util.List;

import com.place.entity.PlaceInfo;


public interface IPlaceManager {
	public int addPlaces(String placeInfos);

	public String findPlaceInfoBySql(Object... args);

}
