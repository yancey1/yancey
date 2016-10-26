package com.yancey.manager.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yancey.manager.common.ConstantsCMP;
import com.yancey.manager.common.NameSpaceEnum;
import com.yancey.manager.dao.IBaseCmpCommonDAO;
import com.yancey.manager.query.BaseAbstractQuery;

import edu.hziee.common.dbroute.BaseDAO;

/**
 * 通用DAO接口实现
 * 
 * */
@SuppressWarnings("unchecked")
public class BaseCmpCommonDAOImpl extends BaseDAO implements IBaseCmpCommonDAO {

  @Override
  public List<BaseAbstractQuery> selectEntityList(BaseAbstractQuery query, NameSpaceEnum nameSpaceEnum) {
    return super.queryForList(nameSpaceEnum.getNameSpace() + ConstantsCMP.POINT + "SELECT_ENTITY_LIST", query);
  }

  @Override
  public int selectEntityCount(BaseAbstractQuery query, NameSpaceEnum nameSpaceEnum) {
    return super.queryForCount(nameSpaceEnum.getNameSpace() + ConstantsCMP.POINT + "SELECT_ENTITY_COUNT", query);
  }

  @Override
  public BaseAbstractQuery selectEntityById(Integer id, NameSpaceEnum nameSpaceEnum) {
    return (BaseAbstractQuery) super.queryForObject(nameSpaceEnum.getNameSpace() + ConstantsCMP.POINT + "SELECT_ENTITY_BY_ID", id);
  }

  @Override
  public void insertEntiy(BaseAbstractQuery entity, NameSpaceEnum nameSpaceEnum) throws Exception {
    super.insert(nameSpaceEnum.getNameSpace() + ConstantsCMP.POINT + "INSERT_ENTITY", entity);
  }

  @Override
  public int updateEntity(BaseAbstractQuery entity, NameSpaceEnum nameSpaceEnum) throws Exception {
    return super.update(nameSpaceEnum.getNameSpace() + ConstantsCMP.POINT + "UPDATE_ENTITY", entity);
  }

  @Override
  public int deleteEntity(Integer id, NameSpaceEnum nameSpaceEnum) throws Exception {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("id", id);
    return super.delete(nameSpaceEnum.getNameSpace() + ConstantsCMP.POINT + "DELETE_ENTITY_BY_ID", map);
  }

  @Override
  public void batchInsertEntity(List<BaseAbstractQuery> entityList, NameSpaceEnum nameSpaceEnum) throws Exception {
    super.batchInsert(nameSpaceEnum.getNameSpace() + ConstantsCMP.POINT + "BATCH_INSERT_ENTITY", entityList);
  }

  @Override
  public void batchUpdateEntity(List<BaseAbstractQuery> entityList, NameSpaceEnum nameSpaceEnum) throws Exception {
    super.batchUpdate(nameSpaceEnum.getNameSpace() + ConstantsCMP.POINT + "BATCH_UPDATE_ENTITY", entityList);
  }

  @Override
  public int batchDeleteEntity(List<Integer> idList, NameSpaceEnum nameSpaceEnum) throws Exception {
    for (Integer id : idList) {
      int ret = this.deleteEntity(id, nameSpaceEnum);
      if (ret <= 0) {
        return ConstantsCMP.OPERATE_FAILD;
      }
    }

    return ConstantsCMP.OPERATE_SUCCESS;
  }

  @Override
  public List<BaseAbstractQuery> selectEntityListWithOutPage(BaseAbstractQuery query, NameSpaceEnum nameSpaceEnum) {
    return super.queryForList(nameSpaceEnum.getNameSpace() + ConstantsCMP.POINT + "SELECT_ENTITY_LIST_WITH_OUT_PAGE", query);
  }

  @Override
  public List<BaseAbstractQuery> getDeleployEntityList(BaseAbstractQuery query, NameSpaceEnum nameSpaceEnum) {
    return super.queryForList(nameSpaceEnum.getNameSpace() + ConstantsCMP.POINT + "GET_DELEPLOY_ENTITY_LIST", query);
  }

  @Override
  public int getMaxId(NameSpaceEnum nameSpaceEnum) {
    return super.queryForCount(nameSpaceEnum.getNameSpace() + ConstantsCMP.POINT + "getMaxId");
  }

  @Override
  public void deleteEntityAll(BaseAbstractQuery query, NameSpaceEnum nameSpaceEnum) {
    super.delete(nameSpaceEnum.getNameSpace() + ConstantsCMP.POINT + "deleteEntityAll", query);
  }

  @Override
  public int getMaxOrderNum(NameSpaceEnum nameSpaceEnum) {
    return (Integer) super.queryForObject(nameSpaceEnum.getNameSpace() + ConstantsCMP.POINT + "getMaxOrderNum");
  }

  @Override
  public void modifyOrderNumBySave(Integer order, NameSpaceEnum nameSpaceEnum) {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("oldOrder", order);
    super.update(nameSpaceEnum.getNameSpace() + ConstantsCMP.POINT + "modifyOrderNumBySave", map);
  }

  @Override
  public void modifyMaxOrderNum(Integer newOrder, Integer oldOrder, NameSpaceEnum nameSpaceEnum) {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("newOrder", newOrder);
    map.put("oldOrder", oldOrder);
    super.update(nameSpaceEnum.getNameSpace() + ConstantsCMP.POINT + "modifyMaxOrderNum", map);
  }

  @Override
  public void modifyMinOrderNum(Integer newOrder, Integer oldOrder, NameSpaceEnum nameSpaceEnum) {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("newOrder", newOrder);
    map.put("oldOrder", oldOrder);
    super.update(nameSpaceEnum.getNameSpace() + ConstantsCMP.POINT + "modifyMinOrderNum", map);
  }

  @Override
  public void modifyOrderNumByDelete(Integer num, NameSpaceEnum nameSpaceEnum) {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("num", num);
    super.update(nameSpaceEnum.getNameSpace()+ ConstantsCMP.POINT + "modifyOrderNumByDelete", map);
  }

  @Override
  public int deleteEntityByMap(Map<String, Object> map, NameSpaceEnum nameSpaceEnum) throws Exception {
    return super.delete(nameSpaceEnum.getNameSpace()+ ConstantsCMP.POINT +"deleteObjByMap", map);
  }
  
  @Override
  public List<BaseAbstractQuery> getEntityListById(BaseAbstractQuery query, NameSpaceEnum nameSpaceEnum) {
    return super.queryForList(nameSpaceEnum.getNameSpace() + ConstantsCMP.POINT + "select_entity_by_getid", query);
  }
}