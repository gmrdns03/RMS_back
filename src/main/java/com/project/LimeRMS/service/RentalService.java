package com.project.LimeRMS.service;

import com.project.LimeRMS.dto.RentalListDto;
import com.project.LimeRMS.mapper.CommCdMapper;
import com.project.LimeRMS.mapper.RentalMapper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentalService {
    private final RentalMapper rentalMapper;
    private final CommCdMapper commCdMapper;
    public List<RentalListDto> getRentalList(String userId) throws Exception {
        List<RentalListDto> rentalListDtos = rentalMapper.findRentalListByUserId(userId);
        List<RentalListDto> resRentalListDtos = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (RentalListDto dto : rentalListDtos) {
            Integer rentalId = dto.getRentalId();
            String boardNm = dto.getBoardNm();
            Integer boardId = dto.getBoardId();
            String cateNm = dto.getCateNm();
            Integer contentId = dto.getContentId();
            String contentNm = dto.getContentNm();
            // 날짜 형식 변환
            String rentalDtString = dto.getRentalDt();
            String predReturnDtString = dto.getPredReturnDt();
            String returnDtString = dto.getReturnDt();

            Date rentalDtDateType = dateParser.parse(rentalDtString);
            Date predReturnDtDateType = dateParser.parse(predReturnDtString);
            Date returnDtDateType = dateParser.parse(returnDtString);

            String rentalDt = formatter.format(rentalDtDateType);
            String predReturnDt = formatter.format(predReturnDtDateType);
            String returnDt = formatter.format(returnDtDateType);

            // rentalStat이 비어 있을 경우 디폴트 값 셋팅
            String rentalStat = dto.getRentalStat();
            if (rentalStat == null || rentalStat.isEmpty()) {
                rentalStat = "CD001002";
            }
            String rentalStatNm = commCdMapper.findCdNmByCd(rentalStat);
            RentalListDto tempRentalListDto = new RentalListDto(
                rentalId, boardNm, boardId, cateNm, contentId, contentNm, rentalDt, predReturnDt, returnDt, rentalStat, rentalStatNm
            );
            resRentalListDtos.add(tempRentalListDto);
        }
        return resRentalListDtos;
    }
}
