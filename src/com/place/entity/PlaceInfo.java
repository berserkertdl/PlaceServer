package com.place.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * User entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "place_info")
public class PlaceInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5764177807155685051L;
	
	
	// Fields
	private int id;
	private String imei;
	private String provider;
	private String longitude;
	private String latitude;
	private String accuracy;
	private Date time;

	// Constructors

	/** default constructor */
	public PlaceInfo() {
	}
	

	public PlaceInfo(int id, String imei, String provider, String longitude,
			String latitude, String accuracy, Date time) {
		super();
		this.id = id;
		this.imei = imei;
		this.provider = provider;
		this.longitude = longitude;
		this.latitude = latitude;
		this.accuracy = accuracy;
		this.time = time;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "imei", length = 50)
	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	@Column(name = "provider", length = 10)
	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	@Column(name = "longitude", length = 50)
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}


	@Column(name = "latitude", length = 50)
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@Column(name = "time")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Column(name = "accuracy", length = 10)
	public String getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}
	

}