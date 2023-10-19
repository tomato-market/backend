package com.tomato.market.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.tomato.market.data.dto.ImageDto;
import com.tomato.market.data.dto.PostDto;

public interface BoardService {
	PostDto writePost(PostDto postDto);

	void uploadImages(Integer postNum, List<MultipartFile> files) throws IOException;

	Page<PostDto> getPostList(Pageable pageable);

	Page<PostDto> getPostSearchList(String keyword, Pageable pageable);

	ImageDto getPostImage(Integer postNum);

}
