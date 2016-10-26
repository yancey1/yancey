package com.yancey.manager.service.impl;

import java.util.List;
import java.util.Map;

import com.yancey.manager.dao.IUserDAO;
import com.yancey.manager.domain.User;
import com.yancey.manager.domain.UserChannel;
import com.yancey.manager.domain.UserMarket;
import com.yancey.manager.domain.UserRole;
import com.yancey.manager.form.MenuForm;
import com.yancey.manager.form.UserForm;
import com.yancey.manager.service.IUserService;
import com.yancey.manager.util.DataGridModel;

public class UserServiceImpl implements IUserService {

  private IUserDAO userDAO;

  @Override
  public int saveUser(UserForm form) throws Exception {
    return userDAO.insertUser(form);
  }

  @Override
  public int removeUser(UserForm form) throws Exception {
     return userDAO.deleteUser(form);
  }

  @Override
  public int modifyUser(UserForm form) throws Exception {
    return userDAO.updateUser(form);
  }

  @Override
  public User findUser(UserForm form) throws Exception {
    return userDAO.selectUser(form);
  }

  @Override
  public List<User> findUserList(UserForm form) throws Exception {
    return userDAO.selectUserList(form);
  }

  @Override
  public Map<String, Object> findUserPageList(DataGridModel page, UserForm form) throws Exception {
    return userDAO.selectUserPageList(page, form);
  }

  @Override
  public int removeUserRole(UserRole form) throws Exception {
    return userDAO.deleteUserRole(form);
  }

  @Override
  public UserRole findUserRole(UserRole form) throws Exception {
    return userDAO.selectUserRole(form);
  }

  @Override
  public void saveUserRoles(List<UserRole> forms) throws Exception {
    userDAO.batchUserRoles(forms);
  }

  @Override
  public void saveUserChannels(List<UserChannel> forms) throws Exception {
    userDAO.batchUserChannels(forms);
  }

  @Override
  public int removeUserChannel(UserChannel form) throws Exception {
    return userDAO.deleteUserChannel(form);
  }
  @Override
  public UserChannel findUserChannel(UserChannel form) throws Exception {

    return userDAO.selectUserChannel(form);
  }

  @Override
  public void saveUserMarkets(List<UserMarket> forms) throws Exception {
    userDAO.batchUserMarkets(forms);
  }

  @Override
  public int removeUserMarket(UserMarket form) throws Exception {
    return userDAO.deleteUserMarket(form);
  }

  @Override
  public List<MenuForm> queryMenu(UserForm form) throws Exception {
    return userDAO.selectMenuList(form);
  }

  public IUserDAO getUserDAO() {
    return userDAO;
  }

  public void setUserDAO(IUserDAO userDAO) {
    this.userDAO = userDAO;
  }

  @Override
  public List<UserMarket> findUserMarketPindaoList(UserMarket form) throws Exception {
    return userDAO.selectUserMarketList(form);
  }

}
