package com.project.LimeRMS.mapper;

import com.project.LimeRMS.dto.CateHigherachyDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommonMapper {
    List<CateHigherachyDto> findContentCateHigerachyByCateId(@Param("cateId") Integer CateId);
}
