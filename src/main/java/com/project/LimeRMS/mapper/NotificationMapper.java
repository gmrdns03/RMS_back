package com.project.LimeRMS.mapper;

import com.project.LimeRMS.dto.NotiDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NotificationMapper {

    void updateNotiStatusByNotiId(Integer notiId);

    List<NotiDto> findNotificationByUserId(String userId);
}
