package com.project.LimeRMS.mapper;

import com.project.LimeRMS.dto.OverdueContentListDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RentalMapper {
    List<OverdueContentListDto> findRentalByRentalStat(String rentalStat);

    void updateOverdue();
}
