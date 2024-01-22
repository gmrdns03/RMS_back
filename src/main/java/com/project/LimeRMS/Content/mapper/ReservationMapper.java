package com.project.LimeRMS.Content.mapper;

import com.project.LimeRMS.Content.dto.ReservationListDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReservationMapper {



    List<ReservationListDto> findReservationListByUserId(@Param("userId") String userId);

    String findReserveYnByContentId(@Param("contentId") Integer contentId);


    String findReserveIdByContentId(@Param("contentId")Integer contentId);
}
