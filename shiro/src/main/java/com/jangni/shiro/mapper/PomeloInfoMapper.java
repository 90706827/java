package com.jangni.shiro.mapper;

import com.jangni.shiro.entity.PomeloInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface PomeloInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PomeloInfo record);

    int insertSelective(PomeloInfo record);

    PomeloInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PomeloInfo record);

    int updateByPrimaryKey(PomeloInfo record);
}