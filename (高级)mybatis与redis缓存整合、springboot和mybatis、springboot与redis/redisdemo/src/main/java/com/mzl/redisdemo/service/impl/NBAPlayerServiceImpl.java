package com.mzl.redisdemo.service.impl;

import com.mzl.redisdemo.entity.NBAPlayer;
import com.mzl.redisdemo.mapper.NBAPlayerMapper;
import com.mzl.redisdemo.service.NBAPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName :   NBAPlayerServiceImpl
 * @Description: NBA球员业务逻辑层
 * @Author: mzl
 * @CreateDate: 2020/9/13 19:30
 * @Version: 1.0
 */
@Service
@Transactional
public class NBAPlayerServiceImpl implements NBAPlayerService {

    @Autowired
    private NBAPlayerMapper nbaPlayerMapper;

    /**
     * 查询所有的NBA球员
     * @return
     */
    @Override
    public List<NBAPlayer> selectAllPlayers() {
        return nbaPlayerMapper.selectAllPlayers();
    }
}
