package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.Counter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.List;

public interface CounterService {

  Optional<Counter> getCounter(Integer id);

  void upsertCount(Counter counter);

  void clearCount(Integer id);

  String uploadFile(MultipartFile file);
}
