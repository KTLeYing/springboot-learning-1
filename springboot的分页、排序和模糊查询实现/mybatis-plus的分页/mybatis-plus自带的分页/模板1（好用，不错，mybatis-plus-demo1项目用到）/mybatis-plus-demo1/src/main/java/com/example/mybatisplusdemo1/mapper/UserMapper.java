package com.example.mybatisplusdemo1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplusdemo1.dto.UserDTO;
import com.example.mybatisplusdemo1.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @InterfaceName :   UserMapper
 * @Description: 用户Mapper映射器
 * @Author: mzl
 * @CreateDate: 2020/11/5 2:32
 * @Version: 1.0
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 获取所有用户
     * @return
     */
    List<User> userList();

    /**
     * 查询直接返回一个用户dto
     * @return
     */
    List<UserDTO> findUserDTO();
}
