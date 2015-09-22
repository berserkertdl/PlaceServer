package com.place.db.service;




public interface IPlaceManager {
	public int addPlaces(String placeInfos);

	public String findPlaceInfoBySql(Object... args);
	
	public String findPlaceInfoBySqlTOP(Object... args);

}
