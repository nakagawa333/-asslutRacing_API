package com.example.assolutoRacing.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.example.assolutoRacing.Dto.selectCarsDto;
@Mapper
public interface CarsMapper {
	/**
	 * 全車名情報を取得
	 * 
	 * @return 全車名情報
	 * 
	 */
	List<selectCarsDto> selectAll();
}
