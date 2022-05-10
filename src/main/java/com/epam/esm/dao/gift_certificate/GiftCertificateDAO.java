package com.epam.esm.dao.gift_certificate;

import com.epam.esm.dao.BaseDao;
import com.epam.esm.domain.gift_certificate.GiftCertificate;
import com.epam.esm.domain.tag.Tag;

import java.util.List;
import java.util.UUID;

public interface GiftCertificateDAO extends BaseDao<GiftCertificate> {
    int update(GiftCertificate updateCertificate);

    void createTagsWithGiftCertificate(UUID certificateId, List<Tag> tags);

    List<GiftCertificate> getFilteredGifts(
            String searchWord, String tagName, boolean doNameSort, boolean doDateSort, boolean isDescending
    );
}