package com.example.assolutoRacing.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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

import com.example.assolutoRacing.Dto.SelectCarsDto;
import com.example.assolutoRacing.Mapper.CarsMapper;
import com.example.assolutoRacing.Mapper.CourseMapper;
import com.example.assolutoRacing.Mapper.MakerMapper;
import com.example.assolutoRacing.Mapper.TireTypeMapper;
import com.example.assolutoRacing.TestBase.MySQLConnector;

@SpringBootTest
class InfosServiceTest{
	
	private AutoCloseable closeable;
	
	@InjectMocks
	InfosService infosServiceMock;
	
	@Autowired
	InfosService infosService;
	
	@Mock
	CarsMapper carsMapper;
	
	@Mock
	CourseMapper courseMapper;
	
	@Mock
	MakerMapper makerMapper;
	
	@Mock
	TireTypeMapper tireTypeMapper;
	
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
	
    //正常系　車両一覧を取得
	@Test
	void testSucess1() throws Exception {
		List<SelectCarsDto> carsList = new ArrayList<>();
		try {
			carsList = infosService.selectCarsAll();
			assertTrue(true);
		} catch(Exception e) {
			fail();
		}
		
		Map<Integer,SelectCarsDto> carsMap = new HashMap<Integer,SelectCarsDto>();
		
		for(SelectCarsDto car : carsList) {
			carsMap.put(car.getCarId(),car);
		}
		
		ResultSet carsRes = mySQLConnector.select("select car_name, car_id, maker_id from car;");
		
		while(carsRes.next()) {
			assertTrue(carsMap.containsKey(carsRes.getInt("car_id")));
			SelectCarsDto car = carsMap.get(carsRes.getInt("car_id"));
			assertThat(car.getCarName()).contains(carsRes.getString("car_name"));
			assertThat(car.getMakerId()).isEqualTo(carsRes.getInt("maker_id"));
		}
	}
	
	//異常系　車両一覧を取得失敗
	@Test
	void testError1() throws Exception{
		try {
			doThrow(new RuntimeException()).when(carsMapper).selectAll();
			infosServiceMock.selectCarsAll();
			fail();
		} catch(Exception e) {
			assertTrue(true);
		}
	}
}
