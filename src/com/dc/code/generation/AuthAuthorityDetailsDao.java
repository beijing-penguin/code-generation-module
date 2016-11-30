package com.dc.code.generation;

import com.yanxiu.jdbc.core.DBHelper;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import java.util.List;

import java.util.ArrayList;

@Component
public class AuthAuthorityDetailsDao {
	@Resource
	private DBHelper frameworkDBHelper;

	public Page selectListAuthAuthorityDetailsByPage(Page page) throws Exception{
		if(page == null) {
			page = new Page();
		}
		String sql = "select * from " +AuthAuthorityDetails.TABLE_NAME;
		page.setPageCount(frameworkDBHelper.selectCount(sql));
		sql = sql + "limit ?,?";
		List<Object> paramsList = new ArrayList<Object>();
		paramsList.add(page.getFirstIndex());
		paramsList.add(page.getPageSize());
		page.setDataList(frameworkDBHelper.selectList(sql,AuthAuthorityDetails.class,paramsList));
		return page;
	}
	public List<AuthAuthorityDetails> selectListAuthAuthorityDetails(AuthAuthorityDetails authAuthorityDetails) throws Exception{
		return frameworkDBHelper.selectEntity(authAuthorityDetails);
	}
	public AuthAuthorityDetails selectOneAuthAuthorityDetails(AuthAuthorityDetails authAuthorityDetails) throws Exception{
		return frameworkDBHelper.selectOneEntity(authAuthorityDetails);
	}
	public int deleteAuthAuthorityDetails(AuthAuthorityDetails authAuthorityDetails) throws Exception{
		return frameworkDBHelper.deleteEntity(authAuthorityDetails);
	}
	public int updateAuthAuthorityDetails(AuthAuthorityDetails authAuthorityDetails) throws Exception{
		return frameworkDBHelper.updateEntity(authAuthorityDetails);
	}
	public int insertAuthAuthorityDetails(AuthAuthorityDetails authAuthorityDetails) throws Exception{
		return frameworkDBHelper.insertEntity(authAuthorityDetails);
	}
}