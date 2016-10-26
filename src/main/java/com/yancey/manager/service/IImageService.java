package com.yancey.manager.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.yancey.manager.domain.Image;

public interface IImageService {

  /**
   * 保存图片文件
   * 
   * @param file
   * @param image
   * @throws Exception
   */
  int saveImage(MultipartFile file, Image image) throws Exception;

  /**
   * 删除图片文件（逻辑删除）
   * 
   * @param image
   * @return
   * @throws Exception
   */
  int removeImage(Image image) throws Exception;

  /**
   * 图片分页
   * 
   * @param page
   * @param image
   * @return
   * @throws Exception
   */
  List<Image> findImagePageList(Image form) throws Exception;

  /**
   * 查询图片
   * 
   * @param image
   * @return
   * @throws Exception
   */
  Image findImage(Image image) throws Exception;

  /**
   * 根据图片Id查询图片
   * 
   * @param image
   * @return
   * @throws Exception
   */
  Image findImageById(Image image) throws Exception;

  public String uploadHtmlImage(MultipartFile file) throws Exception;

  public void batchAddWallImage(int userId,MultipartFile file, int type);
  
  int saveApkImage(MultipartFile file, Image image) throws Exception;

  int saveStrategyImage(MultipartFile myfile, Image image)throws Exception;
}
