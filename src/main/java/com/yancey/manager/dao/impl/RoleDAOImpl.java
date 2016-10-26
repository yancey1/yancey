package com.yancey.manager.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yancey.manager.dao.IRoleDAO;
import com.yancey.manager.domain.Role;
import com.yancey.manager.domain.RoleMenu;
import com.yancey.manager.domain.UserRole;
import com.yancey.manager.form.RoleForm;
import com.yancey.manager.util.DataGridModel;

import edu.hziee.common.dbroute.BaseDAO;

public class RoleDAOImpl extends BaseDAO implements IRoleDAO {

  @Override
  public Map<String, Object> selectUserPageList(DataGridModel page, RoleForm form) throws Exception {
    form = (form == null ? new RoleForm() : form);
    form.setPageInfo(page);
    Map<String, Object> results = new HashMap<String, Object>();
    results.put("total", super.queryForCount("slh_role.query_slh_count", form));
    results.put("rows", super.queryForList("slh_role.select_slh_model_page_list", form));
    return results;
  }

  @Override
  public void batchRoleMenus(List<RoleMenu> forms) throws Exception {
    super.batchInsert("slh_role.insert_slh_role_menu", forms);

  }

  @Override
  public RoleMenu selectRoleMenu(RoleMenu form) throws Exception {
    return (RoleMenu) super.queryForObject("slh_role.select_slh_role_menu", form);
  }

  @Override
  public int deleteRoleMenu(RoleMenu form) throws Exception {
    return super.delete("slh_role.delete_slh_role_menu", form);
  }

  @Override
  public int insertRole(RoleForm form) {
    int id = (Integer) super.insert("slh_role.insert_slh_model", form);
    return id;
  }

  @Override
  public int updateRole(RoleForm form) {
    return super.update("slh_role.update_slh_model", form);
  }

  @Override
  public Role queryRole(RoleForm form) {
    return (Role) super.queryForObject("slh_role.select_slh_model", form);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Role> queryRoleList(RoleForm form) {
    return super.queryForList("slh_role.select_slh_model_list", form);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<UserRole> queryUserRoles(Role form) {

    return super.queryForList("slh_role.select_slh_user_role_list", form);
  }

  @Override
  public int deleteRole(Role form) throws Exception {
    return super.delete("slh_role.delete_slh_model", form);
  }

  @Override
  public void deleteRoleByMenuId(String id) {
    RoleMenu roMe=new RoleMenu();
    roMe.setResourceId(Integer.parseInt(id));
    super.delete("slh_role.deleteRoleByMenuId", roMe);
  }

}
