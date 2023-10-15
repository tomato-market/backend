package com.tomato.market.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tomato.market.data.dto.ImageDto;
import com.tomato.market.data.dto.PostDto;
import com.tomato.market.data.dto.PostListResponseDto;
import com.tomato.market.data.dto.PostResponseDto;
import com.tomato.market.service.BoardService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class BoardController {
	private final Logger logger = LoggerFactory.getLogger(BoardController.class);

	private final BoardService boardService;

	@Autowired
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}


	// 계획을 수정
	// 기본 등록 메소드 부터 만든 후
	// 조회 메소드를 만들기로 함
	@PostMapping(value = "/board/writePost", consumes = {MediaType.APPLICATION_JSON_VALUE,
		MediaType.MULTIPART_FORM_DATA_VALUE})
	public PostResponseDto writePost(
		@RequestPart(value = "postDto") @Valid PostDto postDto,
		@RequestPart(value = "images", required = false) List<MultipartFile> files)
		throws IOException { // 추후 @Valid // 이미지도 받아와야 함
		logger.info("BoardController.writePost() is called");
		logger.info("BoardController.writerPost() : Validation 검증 성공");

		logger.warn(postDto.toString());
		logger.warn(files.toString());

		// 게시글 등록
		PostDto savedPost = boardService.writePost(postDto);
		logger.info("BoardController.writePost() : 게시글 저장 성공");

		Integer postNum = savedPost.getPostNum(); //

		if (files != null) {
			logger.info("BoardController.writePost() : 이미지 개수-" + files.size());
			// postID와 연관하여 이미지 등록 // Image API를 따로 분리? : DB간 관계가 성립되지 않는 문제 있음
			boardService.uploadImages(postNum, files);
			logger.info("BoardController.writePost() : 이미지 저장 성공");
		}

		// 결과 리턴
		return PostResponseDto.builder()
			.status(HttpStatus.OK)
			.message("게시글 등록 성공")
			.build();
	}

	// 조회 삭제 수정 : 각각 별도 이슈로 분리?
//	PutMapping?
//	updatePost() {}


	@GetMapping("/board/getPostList")
	public PostListResponseDto getPostList() { // 리턴 타입을 리스트로?
		// 모든 게시글 조회 -> 페이징 처리 예정, int page 받기
		logger.info("BoardController.getPostList() is called");

//		List<PostDto> postList = boardService.getAllPostList();
		List<PostDto> postList = boardService.getPostList();
		if (postList == null) { // 잘못됨
			logger.warn("BoardController.getPostList() : 게시글 리스트를 찾을 수 없음");
			// throw new Error // Service에서 처리?
		}
		// 게시글 리스트를 받음
		logger.info("BoardController.getPostList() : 게시글 리스트를 찾음");

		// 찾은 postList에서 각 Post의 ID로 Image를 찾음
		List<ImageDto> imageList = new ArrayList<>();
		for (int i = 0; i < postList.size(); i++) {
//			imageList.add(
//			 썸네일로 사용할 Image 1개만 필요
//				boardService.getPostImage(postList.get(i).getPostNum())
//			 이미지가 없는 경우는 Service 단에서 Default 이미지 전송
//			);
		}


		// Return
		// 게시글 List 첨부
		// 이미지 List 첨부
		// 하나의 응답 DTO로 변환하여 반환
		return PostListResponseDto.builder()
			.status(HttpStatus.OK)
			.message("게시글 리스트 불러오기 성공")
			.content(postList)
			.build();
	}

//	@PostMapping("/board/registerPost")
//	public void registerPost() {
//
//	}

//	@PostMapping("/board/registerImage")
//	public void uploadImage() { // 이미지 등록 메소드를 별도 처리?
//		// 게시글 이미지 저장 관련 코드
//		// 이미지는 다른 controller 메소드 사용
//		// multiPartFile로 받음 .getOriginalFilename(),
//		// 이미지는 각각 uuid를 가짐
//		// File 객체 생성, uuid+주소
//		// .transferTo()로 서버에 저장
//
//		// 경로를 기준, base64?
//
//
//		// 게시글 이미지 조회 관련 코드
//		// List<ImageDto> list = findPostImageListById(postId)
//		// for() { list.add
//		// 	if(image == null) { .add(default Image)
//
//	}


}
