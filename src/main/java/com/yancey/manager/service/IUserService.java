package com.yancey.manager.service;

import java.util.List;
import java.util.Map;

import com.yancey.manager.domain.User;
import com.yancey.manager.domain.UserChannel;
import com.yancey.manager.domain.UserMarket;
import com.yancey.manager.domain.UserRole;
import com.yancey.manager.form.MenuForm;
import com.yancey.manager.form.UserForm;
import com.yancey.manager.util.DataGridModel;

public interface IUserService {

  int saveUser(UserForm form) throws Exception;

  int removeUser(UserForm form) throws Exception;

  int modifyUser(UserForm form) throws Exception;

  User findUser(UserForm form) throws Exception;

  List<User> findUserList(UserForm form) throws Exception;

  Map<String, Object> findUserPageList(DataGridModel page, UserForm form) throws Exception;

  /**
   * 保存用户角色
   * 
   * @param forms
   * @throws Exception
   */
  void saveUserRoles(List<UserRole> forms) throws Exception;

  /**
   * 删除用户角色
   * 
   * @param form
   * @return
   * @throws Exception
   */
  int removeUserRole(UserRole form) throws Exception;

  /**
   * 查询用户角色
   * 
   * @param form
   * @return
   * @throws Exception
   */
  UserRole findUserRole(UserRole form) throws Exception;

  /**
   * 保存用户渠道
   * 
   * @param forms
   * @throws Exception
   */
  void saveUserChannels(List<UserChannel> forms) throws Exception;

  /**
   * 删除用户渠道
   * 
   * @param form
   * @return
   * @throws Exception
   */
  int removeUserChannel(UserChannel form) throws Exception;

  /**
   * 查询用户渠道
   * 
   * @param form
   * @return
   * @throws Exception
   */
  UserChannel findUserChannel(UserChannel form) throws Exception;

  /**
   * 保存用户市场
   * 
   * @param forms
   * @throws Exception
   */
  void saveUserMarkets(List<UserMarket> forms) throws Exception;

  /**
   * 删除用户市场
   * 
   * @param form
   * @return
   * @throws Exception
   */
  int removeUserMarket(UserMarket form) throws Exception;

  /**
   * 查询用户权限
   * 
   * @param form
   * @return
   * @throws Exception
   */
  List<MenuForm> queryMenu(UserForm form) throws Exception;

  List<UserMarket> findUserMarketPindaoList(UserMarket form) throws Exception;

}
