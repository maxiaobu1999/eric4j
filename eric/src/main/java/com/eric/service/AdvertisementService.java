package com.eric.service;

import com.eric.repository.entity.AdvertisementEntity;

import java.util.List;

public interface AdvertisementService {
    List<AdvertisementEntity> selectAll();
}
