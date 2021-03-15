package com.example.demo.dubbo.mapper;

import com.example.demo.dubbo.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

/**
 * 路径：com.example.demo.dubbo.mapper
 * 类名：
 * 功能：《用一句描述一下》
 * 备注：
 * 创建人：typ
 * 创建时间：2018/11/16 15:32
 * 修改人：
 * 修改备注：
 * 修改时间：
 */
@Mapper
public interface AdminMapper {

    Admin getById(Integer id);

}
