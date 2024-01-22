package com.project.LimeRMS.Common.mapper;

import com.project.LimeRMS.Common.dto.CateHigherachyDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommonMapper {
    List<CateHigherachyDto> findContentCateHigerachyByCateId(@Param("cateId") Integer CateId);
}
