package com.project.LimeRMS.Content.service;

import com.project.LimeRMS.Content.dto.ReservationListDto;
import com.project.LimeRMS.Common.mapper.CommCdMapper;
import com.project.LimeRMS.Content.mapper.ReservationMapper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationMapper reservationMapper;
    private final CommCdMapper commCdMapper;
    public List<ReservationListDto> getReservationList(String userId) throws Exception {
        List<ReservationListDto> reservationListDtos;
        reservationListDtos = reservationMapper.findReservationListByUserId(userId);
        if (reservationListDtos == null) {
            return null;
        }

        List<ReservationListDto> resReservationListDtos = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (ReservationListDto dto : reservationListDtos) {
            Integer reserveId = dto.getReserveId();
            String boardNm = dto.getBoardNm();
            Integer boardId = dto.getBoardId();
            String cateNm = dto.getCateNm();
            Integer contentId = dto.getContentId();
            String contentNm = dto.getContentNm();
            // 날짜 형식 변환
            String regDtString = dto.getRegDt();
            Date regDtDateType = dateParser.parse(regDtString);
            String regDt = formatter.format(regDtDateType);
            // rentalStat이 비어 있을 경우 디폴트 값 셋팅
            String rentalStat = dto.getRentalStat();
            if (rentalStat == null || rentalStat.isEmpty()) {
                rentalStat = "CD001002";
            }
            String rentalStatNm = commCdMapper.findCdNmByCd(rentalStat);
            ReservationListDto tempReservationListDto = new ReservationListDto(
                reserveId, boardNm, boardId, cateNm, contentId, contentNm, regDt, rentalStat, rentalStatNm
            );
            resReservationListDtos.add(tempReservationListDto);
        }
        return resReservationListDtos;

    }
}
