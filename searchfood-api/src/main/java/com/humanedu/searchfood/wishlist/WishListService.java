package com.humanedu.searchfood.wishlist;

import com.humanedu.searchfood.naver.NaverAPIClient;
import com.humanedu.searchfood.naver.dto.SearchImgRequestDto;
import com.humanedu.searchfood.naver.dto.SearchImgResponseDto;
import com.humanedu.searchfood.naver.dto.SearchRegionRequestDto;
import com.humanedu.searchfood.naver.dto.SearchRegionResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListService {
  @Autowired
  private NaverAPIClient naverAPIClient;
  @Autowired
  private WishListRepository wishListRepository;

  /** 검색 **/
  public WishListDto search(String paramQuery) {
    WishListDto wishListDto = new WishListDto();

    // 1. NaverAPI 지역검색 호출해서 dto 값 매핑
    SearchRegionRequestDto searchRegionRequestDto = new SearchRegionRequestDto();
    searchRegionRequestDto.setQuery(paramQuery);

    SearchRegionResponseDto searchRegionResponseDto
      = naverAPIClient.searchRegion(searchRegionRequestDto);
    List<SearchRegionResponseDto.SearchRegionItem> searchRegionItemList
      = searchRegionResponseDto.getItems();
    if (searchRegionItemList != null && searchRegionItemList.size() > 0) {
      SearchRegionResponseDto.SearchRegionItem searchRegionItem = searchRegionItemList.get(0);

      wishListDto.setTitle(searchRegionItem.getTitle());
      wishListDto.setCategory(searchRegionItem.getCategory());
      wishListDto.setJibunAddress(searchRegionItem.getAddress());
      wishListDto.setRoadAddress(searchRegionItem.getRoadAddress());
      wishListDto.setHomepageLink(searchRegionItem.getLink());
    }

    // 2. NaverAPI 이미지검색 호출해서 dto값 매핑
    SearchImgRequestDto searchImgRequestDto = new SearchImgRequestDto();
    searchImgRequestDto.setQuery(paramQuery);

    SearchImgResponseDto searchImgResponseDto
        = naverAPIClient.searchImg(searchImgRequestDto);
    List<SearchImgResponseDto.SearchImgItem> imgRegionItemsList
        = searchImgResponseDto.getItems();
    if (imgRegionItemsList != null && imgRegionItemsList.size() > 0) {
      SearchImgResponseDto.SearchImgItem imgRegionItem = imgRegionItemsList.get(0);

      wishListDto.setImageLink(imgRegionItem.getLink());
    }

    return wishListDto;
  }

  /** 위시리스트 저장 **/
  public WishListVO addWish(WishListDto wishListDto) {
    // Storage(DB, Memory etc)에 위시정보 저장
    return wishListRepository.wishSave(wishListDto);
  }

  /** 위시리스트 조회 **/
  public List<WishListVO> allWish() {
    List<WishListVO> wishListVOList = wishListRepository.wishAll();
    return wishListVOList;
  }

  /** 방문횟수 추가 **/
  public WishListVO VisitCountAdd(int id) {
    return wishListRepository.VisitCountAdd(id);
  }

  /** 위시리스트 삭제 **/
  public WishListVO WishRemove(int id) {
    return wishListRepository.WishRemove(id);
  }
}
