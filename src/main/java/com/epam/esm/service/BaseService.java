package com.epam.esm.service;

import com.epam.esm.dto.BaseResponseDTO;

import java.util.UUID;

public interface BaseService<T> {
    BaseResponseDTO<T> create(T t);
    BaseResponseDTO<T> get(UUID id);
    BaseResponseDTO<T> delete(UUID id);
}