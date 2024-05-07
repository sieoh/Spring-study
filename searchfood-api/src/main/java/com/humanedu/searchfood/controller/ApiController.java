package com.humanedu.searchfood.controller;

import com.humanedu.searchfood.wishlist.WishListDto;
import com.humanedu.searchfood.wishlist.WishListService;
import com.humanedu.searchfood.wishlist.WishListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class ApiController {
  @Autowired
  private WishListService wishListService;

  // 1. 검색 Api(GET)
  @GetMapping("/search")
  public WishListDto search(@RequestParam String searchQuery) {
    WishListDto wishListDto = wishListService.search(searchQuery);
    return wishListDto;
  }

  // 2. 위시리스트 추가 API(POST)
  @PostMapping("/wishadd")
  public WishListVO wishAdd(@RequestBody WishListDto wishListDto){
    return wishListService.addWish(wishListDto);
  }

  // 3. 위시리스트 목록 가져오기 API(GET)
  @GetMapping("/wishall")
  public List<WishListVO> wishAll() {
    return wishListService.allWish();
  }

  // 4. 방문 추가(Put)
  @PutMapping("/visitCountAdd/{id}")
  public WishListVO visitCountAdd(@PathVariable("id") int id) {
    return wishListService.VisitCountAdd(id);
  }

  // 5. 위시리스트 삭제(delete)
  @DeleteMapping("/wishRemove/{id}")
  public WishListVO WishRemove(@PathVariable("id") int id) {
    return wishListService.WishRemove(id);
  }
}
