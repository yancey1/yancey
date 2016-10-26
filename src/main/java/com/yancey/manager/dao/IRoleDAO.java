package com.yancey.manager.dao;

import java.util.List;
import java.util.Map;

import com.yancey.manager.domain.Role;
import com.yancey.manager.domain.RoleMenu;
import com.yancey.manager.domain.UserRole;
import com.yancey.manager.form.RoleForm;
import com.yancey.manager.util.DataGridModel;

public interface IRoleDAO {

  /**
   * 查询分页内容
   * 
   * @param page
   * @param form
   * @return
   * @throws Exception
   */
  public Map<String, Object> selectUserPageList(DataGridModel page, RoleForm form) throws Exception;

  /**
   * 保存角色的权限
   * 
   * @param form
   * @return
   * @throws Exception
   */
  void batchRoleMenus(List<RoleMenu> forms) throws Exception;

  /**
   * 查询角色的权限
   * 
   * @param form
   * @return
   * @throws Exception
   */
  RoleMenu selectRoleMenu(RoleMenu form) throws Exception;

  /**
   * 删除角色
   * 
   * @param form
   * @return
   * @throws Exception
   */
  int deleteRole(Role form) throws Exception;

  /**
   * 删除角色的权限
   * 
   * @param form
   * @return
   * @throws Exception
   */
  int deleteRoleMenu(RoleMenu form) throws Exception;

  int insertRole(RoleForm form);

  int updateRole(RoleForm form);

  Role queryRole(RoleForm form);

  List<Role> queryRoleList(RoleForm form);

  List<UserRole> queryUserRoles(Role role);
  
  public void deleteRoleByMenuId(String id);
  
}
