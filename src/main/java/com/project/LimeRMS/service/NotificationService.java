package com.project.LimeRMS.service;

import com.project.LimeRMS.dto.NotiDto;
import com.project.LimeRMS.mapper.NotificationMapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationMapper notificationMapper;

    public List<NotiDto> getAllNotification(String userId){
        List<NotiDto> notiDtoList = notificationMapper.findNotificationByUserId(userId);
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        for (NotiDto notiDto : notiDtoList){
            LocalDateTime joinDt = LocalDateTime.parse(notiDto.getJoinDt(), inputFormatter);
            String formattedDate = joinDt.format(outputFormatter);
            notiDto.setJoinDt(formattedDate);
        }
        return notiDtoList;
    }

    public String changeContentRentalStat(Map<String, Integer> alarm){
        Integer notiId = alarm.get("notiId");
        notificationMapper.updateNotiStatusByNotiId(notiId);
        return notiId + "의 상태가 '미확인'에서 '확인'으로 변경되었습니다.";
    }

}
