package com.example.assolutoRacing.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.assolutoRacing.Dto.SelectTireTypeDto;

@Mapper
public interface TireTypeMapper{
	/** タイヤの種類一覧全取得 **/
	List<SelectTireTypeDto> selectAll();
}