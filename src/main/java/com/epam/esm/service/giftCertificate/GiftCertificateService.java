package com.epam.esm.service.giftCertificate;

import com.epam.esm.dto.BaseResponseDTO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.service.BaseService;

import java.util.List;


public interface GiftCertificateService extends BaseService<GiftCertificateDTO> {
    BaseResponseDTO<List<GiftCertificateDTO>> getFilteredGifts(
            String searchWord, String tagName, boolean doNameSort, boolean doDateSort, boolean isDescending
    );

    BaseResponseDTO<List<GiftCertificateDTO>> getAll();
    BaseResponseDTO<GiftCertificateDTO> update(GiftCertificateDTO update);
}
