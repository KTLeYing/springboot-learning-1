package com.example.demo.es.repository;

import com.example.demo.es.entity.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * 路径：com.example.demo.es.repository
 * 类名：
 * 功能：《用一句描述一下》
 * 备注：
 * 创建人：typ
 * 创建时间：2019/1/8 10:11
 * 修改人：
 * 修改备注：
 * 修改时间：
 */
@Component
public interface UserRepository extends ElasticsearchRepository<User,Long> {
}
