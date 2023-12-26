package com.project.LimeRMS.service;

import com.project.LimeRMS.dto.NotiDto;
import com.project.LimeRMS.dto.OverdueContentListDto;
import com.project.LimeRMS.entity.Notification;
import com.project.LimeRMS.mapper.NotificationMapper;
import com.project.LimeRMS.mapper.RentalMapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationMapper notificationMapper;
    private final RentalMapper rentalMapper;

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

    public String addOverdueNotification(String userId){
        //로그인한 유저의 연체된 컨텐츠만 확인해서 알림에 추가
        List<OverdueContentListDto> overdueRentals = rentalMapper.findOverdueRentalByUserId(userId);
        for (OverdueContentListDto overdueRental : overdueRentals){
            Integer contentId = overdueRental.getContentId();
            Integer receiverId = overdueRental.getUserId();
            String contentNm = overdueRental.getContentNm();
            String notiType = "CD004001";
            Integer regUserId = 1;
            String notiContent = "대여중인 컨텐츠 '"+contentNm+"'의 반납 기한이 지났습니다. 빠른 시일 내에 반납 해주시길 바랍니다.";

            //이 contentId로 된 나에게 온 notiType이 연체인 알람이 있는가?
            Notification noti = notificationMapper.findNotiByContentId(contentId, notiType, receiverId);
            if (noti != null){ // 해당 알림이 있다면 오늘 날짜의 알림인지 확인
                LocalDateTime joinDt = noti.getJoinDt();
                LocalDateTime currentDate = LocalDateTime.now();
                if (joinDt.getDayOfWeek() != currentDate.getDayOfWeek()) {
                    //오늘 생성된 알림이 아니라면 추가
                    notificationMapper.insertOverdueNoti(contentId, receiverId, regUserId, notiType, notiContent);
                    Integer notiId = noti.getNotiId();
                    String notiStatus = noti.getNotiStatus();
                    //미확인("CD005001")이라면 전 일자의 알림은 비활성화
                    if (Objects.equals(notiStatus, "CD005001")){
                        notificationMapper.updateNotiStatusByNotiId(notiId);
                    }
                }
            } else { //해당 알림이 없다면 추가
                notificationMapper.insertOverdueNoti(contentId, receiverId, regUserId, notiType, notiContent);
            }
        }
        return "연체된 컨텐츠들이 성공적으로 알림에 추가되었습니다.";
    }
}
