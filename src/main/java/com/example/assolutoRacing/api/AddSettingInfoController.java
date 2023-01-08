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
	public ResponseEntity<Boolean> add(@RequestBody(required = true) @Validated AddSettingInfoBean addSettingInfo) {
		int insertCount = 0;
				
		AddSettingInfoDto addSettingInfoDto = new AddSettingInfoDto();
		addSettingInfoDto.setAbs(addSettingInfo.getAbs());
		addSettingInfoDto.setAckermannAngle(addSettingInfo.getAckermannAngle());
		addSettingInfoDto.setAirPressure(addSettingInfo.getAirPressure());
		addSettingInfoDto.setBreakBallance(addSettingInfo.getBreakBallance());
		addSettingInfoDto.setBreakPower(addSettingInfo.getBreakPower());
		addSettingInfoDto.setCamberAfter(addSettingInfo.getCamberAfter());
		addSettingInfoDto.setCamberAgo(addSettingInfo.getCamberAgo());
		addSettingInfoDto.setCarHigh(addSettingInfo.getCarHigh());
		addSettingInfoDto.setCarId(addSettingInfo.getCarId());
		addSettingInfoDto.setCourseId(addSettingInfo.getCourseId());
		addSettingInfoDto.setCarId(addSettingInfo.getCarId());
		addSettingInfoDto.setDiffgear(addSettingInfo.getDiffgear());
		addSettingInfoDto.setFrontTirePressure(addSettingInfo.getFrontTirePressure());
		addSettingInfoDto.setGearFinal(addSettingInfo.getGearFinal());
		addSettingInfoDto.setGearSix(addSettingInfo.getGearSix());
		addSettingInfoDto.setGearFive(addSettingInfo.getGearFive());
		addSettingInfoDto.setGearFour(addSettingInfo.getGearFour());
		addSettingInfoDto.setGearThree(addSettingInfo.getGearThree());
		addSettingInfoDto.setGearTwo(addSettingInfo.getGearTwo());
		addSettingInfoDto.setGearOne(addSettingInfo.getGearOne());
		addSettingInfoDto.setHoilesize(addSettingInfo.getHoilesize());
		addSettingInfoDto.setMakerId(addSettingInfo.getMakerId());
		addSettingInfoDto.setMaxRudderAngle(addSettingInfo.getMaxRudderAngle());
		addSettingInfoDto.setMemo(addSettingInfo.getMemo());
		addSettingInfoDto.setOffset(addSettingInfo.getOffset());
		addSettingInfoDto.setPowerSteering(addSettingInfo.getPowerSteering());
		addSettingInfoDto.setRearTirePressure(addSettingInfo.getRearTirePressure());
		addSettingInfoDto.setStabiliserAfter(addSettingInfo.getStabiliserAfter());
		addSettingInfoDto.setStabiliserAgo(addSettingInfo.getStabiliserAgo());
		addSettingInfoDto.setTireId(addSettingInfo.getTireId());
		addSettingInfoDto.setTitle(addSettingInfo.getTitle());
		addSettingInfoDto.setUserId(addSettingInfo.getUserId());		
		
		try {
			insertCount = settingInfoService.insert(addSettingInfoDto);
		} catch(Exception e) {
			throw e;
		}
		
		//追加した設定情報が1件の場合、true
		boolean insertSucessFlag = insertCount == 1 ? true : false;
		
		HttpHeaders headers = new HttpHeaders();
		
		ResponseEntity<Boolean> resEntity = new ResponseEntity<>(insertSucessFlag,headers,HttpStatus.CREATED); 
		return resEntity;
	}

}