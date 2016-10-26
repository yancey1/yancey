package com.yancey.manager.service;

import java.util.List;
import java.util.Map;

import com.yancey.manager.domain.Role;
import com.yancey.manager.domain.RoleMenu;
import com.yancey.manager.domain.UserRole;
import com.yancey.manager.form.RoleForm;
import com.yancey.manager.util.DataGridModel;

public interface IRoleService {

  public void saveRole(RoleForm form) throws Exception;

  public int modifyRole(RoleForm form) throws Exception;

  public Role findRoleById(RoleForm form) throws Exception;

  public Role findRoleByName(RoleForm form) throws Exception;

  public List<Role> findRoleList(RoleForm form) throws Exception;

  public Map<String, Object> findRolePageList(DataGridModel page, RoleForm form) throws Exception;

  public int removeRole(Role form) throws Exception;

  public void saveRoleMenus(List<RoleMenu> forms) throws Exception;

  public int removeRoleMenu(RoleMenu form) throws Exception;

  public RoleMenu findRoleMenu(RoleMenu form) throws Exception;

  public List<UserRole> findUserRoles(Role role) throws Exception;
  
  public void deleteRoleByMenuId(String id) throws Exception;
}
