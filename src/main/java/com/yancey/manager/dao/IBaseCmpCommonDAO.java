package com.yancey.manager.dao;

import java.util.List;
import java.util.Map;

import com.yancey.manager.common.NameSpaceEnum;
import com.yancey.manager.query.BaseAbstractQuery;

/**
 * 通用DAO接口
 * 
 * */
public interface IBaseCmpCommonDAO {

  /**
   * 查询数据列表
   * 
   * @param query
   *          非空
   * @param nameSpaceEnum
   *          非空
   * 
   * @return List<BaseAbstractQuery>
   * 
   * */
  public List<BaseAbstractQuery> selectEntityList(BaseAbstractQuery query, NameSpaceEnum nameSpaceEnum);

  /**
   * 查询表的数据量
   * 
   * @param query
   *          非空
   * @param nameSpaceEnum
   *          非空
   * @return int
   * 
   * */
  public int selectEntityCount(BaseAbstractQuery query, NameSpaceEnum nameSpaceEnum);

  /**
   * 根据id查询数据
   * 
   * @param id
   * @param nameSpaceEnum
   *          命名空间
   * */
  public BaseAbstractQuery selectEntityById(Integer id, NameSpaceEnum nameSpaceEnum);

  /**
   * 新增
   * 
   * @param entity
   * @param nameSpaceEnum
   * */
  public void insertEntiy(BaseAbstractQuery entity, NameSpaceEnum nameSpaceEnum) throws Exception;

  /**
   * 更新
   * 
   * @param entity
   * @param nameSpaceEnum
   *          命名空间
   * */
  public int updateEntity(BaseAbstractQuery entity, NameSpaceEnum nameSpaceEnum) throws Exception;

  /**
   * 删除
   * 
   * @param id
   * @param nameSpaceEnum
   *          命名空间
   * */
  public int deleteEntity(Integer id, NameSpaceEnum nameSpaceEnum) throws Exception;

  /**
   * 批量新增
   * 
   * */
  public void batchInsertEntity(List<BaseAbstractQuery> entityList, NameSpaceEnum nameSpaceEnum) throws Exception;

  /**
   * 批量更新
   * 
   * */
  public void batchUpdateEntity(List<BaseAbstractQuery> entityList, NameSpaceEnum nameSpaceEnum) throws Exception;

  /**
   * 批量删除
   * 
   * */
  public int batchDeleteEntity(List<Integer> entityList, NameSpaceEnum nameSpaceEnum) throws Exception;

  /**
   * 查询列表（不分页）
   * 
   * @param query
   * @param nameSpaceEnum
   *          命名空间
   * */
  public List<BaseAbstractQuery> selectEntityListWithOutPage(BaseAbstractQuery query, NameSpaceEnum nameSpaceEnum);
  
  /**
   * 根据主键ID查询中间表（部署）
   * 
   * @param query
   * @param nameSpaceEnum
   *          命名空间
   * */
  public List<BaseAbstractQuery> getDeleployEntityList(BaseAbstractQuery query, NameSpaceEnum nameSpaceEnum);
  
  public int getMaxId(NameSpaceEnum nameSpaceEnum);
  
  public void deleteEntityAll(BaseAbstractQuery query, NameSpaceEnum nameSpaceEnum);
  
  public int getMaxOrderNum(NameSpaceEnum nameSpaceEnum);
  
  public void modifyOrderNumBySave(Integer order,NameSpaceEnum nameSpaceEnum);
  
  public void modifyMaxOrderNum(Integer newOrder,Integer oldOrder,NameSpaceEnum nameSpaceEnum);
  
  public void modifyMinOrderNum(Integer newOrder,Integer oldOrder,NameSpaceEnum nameSpaceEnum);
  
  public void modifyOrderNumByDelete(Integer num,NameSpaceEnum nameSpaceEnum);
  
  public int deleteEntityByMap(Map<String,Object> map, NameSpaceEnum nameSpaceEnum) throws Exception;
  
  public List<BaseAbstractQuery> getEntityListById(BaseAbstractQuery query, NameSpaceEnum nameSpaceEnum);
}
