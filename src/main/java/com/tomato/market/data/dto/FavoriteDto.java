package com.tomato.market.data.dto;

import com.tomato.market.data.entity.FavoriteEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteDto {
	private String userId;
	private Integer postNum;
	private Integer status;

	public static FavoriteDto toFavoriteDto(FavoriteEntity favoriteEntity) {
		return FavoriteDto.builder().build();
	}

	public static FavoriteEntity toFavoriteEntity(FavoriteDto favoriteDto) {
		return FavoriteEntity.builder().build();
	}
}
