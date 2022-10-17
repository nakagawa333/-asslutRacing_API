package com.example.assolutoRacing.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.assolutoRacing.Dto.SelectMakerDto;
@Mapper
public interface MakerMapper {
	/**
	 * 全メーカー情報を取得
	 * 
	 * @return 全メーカー情報
	 * 
	 */
	List<SelectMakerDto> selectAll();
}
