package com.dc.code.generation;

import java.util.List;

public interface AuthAuthorityDetailsService {
	public Page selectListAuthAuthorityDetailsByPage(Page page) throws Exception;
	public List<AuthAuthorityDetails> selectListAuthAuthorityDetails(AuthAuthorityDetails authAuthorityDetails) throws Exception;
	public AuthAuthorityDetails selectOneAuthAuthorityDetails(AuthAuthorityDetails authAuthorityDetails) throws Exception;
	public int deleteAuthAuthorityDetails(AuthAuthorityDetails authAuthorityDetails) throws Exception;
	public int updateAuthAuthorityDetails(AuthAuthorityDetails authAuthorityDetails) throws Exception;
	public int insertAuthAuthorityDetails(AuthAuthorityDetails authAuthorityDetails) throws Exception;
}