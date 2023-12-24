package com.project.LimeRMS.service;

import com.project.LimeRMS.mapper.NotificationMapper;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationMapper notificationMapper;

    public String changeContentRentalStat(Map<String, Integer> alarm){
        Integer notiId = alarm.get("notiId");
        notificationMapper.updateNotiStatusByNotiId(notiId);
        return notiId + "의 상태가 '미확인'에서 '확인'으로 변경되었습니다.";
    }

}
