package com.project.LimeRMS.service;

import com.project.LimeRMS.mapper.RentalMapper;
import com.project.LimeRMS.dto.RentalListDto;
import com.project.LimeRMS.mapper.CommCdMapper;
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

    public String changeContentRentalStat(){
        rentalMapper.updateOverdue();
        return "연체 정보가 업데이트 되었습니다.";
    }

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

    public String contentRentalExtension(String likeUserId, Map<String, Integer> rental){
        //1. 대상 대여 데이터에 관하여 rental 테이블에서 extensionCnt(연장 횟수)를 확인
        Integer extensionCnt = rental.get("extensionCnt");
        //2. board 테이블에서 extensionLimit(연장 횟수 제한)을 확인
        Integer extensionLimit = rental.
        //3. 해당 컨텐츠에 대하여 예약자가 있는지 확인
        //연장 횟수 제한보다 연장 횟수가 적을 경우, 예약자가 없을 경우 연장을 진행
        //4. 연장 할 때 대여 데이터의 extensionCnt를 +1 으로 update 하면됨
        //5. 연장 할 때 대여 데이터의 predReturnDt(반납 예정일)를 기존 predReturnDt + Board.rentalPeriod 로 update 하면 됨
        //이 때 rentalPeriod는 시간단위 이므로 기존 predReturnDt에 대여기간을 시간 단위로 더한 후 해당일 밤 11시 59분 59초로 시간을 변경
        //6. 연장 할 때 대여 데이터의 modfUserId를 token에서 가져온 userId로 update
        //7. 연장 할 때 대여 데이터의 rentalStat(대여상태)를 'CD001001'(대여 중)으로 update



    }
}
