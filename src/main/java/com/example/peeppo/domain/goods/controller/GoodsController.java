package com.example.peeppo.domain.goods.controller;

import com.example.peeppo.domain.goods.dto.*;
import com.example.peeppo.domain.goods.service.GoodsService;
import com.example.peeppo.global.responseDto.ApiResponse;
import com.example.peeppo.global.security.UserDetailsImpl;
import com.example.peeppo.global.utils.ResponseUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goods")
public class GoodsController {
    private final GoodsService goodsService;

    @PostMapping
    public ApiResponse<GoodsResponseDto> goodsCreate(@RequestPart(value = "data") GoodsRequestDto goodsRequestDto,
                                                     @RequestPart(value = "images") List<MultipartFile> images,
                                                     @RequestPart(value = "wanted") WantedRequestDto wantedRequestDto,
                                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return goodsService.goodsCreate(goodsRequestDto, images, wantedRequestDto, userDetails.getUser());
    }

    // 전체 게시물 조회
    @GetMapping
    public Page<GoodsListResponseDto> allGoods(@RequestParam("page") int page,
                                               @RequestParam("size") int size,
                                               @RequestParam("sortBy") String sortBy,
                                               @RequestParam("isAsc") boolean isAsc,
                                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if(userDetails!=null){
            return goodsService.allGoods(page -1, size, sortBy, isAsc,userDetails.getUser());
        }
        return goodsService.allGoodsEveryone(page - 1, size, sortBy, isAsc);
    }

    // 게시물 상세조회
    @GetMapping("/{goodsId}")
    public ApiResponse<GoodsResponseDto> getGoods(@PathVariable Long goodsId,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if(userDetails != null){
           return goodsService.getGoods(goodsId, userDetails.getUser());
        }
        return goodsService.getGoodsEveryone(goodsId);
    }

    @GetMapping("/pocket")
    public ApiResponse<PocketResponseDto> getMyGoods(@RequestParam("page") int page,
                                                     @RequestParam("size") int size,
                                                     @RequestParam("sortBy") String sortBy,
                                                     @RequestParam("isAsc") boolean isAsc,
                                                     @AuthenticationPrincipal UserDetailsImpl userDetails){

        return goodsService.getMyGoods(page - 1, size, sortBy, isAsc, userDetails.getUser().getUserId());
    }

    @GetMapping("/mypocket")
    public ApiResponse<List<GoodsResponseDto>> getMyGoodsWithoutPagenation(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseUtils.ok(goodsService.getMyGoodsWithoutPagenation(userDetails.getUser()));
    }

    @GetMapping("/pocket/{nickname}")
    public ApiResponse<UrPocketResponseDto> getPocket(@PathVariable String nickname,
                                                         @RequestParam("page") int page,
                                                         @RequestParam("size") int size,
                                                         @RequestParam("sortBy") String sortBy,
                                                         @RequestParam("isAsc") boolean isAsc,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return goodsService.getPocket(nickname, userDetails, page - 1, size, sortBy, isAsc);
    }

    @PutMapping("/{goodsId}")
    public ApiResponse<GoodsResponseDto> goodsUpdate(@PathVariable Long goodsId,
                                                     @RequestPart(value = "data") GoodsRequestDto requestDto,
                                                     @RequestPart(value = "images") List<MultipartFile> images,
                                                     @RequestPart(value = "wanted") WantedRequestDto wantedRequestDto) {

        return goodsService.goodsUpdate(goodsId, requestDto, images, wantedRequestDto);
    }

    @DeleteMapping("/{goodsId}")
    public ApiResponse<DeleteResponseDto> deleteGoods(@PathVariable Long goodsId,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) throws IllegalAccessException {
        return goodsService.deleteGoods(goodsId, userDetails.getUser().getUserId());
    }

    @GetMapping("/recent")
    public List<GoodsRecentDto> recentGoods(HttpServletResponse response) {
        return goodsService.recentGoods(response);
    }
}