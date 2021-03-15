package com.mzl.redisdemo.mapper;

import com.mzl.redisdemo.entity.NBAPlayer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @InterfaceName :   NBAPlayerMapper
 * @Description: NBA球员映射器
 * @Author: mzl
 * @CreateDate: 2020/9/13 19:29
 * @Version: 1.0
 */
@Mapper
public interface NBAPlayerMapper {

    /**
     * 查询所有的NBA球员
     * @return
     */
    List<NBAPlayer> selectAllPlayers();
}
