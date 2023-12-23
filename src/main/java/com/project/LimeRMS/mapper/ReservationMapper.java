package com.project.LimeRMS.mapper;

import com.project.LimeRMS.dto.ReservationListDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReservationMapper {
    List<ReservationListDto> findReservationListByUserId(@Param("userId") String userId);

}
