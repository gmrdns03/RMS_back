package com.project.LimeRMS.service;

import com.project.LimeRMS.dto.BoardListDto;
import com.project.LimeRMS.mapper.BoardMapper;
import com.project.LimeRMS.mapper.RentalMapper;
import com.project.LimeRMS.dto.RentalListDto;
import com.project.LimeRMS.mapper.CommCdMapper;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentalService {
    private final RentalMapper rentalMapper;
    private final CommCdMapper commCdMapper;
    private final BoardMapper boardMapper;
    private final UserService userService;

    public void rental(Integer loginUserId, Integer userId, Integer contentId) throws Exception {
        // 1. 로그인 유저의 권한 확인
        if (!loginUserId.equals(userId)) {
            Integer loginUserAuthPriority = userService.getUserAuthPriority(String.valueOf(loginUserId));
            if (loginUserAuthPriority > 4) {
                throw new IllegalAccessException("타인에게 컨텐츠를 대여해줄 권한이 없습니다.");
            }
        }

        // 1. 유저의 권한 확인
        Integer userAuthPriority = userService.getUserAuthPriority(String.valueOf(userId));
        BoardListDto boardDto = boardMapper.findOneByContentId(contentId);
        if (boardDto == null) {
            throw new IllegalAccessException("존재하지 않는 contentId 입니다. 값을 확인해주세요");
        }

        Integer boardViewAuth = boardDto.getViewAuth();
        if (userAuthPriority >= boardViewAuth) {
            throw new IllegalAccessException("해당 컨텐츠에 대한 접근 권한이 없습니다.");
        }

        // 2. 현재 컨텐츠의 상태 확인
        String rentalStat = rentalMapper.findLatestStatByContentId(contentId);
        if (rentalStat == null || rentalStat.isEmpty()) {
            rentalStat = "CD001002";
        }

        // 3. 사람당 대여 가능한 총 개수 확인
        Integer rentalLimit = boardDto.getRentalLimit();
        Integer userRentalCnt = rentalMapper.findUserRentalCntByUserId(userId);
        if (rentalLimit <= userRentalCnt) {
            throw new Exception("대여 가능 개수를 초과하여 더 이상 대여가 불가능 합니다.");
        }

        // 4. 대여
        if (rentalStat.equals("CD001002")) {
            // 반납된 컨텐츠 -> 대여 진행
            Map<String, Object> rentalMap = new HashMap<>();
            LocalDateTime now = LocalDateTime.now();
            Integer rentalPeriod = boardDto.getRentalPeriod();
            LocalDateTime predReturnDt = now.plusHours(rentalPeriod).with(LocalTime.of(23, 59, 59));
            rentalMap.put("userId", userId);
            rentalMap.put("contentId", contentId);
            rentalMap.put("rentalDt", now);
            rentalMap.put("predReturnDt", predReturnDt);
            rentalMap.put("rentalStat", "CD001001");
            rentalMap.put("regUserId", userId);
            rentalMap.put("modfUserId", userId);
            rentalMapper.rentalContentByContentId(rentalMap);
        } else {
            // 대여 혹은 연체 중인 컨텐츠 -> 대여 진행 못함
            throw new IllegalAccessException("이미 대여 중인 컨텐츠 입니다. 상태를 다시 확인해주세요");
        }
    }

    public void rentalContentReturn (Integer loginUserId, Integer userId, Integer contentId) throws Exception {
        // 1. 로그인 유저의 권한 확인
        if (!loginUserId.equals(userId)) {
            // 로그인 유저 권한 확인
            Integer userAuthPriority = userService.getUserAuthPriority(String.valueOf(loginUserId));
            if (userAuthPriority > 4) {
                throw new IllegalAccessException("타인의 컨텐츠를 반납할 권한이 없습니다.");
            }
        }

        // 2. 유저 대여 반납
        LocalDateTime now = LocalDateTime.now();
        rentalMapper.contentReturn(userId, contentId, now);
    }

    public String changeContentRentalStat(){
        rentalMapper.updateOverdue();
        return "연체 정보가 업데이트 되었습니다.";
    }

    public List<RentalListDto> getRentalList(String userId) throws Exception {
        List<RentalListDto> rentalListDtos = rentalMapper.findRentalListByUserId(userId);
        if (rentalListDtos == null) {
            return null;
        }

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
            // 날짜 형식 변환 대여일
            String rentalDtString = dto.getRentalDt();
            Date rentalDtDateType = dateParser.parse(rentalDtString);
            String rentalDt = formatter.format(rentalDtDateType);

            // 날짜 형식 변환 반납예정일
            String predReturnDtString = dto.getPredReturnDt();
            Date predReturnDtDateType = dateParser.parse(predReturnDtString);
            String predReturnDt = formatter.format(predReturnDtDateType);

            // 날짜 형식 변환 반납일
            String returnDt;
            if (dto.getReturnDt() == null) {
                returnDt = null;
            } else {
                String returnDtString = dto.getReturnDt();
                Date returnDtDateType = dateParser.parse(returnDtString);
                returnDt = formatter.format(returnDtDateType);
            }

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
