package com.fixit.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fixit.dao.QueryFixersDao;
import com.fixit.domain.vo.Query;
import com.fixit.domain.vo.QueryFixers;
import com.fixit.domain.vo.User;
import com.fixit.utility.FixitException;

@Service("QueryFixersService")
@Transactional
public class QueryFixersServiceImpl implements QueryFixersService {

	@Autowired
	private QueryFixersDao queryFixerDao;

	@Override
	@Transactional
	public void saveQueryFixers(Set<User> fixers, Query query) throws FixitException {
		
		QueryFixers queryFixer;
		if (fixers != null) {
			for (User u : fixers) {
				queryFixer = new QueryFixers();
				queryFixer.setQuery(query);
				queryFixer.setUser(u);
				try {
					queryFixerDao.store(queryFixer);
				} catch (Exception e) {

				}
			}
		}

	}

	@Override
	@Transactional
	public Map<String, String> getQueryFixersIdsAndNamesBasedOnQueryId(Integer queryId, int startIndex, int maxResult)
			throws FixitException {
		Map<String, String> idAndNamesMap = new HashMap<>();
		String ids = "";
		String names = "";
		List<QueryFixers> selectedFixerSet = new LinkedList<QueryFixers>();
		try {
			selectedFixerSet = queryFixerDao.getQueryFixersBasedOnQueryId(queryId, startIndex, maxResult);
			if (selectedFixerSet != null && selectedFixerSet.size() > 0) {
				idAndNamesMap.put("selectedFixers", "true");
			
			for (int i = 0; i < selectedFixerSet.size(); i++) {

				User mySelectedFixer = selectedFixerSet.get(i).getUser();
				if (i == 0) {
					ids += "[" + mySelectedFixer.getUserId();
					names += "[" + "\'" + mySelectedFixer.getUserName() + "\'";
					if(selectedFixerSet.size()==1){
						ids+="]";
						names+="]";
					}
				} else if (i == (selectedFixerSet.size() - 1)) {
					ids += "" + "," + mySelectedFixer.getUserId() + "]";
					names += "," + "\'" + mySelectedFixer.getUserName() + "\'" + "]";
				} else {
					ids += "" + "," + mySelectedFixer.getUserId();
					names += "," + "\'" + mySelectedFixer.getUserName() + "\'";
				}

			}
			idAndNamesMap.put("fixersId", ids);
			idAndNamesMap.put("fixersNames", names);
			}else{
				 ids = "[]";
				 names = "[]";
				 idAndNamesMap.put("fixersId", ids);
					idAndNamesMap.put("fixersNames", names);
			}
			
		} catch (Exception e) {
			return null;
		}
		return idAndNamesMap;
	}
	
	@Override
	@Transactional
	public Map<String, String> getQueryFixersIdsAndNamesBasedOnQueryIdNoBrackets(Integer queryId, int startIndex, int maxResult)
			throws FixitException {
		Map<String, String> idAndNamesMap = new HashMap<>();
		String ids = "";
		String names = "";
		List<QueryFixers> selectedFixerSet = new LinkedList<QueryFixers>();
		try {
			selectedFixerSet = queryFixerDao.getQueryFixersBasedOnQueryId(queryId, startIndex, maxResult);
			if (selectedFixerSet != null && selectedFixerSet.size() > 0) {
				idAndNamesMap.put("selectedFixers", "true");
			
			for (int i = 0; i < selectedFixerSet.size(); i++) {

				User mySelectedFixer = selectedFixerSet.get(i).getUser();
				if (i == 0) {
					ids += "" + mySelectedFixer.getUserId();
					names += "" + "" + mySelectedFixer.getUserName() + "";
					if(selectedFixerSet.size()==1){
						ids+="";
						names+="";
					}
				} else if (i == (selectedFixerSet.size() - 1)) {
					ids += "" + "," + mySelectedFixer.getUserId() + "";
					names += "," + "" + mySelectedFixer.getUserName() + "" + "";
				} else {
					ids += "" + "," + mySelectedFixer.getUserId();
					names += "," + "" + mySelectedFixer.getUserName() + "";
				}

			}
			idAndNamesMap.put("fixersId", ids);
			idAndNamesMap.put("fixersNames", names);
			}else{
				 ids = "";
				 names = "";
				 idAndNamesMap.put("fixersId", ids);
					idAndNamesMap.put("fixersNames", names);
			}
			
		} catch (Exception e) {
			return null;
		}
		return idAndNamesMap;
	}

	@Override
	@Transactional
	public List<QueryFixers> getQueryFixersBasedOnQueryId(Integer queryId,int startIndex , int maxResult) throws FixitException{
		List<QueryFixers> selectedFixerSet = new LinkedList<QueryFixers>();
		try{
			selectedFixerSet = queryFixerDao.getQueryFixersBasedOnQueryId(queryId, startIndex, maxResult);
			return selectedFixerSet;
		}catch(Exception e){
			return null;
		}
	}
@Override
@Transactional
	public Integer deleteQueryFixersBasedOnQueryId(Integer queryId) throws FixitException{
		try{
			return queryFixerDao.deleteQueryFixersBasedOnQueryId(queryId);
		}catch(Exception e){
			return -1;
		}
		
	}
@Override
@Transactional
public QueryFixers findQueryFixersBasedOnQueryIdAndFixerId(Integer queryId,Integer userId,int startIndex , int maxResult) throws FixitException{
	try{
		return queryFixerDao.findQueryFixersBasedOnQueryIdAndFixerId(queryId, userId, startIndex, maxResult);
	}catch(Exception e){
		return null;
	}
}


}
