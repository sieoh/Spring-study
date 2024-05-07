package com.humanedu.searchfood.naver;

import com.humanedu.searchfood.naver.dto.SearchImgRequestDto;
import com.humanedu.searchfood.naver.dto.SearchImgResponseDto;
import com.humanedu.searchfood.naver.dto.SearchRegionRequestDto;
import com.humanedu.searchfood.naver.dto.SearchRegionResponseDto;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class NaverAPIClient {
  /**  1. 지역검색 API call method(request dto, response dto)  **/
  public SearchRegionResponseDto searchRegion(SearchRegionRequestDto searchRegionRequestDto) {
    // (3, 4) Response 설정, 실제
    ResponseEntity<SearchRegionResponseDto> responseRestTemplate
      = new RestTemplate().exchange(getURI("/v1/search/local.json",searchRegionRequestDto.getQuery())
                                    , HttpMethod.GET
                                    , getHttpEntity()
                                    , SearchRegionResponseDto.class
    );
    return responseRestTemplate.getBody();
  }

  /**  2. 지역 이미지 검색 API call method(request dto, response dto)  **/
  public SearchImgResponseDto searchImg(SearchImgRequestDto searchImgRequestDto) {
    // (4) 실제 API Call
    return new RestTemplate()
        .exchange(getURI("/v1/search/image", searchImgRequestDto.getQuery())
                  , HttpMethod.GET
                  , getHttpEntity()
                  , SearchImgResponseDto.class
        ).getBody();
  }


  /**  (1) Header 리팩토링 설정  **/
  /** API call library -> HttpURLConnection, WebClient, [RestTemplate], Retry  etc... (여기서는 RestTemplate 사용)  **/
  private HttpHeaders getHttpHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.set("X-Naver-Client-Id", "DKr23szy0TgNWJCwf7Cz");
    headers.set("X-Naver-Client-Secret", "6PpXY8Lg5n");
    headers.setContentType(MediaType.APPLICATION_JSON);

    return headers;
  }

  /**  (2) Request 설정  **/
  private URI getURI(String path, String query) {
    return UriComponentsBuilder
            .fromUriString("https://openapi.naver.com")
            .path(path)
            .queryParam("query", query)
            .encode()
            .build()
            .toUri();
  }

  /**  (3) Response 설정  **/
  private HttpEntity getHttpEntity() {
    return new HttpEntity(getHttpHeaders());
  }
}
