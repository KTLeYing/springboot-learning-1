package com.mzl.redisdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName :   NBAPlayer
 * @Description: nba球员实体类
 * @Author: mzl
 * @CreateDate: 2020/9/13 19:25
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NBAPlayer {

    private Integer id;
    private String countryEn;
    private String teamName;
    private String birthDay;

}
