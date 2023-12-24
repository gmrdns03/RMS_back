package com.project.LimeRMS.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NotificationMapper {

    void updateNotiStatusByNotiId(Integer notiId);
}
