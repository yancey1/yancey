package com.yancey.manager.dao.impl;

import java.util.List;

import com.yancey.manager.dao.IMenuDAO;
import com.yancey.manager.form.MenuForm;

import edu.hziee.common.dbroute.BaseDAO;

public class MenuDAOImpl extends BaseDAO implements IMenuDAO {

  @Override
  public List<?> selectMenuTreeList(MenuForm form) throws Exception {
    return super.queryForList("slh_menu.select_slh_model_page_list", form);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<MenuForm> selectMenuForms(MenuForm form) throws Exception {
    return (List<MenuForm>) super.queryForList("slh_menu.select_slh_model_list", form);
  }

  @Override
  public MenuForm queryMenuForm(MenuForm form) {
    return (MenuForm) super.queryForObject("slh_menu.select_slh_model", form);
  }

  @Override
  public int updateMenuForm(MenuForm form) {
    return super.update("slh_menu.update_slh_model", form);
  }

  @Override
  public int insertMenuForm(MenuForm form) {
    int id = (Integer) super.insert("slh_menu.insert_slh_model", form);
    return id;
  }

  @Override
  public void deleteMenuById(String id) {
    MenuForm form=new MenuForm();
    form.setResourceId(Integer.parseInt(id));
    super.delete("slh_menu.delete_slh_model", form);
  }
}
