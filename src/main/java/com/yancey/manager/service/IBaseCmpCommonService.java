package com.yancey.manager.service;

import java.util.List;
import java.util.Map;

import com.yancey.manager.common.NameSpaceEnum;
import com.yancey.manager.query.BaseAbstractQuery;

/**
 * 基础通用服务接口
 * 
 * @author xuwenqiang
 * 
 *         通用的功能包括（目前仅支持单表）： 1、分页查询列表 2、根据id查询数据 3、新增 4、更新 5、更具id删除 6、批量新增
 *         7、批量更新 8、批量删除
 * */
public interface IBaseCmpCommonService {

  /**
   * 分页查询列表
   * 
   * @param query
   * @param nameSpaceEnum
   *          命名空间
   * */
  public Map<String, Object> getEntityList(BaseAbstractQuery query, NameSpaceEnum nameSpaceEnum);
  
  /**
   * 查询列表（不分页）
   * 
   * @param query
   * @param nameSpaceEnum
   *          命名空间
   * */
  public List<BaseAbstractQuery> getEntityListWithOutPage(BaseAbstractQuery query, NameSpaceEnum nameSpaceEnum);
  

  /**
   * 根据id查询数据
   * 
   * @param id
   * @param nameSpaceEnum
   *          命名空间
   * */
  public BaseAbstractQuery getEntityById(Integer id, NameSpaceEnum nameSpaceEnum);

  /**
   * 新增
   * 
   * @param entity
   * @param nameSpaceEnum
   * */
  public void saveEntiy(BaseAbstractQuery entity, NameSpaceEnum nameSpaceEnum) throws Exception;

  /**
   * 更新
   * 
   * @param entity
   * @param nameSpaceEnum
   *          命名空间
   * */
  public boolean updateEntity(BaseAbstractQuery entity, NameSpaceEnum nameSpaceEnum) throws Exception;

  /**
   * 删除
   * 
   * @param id
   * @param nameSpaceEnum
   *          命名空间
   * */
  public boolean deleteEntity(Integer id, NameSpaceEnum nameSpaceEnum) throws Exception;

  /**
   * 批量新增
   * 
   * */
  public void batchSaveEntity(List<BaseAbstractQuery> entityList, NameSpaceEnum nameSpaceEnum) throws Exception;

  /**
   * 批量更新
   * 
   * */
  public boolean batchUpdateEntity(List<BaseAbstractQuery> entityList, NameSpaceEnum nameSpaceEnum) throws Exception;

  /**
   * 批量删除
   * 
   * */
  public boolean batchDeleteEntity(List<Integer> idList, NameSpaceEnum nameSpaceEnum) throws Exception;
  
  /**
   * 根据主键ID查询中间表（部署）
   * 
   * @param query
   * @param nameSpaceEnum
   *          命名空间
   * */
  public List<BaseAbstractQuery> getDeleployEntityList(BaseAbstractQuery query, NameSpaceEnum nameSpaceEnum);
  
  /**
   * 判断对象是否被引用
   * 
   * @param id
   * */
  public boolean isBeUsed(BaseAbstractQuery query, NameSpaceEnum nameSpaceEnum);
  
  public int getMaxId(NameSpaceEnum nameSpaceEnum);
  
  public void deleteEntityAll(BaseAbstractQuery query, NameSpaceEnum nameSpaceEnum);
  
  public boolean deleteEntityByMap(Map<String,Object> map, NameSpaceEnum nameSpaceEnum) throws Exception;
  
  public List<BaseAbstractQuery> getEntityListById(BaseAbstractQuery query, NameSpaceEnum nameSpaceEnum);
}
