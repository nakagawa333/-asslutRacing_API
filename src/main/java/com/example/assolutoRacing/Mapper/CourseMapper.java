package com.example.assolutoRacing.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.example.assolutoRacing.Dto.SelectCourseDto;
@Mapper
public interface CourseMapper {
	/**
	 * 全コース情報を取得
	 * 
	 * @return 全車名情報
	 * 
	 */
	List<SelectCourseDto> selectAll();
}
