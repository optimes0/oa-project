package org.fkjava.menu.service;

import java.util.List;
import java.util.Set;

import org.fkjava.commons.data.domain.Result;
import org.fkjava.menu.domain.Menu;

public interface MenuService {

	void save(Menu menu);

	List<Menu> findTopMenus();

	Result move(String id, String targetId, String moveType);

	Result remove(String id);

	

	Set<String> findMyUrls(String userId);

	List<Menu> findMyMenus(String userId);

}
