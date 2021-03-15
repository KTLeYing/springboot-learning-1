package com.mzl.redisdemo.service;

import com.mzl.redisdemo.entity.NBAPlayer;

import java.util.List;

/**
 * @InterfaceName :   NBAPlayerService
 * @Description: NBA球员业务逻辑层接口
 * @Author: mzl
 * @CreateDate: 2020/9/13 19:30
 * @Version: 1.0
 */
public interface NBAPlayerService {

    /**
     * 查询所有的NBA球员
     * @return
     */
    List<NBAPlayer> selectAllPlayers();
}
