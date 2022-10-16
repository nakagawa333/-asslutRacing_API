package com.example.assolutoRacing.Bean;

import java.util.List;

import com.example.assolutoRacing.Dto.selectCarsDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InfosBean {
	@Getter
	@Setter
	public List<selectCarsDto> carsList;
}
