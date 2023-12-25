package com.project.LimeRMS.mapper;

import com.project.LimeRMS.dto.NotiDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface NotificationMapper {

    void updateNotiStatusByNotiId(Integer notiId);

    List<NotiDto> findNotificationByUserId(String userId);

    void insertOverdueNoti(Integer contentId, Integer receiverId, Integer regUserId, String notiType, @Param("notiContent")String notiContent);
}
