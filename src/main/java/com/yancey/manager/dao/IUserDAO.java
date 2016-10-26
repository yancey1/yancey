package com.yancey.manager.dao;

import java.util.List;
import java.util.Map;

import com.yancey.manager.domain.User;
import com.yancey.manager.domain.UserChannel;
import com.yancey.manager.domain.UserMarket;
import com.yancey.manager.domain.UserRole;
import com.yancey.manager.form.MenuForm;
import com.yancey.manager.form.UserForm;
import com.yancey.manager.util.DataGridModel;

public interface IUserDAO {

  /**
   * 保存用户
   * 
   * @param form
   * @return
   * @throws Exception
   */
  int insertUser(UserForm form) throws Exception;

  /**
   * 删除用户信息
   * 
   * @param form
   * @return
   * @throws Exception
   */
  int deleteUser(UserForm form) throws Exception;

  /**
   * 编辑用户
   * 
   * @param form
   * @return
   * @throws Exception
   */
  int updateUser(UserForm form) throws Exception;

  /**
   * 获取指定用户
   * 
   * @param form
   * @return
   * @throws Exception
   */
  User selectUser(UserForm form) throws Exception;

  /**
   * 不带分页的用户列表
   * 
   * @param form
   * @return
   * @throws Exception
   */
  List<User> selectUserList(UserForm form) throws Exception;

  /**
   * 查询用户的权限
   * 
   * @param formUser
   * @return
   * @throws Exception
   */
  List<MenuForm> selectMenuList(UserForm form) throws Exception;

  /**
   * 带分页的用户列表
   * 
   * @param page
   * @param form
   * @return
   * @throws Exception
   */
  Map<String, Object> selectUserPageList(DataGridModel page, UserForm form) throws Exception;

  /**
   * 保存用户的角色
   * 
   * @param form
   * @return
   * @throws Exception
   */
  void batchUserRoles(List<UserRole> forms) throws Exception;

  /**
   * 查询用户的角色
   * 
   * @param form
   * @return
   * @throws Exception
   */
  UserRole selectUserRole(UserRole form) throws Exception;

  /**
   * 删除用户的角色
   * 
   * @param form
   * @return
   * @throws Exception
   */
  int deleteUserRole(UserRole form) throws Exception;

  /**
   * 保存用户的渠道
   * 
   * @param form
   * @return
   * @throws Exception
   */
  void batchUserChannels(List<UserChannel> forms) throws Exception;

  /**
   * 查询用户的渠道
   * 
   * @param form
   * @return
   * @throws Exception
   */
  UserChannel selectUserChannel(UserChannel form) throws Exception;

  /**
   * 删除用户的渠道
   * 
   * @param form
   * @return
   * @throws Exception
   */
  int deleteUserChannel(UserChannel form) throws Exception;

  /**
   * 保存用户的市场
   * 
   * @param form
   * @return
   * @throws Exception
   */
  void batchUserMarkets(List<UserMarket> forms) throws Exception;

  /**
   * 删除用户的市场
   * 
   * @param form
   * @return
   * @throws Exception
   */
  int deleteUserMarket(UserMarket form) throws Exception;

  List<UserMarket> selectUserMarketList(UserMarket form) throws Exception;

}