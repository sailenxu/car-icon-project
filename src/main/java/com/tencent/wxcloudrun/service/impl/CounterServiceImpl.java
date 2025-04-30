package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.controller.CounterController;
import com.tencent.wxcloudrun.dao.CountersMapper;
import com.tencent.wxcloudrun.model.Counter;
import com.tencent.wxcloudrun.service.CounterService;
import com.tencent.wxcloudrun.util.UploadProperties;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

@Slf4j
@Service
public class CounterServiceImpl implements CounterService {

  final CountersMapper countersMapper;
  @Autowired
  private UploadProperties uploadProperties;

  public CounterServiceImpl(@Autowired CountersMapper countersMapper) {
    this.countersMapper = countersMapper;
  }

  @Override
  public Optional<Counter> getCounter(Integer id) {
    return Optional.ofNullable(countersMapper.getCounter(id));
  }

  @Override
  public void upsertCount(Counter counter) {
    countersMapper.upsertCount(counter);
  }

  @Override
  public void clearCount(Integer id) {
    countersMapper.clearCount(id);
  }

  @Override
  public String uploadFile(MultipartFile file) {
    if (file.isEmpty()) {
      return "文件为空，请选择一个文件";
    }

    // 获取文件存储路径
    String uploadDir = uploadProperties.getDir();
    File uploadPath = new File(uploadDir);
    if (!uploadPath.exists()) {
      uploadPath.mkdirs(); // 如果目录不存在，创建目录
    }

    try {
      // 保存文件到指定路径
      String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
      log.info("文件名称:{}", fileName);
      String filePath = uploadDir + fileName;
      file.transferTo(new File(filePath));

      // 返回文件的访问 URL（假设你的服务器地址是 http://localhost:8080）
      String fileUrl = "http://localhost:8080/" + filePath;
      return "文件上传成功，文件路径：" + fileUrl;
    } catch (IOException e) {
      e.printStackTrace();
      return "文件上传失败：" + e.getMessage();
    }
  }
}
