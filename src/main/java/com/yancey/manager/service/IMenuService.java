package com.yancey.manager.service;

import java.util.List;

import com.yancey.manager.form.MenuForm;

public interface IMenuService {

  public MenuForm findMenuById(MenuForm form) throws Exception;

  public void modifyMenu(MenuForm form) throws Exception;

  public MenuForm findMenuByName(MenuForm form) throws Exception;

  public List<?> findMenuTreeList(MenuForm form) throws Exception;

  public List<MenuForm> findMenuForms(MenuForm form) throws Exception;

  public void saveMenu(MenuForm form) throws Exception;
  
  public void deleteMenuById(String id) throws Exception;
}
