package com.yancey.manager.service.impl.log;

import org.springframework.stereotype.Service;

import com.yancey.manager.annotation.Action;

@Service("appUserAction")
public class AppUserAction {
  /**
   * 添加及保存操作
   */
  @Action(description = "添加或保存用户信息")
  public String save() {
    System.out.println("here is save()");
    return null;
  }
  /**
   * 修改密码
   * 
   * @return
   */
  @Action(description = "修改密码")
  public String resetPassword() {
    System.out.println("here is resetPassword()");
    return null;
  }

  @Action(description = "test")
  public String test() {
    System.out.println("test");
    return null;
  }

}
