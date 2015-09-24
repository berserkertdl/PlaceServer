package com.place.db.service;




public interface IPlaceManager {
	public String addPlaces(String placeInfos);

	public String findPlaceInfoBySql(Object... args);
	
	public String findPlaceInfoBySqlTOP(Object... args);
	
	public String findPlaceInfoGroupByImei(String imeis);

}
