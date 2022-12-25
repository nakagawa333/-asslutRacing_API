package com.example.assolutoRacing.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.assolutoRacing.Bean.UpdateSettingInfoBean;
import com.example.assolutoRacing.Constants.Constants;
import com.example.assolutoRacing.Dto.UpdateSettingInfoDto;
import com.example.assolutoRacing.Service.SettingInfoService;

/**
 * 
 * @author nakagawa.so
 * 設定情報更新コントローラークラス
 */
@CrossOrigin(origins = Constants.ORIGINS)
@RestController
public class UpdateSettingInfoController {	
	@Autowired
	SettingInfoService settingInfoService;
	
	@RequestMapping(path = "/update", method = RequestMethod.PUT)
	@Transactional
	public ResponseEntity<Boolean> update(@RequestBody(required = true) UpdateSettingInfoBean updateSettingInfo) {
		int updateCount = 0;
		
		UpdateSettingInfoDto updateSettingInfoDto = new UpdateSettingInfoDto();
		updateSettingInfoDto.setAbs(updateSettingInfo.getAbs());
		updateSettingInfoDto.setAckermannAngle(updateSettingInfo.getAckermannAngle());
		updateSettingInfoDto.setAirPressure(updateSettingInfo.getAirPressure());
		updateSettingInfoDto.setBreakBallance(updateSettingInfo.getBreakBallance());
		updateSettingInfoDto.setBreakPower(updateSettingInfo.getBreakPower());
		updateSettingInfoDto.setCamberAfter(updateSettingInfo.getCamberAfter());
		updateSettingInfoDto.setCamberAgo(updateSettingInfo.getCamberAgo());
		updateSettingInfoDto.setCarHigh(updateSettingInfo.getCarHigh());
		updateSettingInfoDto.setCarId(updateSettingInfo.getCarId());
		updateSettingInfoDto.setCourseId(updateSettingInfo.getCourseId());
		updateSettingInfoDto.setCarId(updateSettingInfo.getCarId());
		updateSettingInfoDto.setDiffgear(updateSettingInfo.getDiffgear());
		updateSettingInfoDto.setFrontTirePressure(updateSettingInfo.getFrontTirePressure());
		updateSettingInfoDto.setGearFinal(updateSettingInfo.getGearFinal());
		updateSettingInfoDto.setGearFive(updateSettingInfo.getGearFive());
		updateSettingInfoDto.setGearFour(updateSettingInfo.getGearFour());
		updateSettingInfoDto.setGearThree(updateSettingInfo.getGearThree());
		updateSettingInfoDto.setGearTwo(updateSettingInfo.getGearTwo());
		updateSettingInfoDto.setGearOne(updateSettingInfo.getGearOne());
		updateSettingInfoDto.setHoilesize(updateSettingInfo.getHoilesize());
		updateSettingInfoDto.setId(updateSettingInfo.getId());
		updateSettingInfoDto.setMakerId(updateSettingInfo.getMakerId());
		updateSettingInfoDto.setMaxRudderAngle(updateSettingInfo.getMaxRudderAngle());
		updateSettingInfoDto.setMemo(updateSettingInfo.getMemo());
		updateSettingInfoDto.setOffset(updateSettingInfo.getOffset());
		updateSettingInfoDto.setPowerSteering(updateSettingInfo.getPowerSteering());
		updateSettingInfoDto.setRearTirePressure(updateSettingInfo.getRearTirePressure());
		updateSettingInfoDto.setStabiliserAfter(updateSettingInfo.getStabiliserAfter());
		updateSettingInfoDto.setStabiliserAgo(updateSettingInfo.getStabiliserAgo());
		updateSettingInfoDto.setTireId(updateSettingInfo.getTireId());
		updateSettingInfoDto.setTitle(updateSettingInfo.getTitle());
		updateSettingInfoDto.setUserId(updateSettingInfo.getUserId());
		
		try {
			updateCount = settingInfoService.update(updateSettingInfoDto);
		} catch(Exception e) {
			throw e;
		}
		
		//更新件数が1件の場合成功
		Boolean updateSucessFlag = updateCount == 1 ? true : false;
		HttpHeaders headers = new HttpHeaders();
		
		ResponseEntity<Boolean> resEntity = new ResponseEntity<>(updateSucessFlag,headers,HttpStatus.CREATED); 
		return resEntity;
	}

}