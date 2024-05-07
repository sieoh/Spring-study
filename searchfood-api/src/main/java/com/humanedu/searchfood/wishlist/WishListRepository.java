package com.humanedu.searchfood.wishlist;

import com.humanedu.searchfood.utils.StringUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class WishListRepository {
  // List 자바메모리 위시 정보
  private List<WishListVO> wishListVOList = new ArrayList<>();

  /** 위시리스트 저장 **/
  public WishListVO wishSave(WishListDto wishListDto) {
    WishListVO wishListVO = new WishListVO();
    wishListVO.setTitle(StringUtils.removeTags(wishListDto.getTitle()));
    wishListVO.setCategory(wishListDto.getCategory());
    wishListVO.setJibunAddress(wishListDto.getJibunAddress());
    wishListVO.setRoadAddress(wishListDto.getRoadAddress());
    wishListVO.setHomepageLink(wishListDto.getHomepageLink());
    wishListVO.setImageLink(wishListDto.getImageLink());
    wishListVO.setVisitIs(false);
    wishListVO.setVisitCount(wishListVO.getVisitCount());
    wishListVO.setLastVisitDate(null);

    wishListVOList.add(wishListVO);

    return wishListVO;
  }

  /** 위시리스트 조회 **/
  public List<WishListVO> wishAll() {
    return wishListVOList;
  }

  /** 방문횟수 추가 **/
  public WishListVO VisitCountAdd(int id) {
//    WishListVO wishlistVO = new WishListVO();
//    // 계속 새로운 객체를 불러오는거라서 0 + 1 -> 0 + 1 -> 무한반복
//    wishlistVO.setTitle(wishListVO.getTitle());
//    wishlistVO.setId(id);
//    wishlistVO.setVisitIs(true);
//    wishlistVO.setVisitCount(wishlistVO.getVisitCount() + 1);
//    wishlistVO.setLastVisitDate(LocalDateTime.now());

    WishListVO wishListVO = wishListVOList.get(id);
    wishListVO.setVisitIs(true);
    wishListVO.setVisitCount(wishListVO.getVisitCount() + 1);
    wishListVO.setLastVisitDate(LocalDateTime.now());

    return wishListVO;
  }

  /** 위시리스트 삭제 **/
  public WishListVO WishRemove(int id) {
    return wishListVOList.remove(id);
  }
}
