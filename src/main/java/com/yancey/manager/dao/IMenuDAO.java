package com.yancey.manager.dao;

import java.util.List;

import com.yancey.manager.form.MenuForm;

public interface IMenuDAO {
  /**
   * 查询分页内容
   * 
   * @param page
   * @param form
   * @return
   * @throws Exception
   */
  public List<?> selectMenuTreeList(MenuForm form) throws Exception;

  public List<MenuForm> selectMenuForms(MenuForm form) throws Exception;

  MenuForm queryMenuForm(MenuForm form);

  int updateMenuForm(MenuForm form);

  int insertMenuForm(MenuForm form);
  
  public void deleteMenuById(String id);
  
}
