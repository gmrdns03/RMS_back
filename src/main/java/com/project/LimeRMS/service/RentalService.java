package com.project.LimeRMS.service;

import com.project.LimeRMS.mapper.RentalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalMapper rentalMapper;

    public String changeContentRentalStat(){
        rentalMapper.updateOverdue();
        return "연체 정보가 업데이트 되었습니다.";
    }

}
