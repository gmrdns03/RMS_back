package com.project.LimeRMS.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommCdMapper {
    String findCdNmByUserStat(String userStat);
}
