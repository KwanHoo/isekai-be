package com.isekai.ssgserver.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.isekai.ssgserver.member.dto.BundleProductReqDto;
import com.isekai.ssgserver.member.dto.CategoryLReqDto;
import com.isekai.ssgserver.member.dto.CategoryMReqDto;
import com.isekai.ssgserver.member.dto.FavoriteCountResponseDto;
import com.isekai.ssgserver.member.dto.FavoriteDelRequestDto;
import com.isekai.ssgserver.member.dto.FavoritePutReqDto;
import com.isekai.ssgserver.member.dto.SellerReqDto;
import com.isekai.ssgserver.member.dto.SingleProductReqDto;
import com.isekai.ssgserver.member.enums.FavoriteDivision;
import com.isekai.ssgserver.member.service.MemberFavoriteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members/favorite")
@Slf4j
@Tag(name = "favorite", description = "회원 상품 좋아요 API document")
public class MemberFavoriteController {
	private final MemberFavoriteService memberFavoriteService;

	/*  뭐든 찜하기
	 *	identifier
	 * */
	// @PostMapping("/{identifier}/{division}")
	@PostMapping("")
	@Operation(summary = "뭐든 상품 찜하기", description = "회원이 찜한 것을 저장합니다.")
	public ResponseEntity<Void> putFavoriteTotal(
		@RequestBody FavoritePutReqDto favoritePutReqDto1,
		@PathVariable String identifier,
		@PathVariable FavoriteDivision division) {
		log.info("MemberFavoriteController.putFavoriteTotal");
		log.info("MemberFavoriteController.putFavoriteTotal");
		FavoritePutReqDto favoritePutReqDto = new FavoritePutReqDto();

		memberFavoriteService.postFavoriteTotal(favoritePutReqDto);
		// memberFavoriteService.postFavoriteTotal(favoritePutReqDto1, identifier, division);

		return ResponseEntity.ok().build();
	}

	/* 단일상품찜하기
	 *	product_id
	 * 	division = 0
	 * */
	@PostMapping("/single-product")
	@Operation(summary = "단일 상품 찜하기", description = "회원이 단일 상품 찜한 것을 저장합니다.")
	public ResponseEntity<Void> putSingleProduct(
		@RequestBody SingleProductReqDto singleProductReqDto) {

		log.info("MemberFavoriteController.putSingleProduct");
		log.info("singleProductReqDto = " + singleProductReqDto);

		memberFavoriteService.postSingleProduct(singleProductReqDto);
		return ResponseEntity.ok().build();
	}

	/* 묶음상품찜하기
	 *	bundel_id
	 * 	division = 1
	 * */
	@PostMapping("/bundle-product")
	@Operation(summary = "묶음 상품 찜하기", description = "회원이 묶음 상품 찜한 것을 저장합니다.")
	public ResponseEntity<Void> putBundleProduct(
		@RequestBody BundleProductReqDto bundleProductReqDto) {

		log.info("MemberFavoriteController.putBundleProduct");
		log.info("bundleProductReqDto = " + bundleProductReqDto);

		memberFavoriteService.postBundleProduct(bundleProductReqDto);
		return ResponseEntity.ok().build();
	}

	/* 카테고리L 찜하기
	 *	category_l_id
	 * 	division = 2
	 * */
	@PostMapping("/categoryL")
	@Operation(summary = "대 카테고리 찜하기", description = "회원이 대 카테고리 찜한 것을 저장합니다.")
	public ResponseEntity<Void> putCategoryL(
		@RequestBody CategoryLReqDto categoryLReqDto) {
		log.info("MemberFavoriteController.putCategoryL");
		log.info("categoryLReqDto = " + categoryLReqDto);

		memberFavoriteService.postCategoryL(categoryLReqDto);
		return ResponseEntity.ok().build();
	}

