package com.yancey.manager.service.impl;

import java.util.List;
import java.util.Map;

import com.yancey.manager.dao.IRoleDAO;
import com.yancey.manager.domain.Role;
import com.yancey.manager.domain.RoleMenu;
import com.yancey.manager.domain.UserRole;
import com.yancey.manager.form.RoleForm;
import com.yancey.manager.service.IRoleService;
import com.yancey.manager.util.DataGridModel;

public class RoleServiceImpl implements IRoleService {

  private IRoleDAO roleDAO;

  @Override
  public void saveRole(RoleForm form) throws Exception {
    roleDAO.insertRole(form);
  }

  @Override
  public int modifyRole(RoleForm form) throws Exception {
    return roleDAO.updateRole(form);
  }

  @Override
  public Role findRoleById(RoleForm form) throws Exception {
    return roleDAO.queryRole(form);
  }

  public List<Role> findRoleList(RoleForm form) throws Exception {
    return roleDAO.queryRoleList(form);
  }

  @Override
  public Map<String, Object> findRolePageList(DataGridModel page, RoleForm form) throws Exception {
    return roleDAO.selectUserPageList(page, form);
  }

  @Override
  public Role findRoleByName(RoleForm form) throws Exception {
    return roleDAO.queryRole(form);
  }

  @Override
  public void saveRoleMenus(List<RoleMenu> forms) throws Exception {
    roleDAO.batchRoleMenus(forms);
  }

  @Override
  public int removeRoleMenu(RoleMenu form) throws Exception {
    return roleDAO.deleteRoleMenu(form);
  }

  @Override
  public RoleMenu findRoleMenu(RoleMenu form) throws Exception {
    return roleDAO.selectRoleMenu(form);
  }

  public IRoleDAO getRoleDAO() {
    return roleDAO;
  }

  public void setRoleDAO(IRoleDAO roleDAO) {
    this.roleDAO = roleDAO;
  }

  @Override
  public List<UserRole> findUserRoles(Role role) throws Exception {

    return roleDAO.queryUserRoles(role);
  }

  @Override
  public int removeRole(Role form) throws Exception {
    return roleDAO.deleteRole(form);
  }

  @Override
  public void deleteRoleByMenuId(String id) throws Exception {
     roleDAO.deleteRoleByMenuId(id);
  }

}
