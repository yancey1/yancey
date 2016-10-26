package com.yancey.manager.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yancey.manager.common.NameSpaceEnum;
import com.yancey.manager.dao.IBaseCmpCommonDAO;
import com.yancey.manager.query.BaseAbstractQuery;
import com.yancey.manager.service.IBaseCmpCommonService;

/**
 * 基础通用服务接口实现
 * 
 * @author joseph
 * */
public class BaseCmpCommonServiceImpl implements IBaseCmpCommonService {

  private IBaseCmpCommonDAO baseCmpCommonDAO;

  public void setBaseCmpCommonDAO(IBaseCmpCommonDAO baseCmpCommonDAO) {
    this.baseCmpCommonDAO = baseCmpCommonDAO;
  }

  @Override
  public Map<String, Object> getEntityList(BaseAbstractQuery query, NameSpaceEnum nameSpaceEnum) {
    Map<String, Object> map = new HashMap<String, Object>();
    if (query == null || nameSpaceEnum == null) {
      return map;
    }

    List<BaseAbstractQuery> list = baseCmpCommonDAO.selectEntityList(query, nameSpaceEnum);
    int count = baseCmpCommonDAO.selectEntityCount(query, nameSpaceEnum);
    map.put("rows", list);
    map.put("total", count);

    return map;
  }

  @Override
  public BaseAbstractQuery getEntityById(Integer id, NameSpaceEnum nameSpaceEnum) {
    if (id == null || nameSpaceEnum == null) {
      return null;
    }

    return baseCmpCommonDAO.selectEntityById(id, nameSpaceEnum);
  }

  @Override
  public void saveEntiy(BaseAbstractQuery entity, NameSpaceEnum nameSpaceEnum) throws Exception {
    if (entity == null || nameSpaceEnum == null) {
      return;
    }

    baseCmpCommonDAO.insertEntiy(entity, nameSpaceEnum);
  }

  @Override
  public boolean updateEntity(BaseAbstractQuery entity, NameSpaceEnum nameSpaceEnum) throws Exception {
    if (entity == null || nameSpaceEnum == null) {
      return false;
    }

    int ret = baseCmpCommonDAO.updateEntity(entity, nameSpaceEnum);
    if (ret > 0) {
      return true;
    }

    return false;
  }

  @Override
  public boolean deleteEntity(Integer id, NameSpaceEnum nameSpaceEnum) throws Exception {
    if (id == null || nameSpaceEnum == null) {
      return false;
    }

    int ret = baseCmpCommonDAO.deleteEntity(id, nameSpaceEnum);
    if (ret > 0) {
      return true;
    }

    return false;
  }

  @Override
  public void batchSaveEntity(List<BaseAbstractQuery> entityList, NameSpaceEnum nameSpaceEnum) throws Exception {
    if (entityList == null || entityList.isEmpty() || nameSpaceEnum == null) {
      return;
    }

    baseCmpCommonDAO.batchInsertEntity(entityList, nameSpaceEnum);
  }

  @Override
  public boolean batchUpdateEntity(List<BaseAbstractQuery> entityList, NameSpaceEnum nameSpaceEnum) throws Exception {
    if (entityList == null || entityList.isEmpty() || nameSpaceEnum == null) {
      return false;
    }

    baseCmpCommonDAO.batchUpdateEntity(entityList, nameSpaceEnum);

    return true;
  }

  @Override
  public boolean batchDeleteEntity(List<Integer> idList, NameSpaceEnum nameSpaceEnum) throws Exception {
    if (idList == null || idList.isEmpty() || nameSpaceEnum == null) {
      return false;
    }

    int ret = baseCmpCommonDAO.batchDeleteEntity(idList, nameSpaceEnum);
    if (ret > 0) {
      return true;
    }

    return false;
  }

  @Override
  public List<BaseAbstractQuery> getEntityListWithOutPage(BaseAbstractQuery query, NameSpaceEnum nameSpaceEnum) {
    if (nameSpaceEnum == null) {
      return null;
    }
    
    return baseCmpCommonDAO.selectEntityListWithOutPage(query, nameSpaceEnum);
  }

  @Override
  public List<BaseAbstractQuery> getDeleployEntityList(BaseAbstractQuery query, NameSpaceEnum nameSpaceEnum) {
    if (nameSpaceEnum == null) {
      return null;
    }
    
    return baseCmpCommonDAO.getDeleployEntityList(query, nameSpaceEnum);
  }

  @Override
  public boolean isBeUsed(BaseAbstractQuery query, NameSpaceEnum nameSpaceEnum) {
    int count = baseCmpCommonDAO.selectEntityCount(query, nameSpaceEnum);
    if(count>0){
      return true;
    }
    return false;
  }

  @Override
  public int getMaxId(NameSpaceEnum nameSpaceEnum) {
    return baseCmpCommonDAO.getMaxId(nameSpaceEnum);
  }
  
  @Override
  public void deleteEntityAll(BaseAbstractQuery query,NameSpaceEnum nameSpaceEnum){
    baseCmpCommonDAO.deleteEntityAll(query,nameSpaceEnum);
  }
  
  public int getMaxOrderNum(NameSpaceEnum nameSpaceEnum){
    return baseCmpCommonDAO.getMaxOrderNum(nameSpaceEnum);
  }
  
  public void modifyOrderNumBySave(Integer order,NameSpaceEnum nameSpaceEnum){
     baseCmpCommonDAO.modifyOrderNumBySave(order,nameSpaceEnum);
  }
  
  public void modifyMaxOrderNum(Integer newNum,Integer oldNum,NameSpaceEnum nameSpaceEnum){
     baseCmpCommonDAO.modifyMaxOrderNum(newNum,oldNum,nameSpaceEnum);
  }
  
  public void modifyMinOrderNum(Integer newNum,Integer oldNum,NameSpaceEnum nameSpaceEnum){
     baseCmpCommonDAO.modifyMinOrderNum(newNum,oldNum,nameSpaceEnum);
  }
  
  public void modifyOrderNumByDelete(Integer num,NameSpaceEnum nameSpaceEnum){
    baseCmpCommonDAO.modifyOrderNumByDelete(num,nameSpaceEnum);
 }
  
  @Override
  public boolean deleteEntityByMap(Map<String, Object> map, NameSpaceEnum nameSpaceEnum) throws Exception {

    int ret = baseCmpCommonDAO.deleteEntityByMap(map, nameSpaceEnum);
    if (ret > 0) {
      return true;
    }

    return false;
  }

  @Override
  public List<BaseAbstractQuery> getEntityListById(BaseAbstractQuery query, NameSpaceEnum nameSpaceEnum) {
    return baseCmpCommonDAO.getEntityListById(query, nameSpaceEnum);
  }
}
