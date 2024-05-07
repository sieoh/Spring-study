package com.humanedu.searchfood;

import com.humanedu.searchfood.naver.NaverAPIClient;
import com.humanedu.searchfood.naver.dto.SearchImgRequestDto;
import com.humanedu.searchfood.naver.dto.SearchImgResponseDto;
import com.humanedu.searchfood.naver.dto.SearchRegionRequestDto;
import com.humanedu.searchfood.naver.dto.SearchRegionResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SearchfoodApiApplicationTests {
	@Autowired
	private NaverAPIClient naverAPIClient;

	@Test
	void contextLoads() {
	}

	@Test
	void naverSearchRegionAPITEST(){
		String paramQuery = "커피";

		// 네이버 지역검색 OpenAPI Call method 테스트
		SearchRegionRequestDto searchRegionRequestDto = new SearchRegionRequestDto();
		searchRegionRequestDto.setQuery(paramQuery);

		SearchRegionResponseDto searchRegionResponseDto =
			naverAPIClient.searchRegion(searchRegionRequestDto);
		System.out.println("네이버 지경검색 OpenAPI response json " + searchRegionResponseDto);
	}

	@Test
	void naverSearchImgAPITEST(){
		String paramQuery = "커피";

		// 네이버 이미지 검색 OpenAPI Call method 테스트
		SearchImgRequestDto searchImgRequestDto = new SearchImgRequestDto();
		searchImgRequestDto.setQuery(paramQuery);

		SearchImgResponseDto searchImgResponseDto =
			naverAPIClient.searchImg(searchImgRequestDto);
		System.out.println("네이버 지경검색 OpenAPI response json " + searchImgResponseDto);
	}
}
