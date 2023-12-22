package com.project.LimeRMS.mapper;

import com.project.LimeRMS.dto.CommCdDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommCdMapper {
    String findCdNmByCd(String commCd);

    List<CommCdDto> findCommCdByHiCommCd(String hiCommCd);
}
