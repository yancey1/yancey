package com.yancey.manager.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yancey.manager.dao.IUserDAO;
import com.yancey.manager.domain.User;
import com.yancey.manager.domain.UserChannel;
import com.yancey.manager.domain.UserMarket;
import com.yancey.manager.domain.UserRole;
import com.yancey.manager.form.MenuForm;
import com.yancey.manager.form.UserForm;
import com.yancey.manager.util.DataGridModel;

import edu.hziee.common.dbroute.BaseDAO;

public class UserDAOImpl extends BaseDAO implements IUserDAO {

  @Override
  public int insertUser(UserForm form) throws Exception {
    Object obj = super.insert("slh_user.insert_slh_user", form);
    return obj == null ? 0 : (Integer) obj;
  }

  @Override
  public int deleteUser(UserForm form) throws Exception {
    return super.delete("slh_user.delete_slh_user", form);
  }

  @Override
  public int updateUser(UserForm form) throws Exception {
    return super.update("slh_user.update_slh_user", form);
  }

  @Override
  public User selectUser(UserForm form) throws Exception {
    return (User) super.queryForObject("slh_user.select_slh_user", form);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<User> selectUserList(UserForm form) throws Exception {
    return super.queryForList("slh_user.select_slh_user_list", form);
  }

  @Override
  public Map<String, Object> selectUserPageList(DataGridModel page, UserForm form) throws Exception {
    form = (form == null ? new UserForm() : form);
    form.setPageInfo(page);
    Map<String, Object> results = new HashMap<String, Object>();
    results.put("total", super.queryForCount("slh_user.select_slh_user_page_list_count", form));
    results.put("rows", super.queryForList("slh_user.select_slh_user_page_list", form));
    return results;
  }

  @Override
  public void batchUserRoles(List<UserRole> forms) throws Exception {
    super.batchInsert("slh_user.insert_slh_user_role", forms);
  }

  @Override
  public int deleteUserRole(UserRole form) throws Exception {
    return super.delete("slh_user.delete_slh_user_role", form);
  }

  @Override
  public UserRole selectUserRole(UserRole form) throws Exception {
    return (UserRole) super.queryForObject("slh_user.select_slh_user_role", form);
  }

  @Override
  public void batchUserChannels(List<UserChannel> forms) throws Exception {
    super.batchInsert("slh_user.insert_slh_user_channel", forms);
  }

  @Override
  public UserChannel selectUserChannel(UserChannel form) throws Exception {
    return (UserChannel) super.queryForObject("slh_user.select_slh_user_channel", form);
  }

  @Override
  public int deleteUserChannel(UserChannel form) throws Exception {
    return delete("slh_user.delete_slh_user_channel", form);
  }

  @Override
  public void batchUserMarkets(List<UserMarket> forms) throws Exception {
    super.batchInsert("slh_user.insert_slh_user_market", forms);
  }

  @Override
  public int deleteUserMarket(UserMarket form) throws Exception {
    return delete("slh_user.delete_slh_user_market", form);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<MenuForm> selectMenuList(UserForm form) throws Exception {
    return super.queryForList("slh_user.select_slh_user_menu_list", form);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<UserMarket> selectUserMarketList(UserMarket form) throws Exception {
    return super.queryForList("slh_user.select_slh_user_market_pindao_list", form);
  }

}