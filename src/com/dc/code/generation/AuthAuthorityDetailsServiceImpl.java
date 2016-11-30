package com.dc.code.generation;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

import java.util.ArrayList;

@Service
public class AuthAuthorityDetailsServiceImpl implements AuthAuthorityDetailsService{
	@Resource
	private AuthAuthorityDetailsDao authAuthorityDetailsDao;

	@Override
	public Page selectListAuthAuthorityDetailsByPage(Page page) throws Exception{
		return authAuthorityDetailsDao.selectListAuthAuthorityDetailsByPage(page);
	}
	@Override
	public List<AuthAuthorityDetails> selectListAuthAuthorityDetails(AuthAuthorityDetails authAuthorityDetails) throws Exception{
		return authAuthorityDetailsDao.selectListAuthAuthorityDetails(authAuthorityDetails);
	}
	@Override
	public AuthAuthorityDetails selectOneAuthAuthorityDetails(AuthAuthorityDetails authAuthorityDetails) throws Exception{
		return authAuthorityDetailsDao.selectOneAuthAuthorityDetails(authAuthorityDetails);
	}
	@Override
	public int deleteAuthAuthorityDetails(AuthAuthorityDetails authAuthorityDetails) throws Exception{
		return authAuthorityDetailsDao.deleteAuthAuthorityDetails(authAuthorityDetails);
	}
	@Override
	public int updateAuthAuthorityDetails(AuthAuthorityDetails authAuthorityDetails) throws Exception{
		return authAuthorityDetailsDao.updateAuthAuthorityDetails(authAuthorityDetails);
	}
	@Override
	public int insertAuthAuthorityDetails(AuthAuthorityDetails authAuthorityDetails) throws Exception{
		return authAuthorityDetailsDao.insertAuthAuthorityDetails(authAuthorityDetails);
	}
}