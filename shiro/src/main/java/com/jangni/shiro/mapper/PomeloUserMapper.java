package com.jangni.shiro.mapper;

import com.jangni.shiro.entity.PomeloUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PomeloUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PomeloUser record);

    int insertSelective(PomeloUser record);

    PomeloUser selectByPrimaryKey(Integer id);

    List<PomeloUser> selectPageAll();

    int updateByPrimaryKeySelective(PomeloUser record);

    int updateByPrimaryKey(PomeloUser record);
}