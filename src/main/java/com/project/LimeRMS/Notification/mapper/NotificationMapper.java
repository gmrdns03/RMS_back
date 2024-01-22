package com.project.LimeRMS.Notification.mapper;

import com.project.LimeRMS.Notification.dto.NotiDto;
import java.util.List;

import com.project.LimeRMS.entity.Notification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface NotificationMapper {

    void updateNotiStatusByNotiId(Integer notiId);

    List<NotiDto> findNotificationByUserId(String userId);

    void insertOverdueNoti(@Param("contentId")Integer contentId, @Param("receiverId")Integer receiverId, @Param("regUserId")Integer regUserId, @Param("notiType")String notiType, @Param("notiContent")String notiContent);

    Notification findNotiByContentId(@Param("contentId")Integer contentId, @Param("notiType")String notiType, @Param("receiverId")Integer receiverId);
}
