package com.example.assolutoRacing.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.assolutoRacing.Bean.InfosBean;
import com.example.assolutoRacing.Dto.SelectCarsDto;
import com.example.assolutoRacing.Dto.SelectCourseDto;
import com.example.assolutoRacing.Dto.SelectMakerDto;
import com.example.assolutoRacing.Dto.SelectTireTypeDto;
import com.example.assolutoRacing.Service.InfosService;
import com.example.assolutoRacing.TestBase.MySQLConnector;

@SpringBootTest
class InfosControllerTest{
	private AutoCloseable closeable;
	
	@Autowired
	private InfosController infosController;
	
	@InjectMocks
	private InfosController infosControllerMock;
	
	@Mock
	private InfosService infosService;
	
	@Autowired
	MySQLConnector mySQLConnector;
	
	@BeforeEach
    public void setUp() throws Exception {
		//データベースに接続
		mySQLConnector.getConnection();
    	closeable = MockitoAnnotations.openMocks(this);
    }
    
    @AfterEach
    void tearDown() throws Exception {
    	//データベースの接続を閉じる
    	mySQLConnector.close();
        closeable.close();
    }

	
	//正常系
	@Test
	void testSucess1() {
		try {
			ResponseEntity<InfosBean> resInfos = infosController.infos();
			InfosBean infosBean = resInfos.getBody();
			List<SelectCarsDto> carsList = infosBean.getCarsList();
			List<SelectCourseDto> courseList = infosBean.getCourseList();
			List<SelectMakerDto> makerList = infosBean.getMakerList();
			List<SelectTireTypeDto> tireTypeList = infosBean.getTireTypeList();
			
			Map<Integer,SelectCarsDto> carsMap = new HashMap<Integer,SelectCarsDto>();
			Map<Integer,SelectCourseDto> courseMap = new HashMap<Integer,SelectCourseDto>();
			Map<Integer,SelectMakerDto> makerMap = new HashMap<Integer,SelectMakerDto>();
			Map<Integer,SelectTireTypeDto> tireTypeMap = new HashMap<Integer,SelectTireTypeDto>();
			
			for(SelectCarsDto car: carsList) {
				carsMap.put(car.getCarId(), car);
			}
			
			ResultSet carsRes = mySQLConnector.select("select car_name, car_id, maker_id from car;");
			
			while(carsRes.next()) {
				assertTrue(carsMap.containsKey(carsRes.getInt("car_id")));
				SelectCarsDto car = carsMap.get(carsRes.getInt("car_id"));
				assertThat(car.getCarName()).contains(carsRes.getString("car_name"));
				assertThat(car.getMakerId()).isEqualTo(carsRes.getInt("maker_id"));
			}
			
			for(SelectCourseDto course : courseList) {
				courseMap.put(course.getCourseId(), course);
			}
			
			ResultSet courseRes = mySQLConnector.select("select course_name,course_id from course;");
			
			while(courseRes.next()) {
				assertTrue(courseMap.containsKey(courseRes.getInt("course_id")));
				SelectCourseDto course = courseMap.get(courseRes.getInt("course_id"));
				assertThat(course.getCourseName()).contains(courseRes.getString("course_name"));
			}
			
			for(SelectMakerDto maker: makerList) {
				makerMap.put(maker.getMakerId(),maker);
			}
			
			ResultSet makerRes = mySQLConnector.select("select maker_name,maker_id from maker;");

			while(makerRes.next()) {
				assertTrue(makerMap.containsKey(makerRes.getInt("maker_id")));
				SelectMakerDto maker = makerMap.get(makerRes.getInt("maker_id"));
				assertThat(maker.getMakerName()).contains(makerRes.getString("maker_name"));
			}
			
			for(SelectTireTypeDto tire: tireTypeList) {
				tireTypeMap.put(tire.getTireId(),tire);
			}
			
			ResultSet tireRes = mySQLConnector.select("select tire_name,tire_id from tire_type;");

			while(tireRes.next()) {
				assertTrue(tireTypeMap.containsKey(tireRes.getInt("tire_id")));
				SelectTireTypeDto tire = tireTypeMap.get(tireRes.getInt("tire_id"));
				assertThat(tire.getTireName()).contains(tireRes.getString("tire_name"));
			}
			
			
			//HTTPステータスが200 OKであること
			assertThat(HttpStatus.OK.value()).isEqualTo(resInfos.getStatusCodeValue());
			assertTrue(true);
		} catch(Exception e) {
			fail();
		}
	}
	
	//異常系 車一覧取得失敗
	@Test
	void testError1() {
		try {
			doThrow(new RuntimeException()).when(infosService).selectCarsAll();
			//対象メソッド実施
			infosControllerMock.infos();
			fail();
		} catch(Exception e) {
			assertTrue(true);
		}
	}
	
	//異常系 コース一覧取得失敗
	@Test
	void testError2() {
		try {
			doThrow(new RuntimeException()).when(infosService).selectCourseAll();
			//対象メソッド実施
			infosControllerMock.infos();
			fail();
		} catch(Exception e) {
			assertTrue(true);
		}
	}
	
	//異常系 メーカー一覧取得失敗
	@Test
	void testError3() {
		try {
			doThrow(new RuntimeException()).when(infosService).selectMakerAll();
			//対象メソッド実施
			infosControllerMock.infos();
			fail();
		} catch(Exception e) {
			assertTrue(true);
		}
	}
	
	//異常系 タイヤの種類一覧
	@Test
	void testError4() {
		try {
			doThrow(new RuntimeException()).when(infosService).selectTireTypeAll();
			//対象メソッド実施
			infosControllerMock.infos();
			fail();
		} catch(Exception e) {
			assertTrue(true);
		}
	}
}
