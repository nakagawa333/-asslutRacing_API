package com.example.assolutoRacing.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.assolutoRacing.Bean.AddSettingInfoBean;
import com.example.assolutoRacing.Constants.Constants;
import com.example.assolutoRacing.Dto.AddSettingInfoDto;
import com.example.assolutoRacing.Service.SettingInfoService;

/**
 * 
 * @author nakagawa.so
 * 設定情報登録コントローラークラス
 */
@CrossOrigin(origins = Constants.ORIGINS)
@RestController
public class AddSettingInfoController{
	@Autowired
	SettingInfoService settingInfoService;
	
	@RequestMapping(path = "/add", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<Integer> add(@RequestBody(required = true) @Validated AddSettingInfoBean addSettingInfo) {
		int insertCount = 0;
				
		AddSettingInfoDto addSettingInfoDto = new AddSettingInfoDto();
		addSettingInfoDto.setAbs(addSettingInfoDto.getAbs());
		addSettingInfoDto.setAckermannAngle(addSettingInfoDto.getAckermannAngle());
		addSettingInfoDto.setAirPressure(addSettingInfoDto.getAirPressure());
		addSettingInfoDto.setBreakBallance(addSettingInfoDto.getBreakBallance());
		addSettingInfoDto.setBreakPower(addSettingInfoDto.getBreakPower());
		addSettingInfoDto.setCamberAfter(addSettingInfoDto.getCamberAfter());
		addSettingInfoDto.setCamberAgo(addSettingInfoDto.getCamberAgo());
		addSettingInfoDto.setCarHigh(addSettingInfoDto.getCarHigh());
		addSettingInfoDto.setCarId(addSettingInfoDto.getCarId());
		addSettingInfoDto.setCourseId(addSettingInfoDto.getCourseId());
		addSettingInfoDto.setCarId(addSettingInfoDto.getCarId());
		addSettingInfoDto.setDiffgear(addSettingInfoDto.getDiffgear());
		addSettingInfoDto.setFrontTirePressure(addSettingInfoDto.getFrontTirePressure());
		addSettingInfoDto.setGearFinal(addSettingInfoDto.getGearFinal());
		addSettingInfoDto.setGearSix(addSettingInfoDto.getGearSix());
		addSettingInfoDto.setGearFive(addSettingInfoDto.getGearFive());
		addSettingInfoDto.setGearFour(addSettingInfoDto.getGearFour());
		addSettingInfoDto.setGearThree(addSettingInfoDto.getGearThree());
		addSettingInfoDto.setGearTwo(addSettingInfoDto.getGearTwo());
		addSettingInfoDto.setGearOne(addSettingInfoDto.getGearOne());
		addSettingInfoDto.setHoilesize(addSettingInfoDto.getHoilesize());
		addSettingInfoDto.setId(addSettingInfoDto.getId());
		addSettingInfoDto.setMakerId(addSettingInfoDto.getMakerId());
		addSettingInfoDto.setMaxRudderAngle(addSettingInfoDto.getMaxRudderAngle());
		addSettingInfoDto.setMemo(addSettingInfoDto.getMemo());
		addSettingInfoDto.setOffset(addSettingInfoDto.getOffset());
		addSettingInfoDto.setPowerSteering(addSettingInfoDto.getPowerSteering());
		addSettingInfoDto.setRearTirePressure(addSettingInfoDto.getRearTirePressure());
		addSettingInfoDto.setStabiliserAfter(addSettingInfoDto.getStabiliserAfter());
		addSettingInfoDto.setStabiliserAgo(addSettingInfoDto.getStabiliserAgo());
		addSettingInfoDto.setTireId(addSettingInfoDto.getTireId());
		addSettingInfoDto.setTitle(addSettingInfoDto.getTitle());
		addSettingInfoDto.setUserId(addSettingInfoDto.getUserId());
		
		try {
			insertCount = settingInfoService.insert(addSettingInfoDto);
		} catch(Exception e) {
			throw e;
		}
		HttpHeaders headers = new HttpHeaders();
		
		ResponseEntity<Integer> resEntity = new ResponseEntity<>(insertCount,headers,HttpStatus.CREATED); 
		return resEntity;
	}

}