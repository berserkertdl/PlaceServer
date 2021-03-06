package com.place.db.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.place.db.dao.IPlaceDAO;
import com.place.entity.PlaceInfo;

@Repository
public class PlaceDAOImpl extends BaseDAO implements IPlaceDAO {
	
	public int addPlaces(List<PlaceInfo> placeInfos) {
		int flag = 1;
		try {
			getHibernateTemplate().saveOrUpdateAll(placeInfos);
		} catch (Exception e) {
			flag = 0;
		}
		return flag;
	}


	@SuppressWarnings("unchecked")
	public List<PlaceInfo> findPlaceInfoBySql(String sql, Object... args) {
		List<PlaceInfo> list = null;
		try {
			list = getHibernateTemplate().find(sql, args);
		} catch (Exception e) {
			
		}
		return list;
	}


	@SuppressWarnings("unchecked")
	public List<PlaceInfo> findPlaceInfoBySqlTop(String sql,int maxResults, Object... args) {
		List<PlaceInfo> list = null;
		try {
			HibernateTemplate hibernateTemplate = getHibernateTemplate();
//			hibernateTemplate.setMaxResults(maxResults);
			list = hibernateTemplate.find(sql, args);
		} catch (Exception e) {
			
		}
		return list;
	}


	@SuppressWarnings("unchecked")
	public List<Object[]> findBySql(String sql, Object... args) {
		List<Object[]> list = null;
		try {
			list = (List<Object[]>)getHibernateTemplate().find(sql, args);
		} catch (Exception e) {
			
		}
		return list;
	}

}