	/* 카테고리M 찜하기
	 *	category_m_id
	 * 	division = 3
	 * */
	@PostMapping("/categoryM")
	@Operation(summary = "중 카테고리 찜하기", description = "회원이 중 카테고리 찜한 것을 저장합니다.")
	public ResponseEntity<Void> putCategoryM(
		@RequestBody CategoryMReqDto categoryMReqDto) {
		log.info("MemberFavoriteController.putCategoryM");
		log.info("categoryMReqDto = " + categoryMReqDto);

		memberFavoriteService.postCategoryM(categoryMReqDto);
		return ResponseEntity.ok().build();
	}

	/* 판매자,브랜드 찜하기
	 *	category_m_id
	 * 	division = 4
	 * */
	@PostMapping("/seller")
	@Operation(summary = "브랜드 찜하기", description = "회원이 브랜드 찜한 것을 저장합니다.")
	public ResponseEntity<Void> putSeller(
		@RequestBody SellerReqDto sellerReqDto) {
		log.info("MemberFavoriteController.putSeller");
		log.info("sellerReqDto = " + sellerReqDto);

		memberFavoriteService.postSeller(sellerReqDto);
		return ResponseEntity.ok().build();
	}

	// 찜 목록 조회
	// 동적 - 쿼리dsl로
	// --> 상품전체 / 브랜드&스토어 / 카테고리 /
	// 30개씩 페이지네이션
	@GetMapping("/list")
	@Operation(summary = "찜 목록 조회", description = "찜 목록을 조회합니다.")
	public ResponseEntity<?> getFavoriteList() {
		return null;
	}

	// 찜인지
	// 해당 아이템이 찜인지 T/F 반환
	@GetMapping("/check")
	@Operation(summary = "찜인지 여부", description = "찜인지 여부를 조회합니다.")
	public ResponseEntity<Boolean> getIsFavorite(
		@RequestParam(value = "uuid") String uuid,
		@RequestParam(value = "division") byte division,
		@RequestParam(value = "identifier") String identifier
	) {
		log.info("MemberFavoriteController.getIsFavorite");

		boolean result = memberFavoriteService.checkFavoriteExists(uuid, division, identifier);

		return ResponseEntity.ok(result);
	}

	// 찜 갯수 조회
	// 상품 찜, 카테고리 찜, 브랜드 & 스토어 찜 개수
	// 3개를 다 던져주어야함
	@GetMapping("/number")
	@Operation(summary = "찜 갯수", description = "찜 상품, 브랜드, 카테고리 갯수를 구합니다.")
	public ResponseEntity<FavoriteCountResponseDto> getCountFavorite() {
		log.info("MemberFavoriteController.getCountFavorite");
		FavoriteCountResponseDto favoriteCountResponseDto = memberFavoriteService.countFavorites();

		return ResponseEntity.ok(favoriteCountResponseDto);
	}

	// 찜 선택들 삭제
	@DeleteMapping("/selects")
	@Operation(summary = "찜 선택들 삭제", description = "찜 선택한거 모두 삭제합니다.")
	public ResponseEntity<Void> deleteFavoriteList(
		@RequestBody FavoriteDelRequestDto favoriteDelRequestDto) {
		log.info("MemberFavoriteController.deleteFavoriteList");
		log.info("favoriteDelRequestDto = " + favoriteDelRequestDto);

		memberFavoriteService.deleteFavorites(favoriteDelRequestDto);
		return ResponseEntity.ok().build();
	}

	// 찜 1개 삭제
	@DeleteMapping("/{favorite_id}")
	@Operation(summary = "찜 하나 삭제", description = "찜 하나를 삭제합니다.")
	public ResponseEntity<Void> deleteFavoriteSeelct(
		@PathVariable Long favoriteId) {
		log.info("MemberFavoriteController.deleteFavoriteSeelct");
		log.info("favoriteId = " + favoriteId);

		memberFavoriteService.deleteFavoriteOne(favoriteId);
		return ResponseEntity.ok().build();
	}

}
