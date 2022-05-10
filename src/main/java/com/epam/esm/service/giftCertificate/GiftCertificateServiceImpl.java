package com.epam.esm.service.giftCertificate;

import com.epam.esm.dao.gift_certificate.GiftCertificateDAO;
import com.epam.esm.dao.tag.TagDAO;
import com.epam.esm.domain.gift_certificate.GiftCertificate;
import com.epam.esm.dto.BaseResponseDTO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exception.BaseException;
import com.epam.esm.exception.DataNotFoundException;
import com.epam.esm.exception.UnknownDatabaseException;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService{

    private final GiftCertificateDAO giftCertificateDao;
    private final TagDAO tagDao;
    private final ModelMapper modelMapper;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDAO giftCertificateDao, TagDAO tagDao, ModelMapper modelMapper) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public BaseResponseDTO<GiftCertificateDTO> create(GiftCertificateDTO giftCertificateDto) {

        checkIfGiftCertificateValid(giftCertificateDto);

        giftCertificateDto.setCreateDate(getCurrentTimeInIso8601());
        giftCertificateDto.setLastUpdateDate(getCurrentTimeInIso8601());
        giftCertificateDto.setId(UUID.randomUUID());
        GiftCertificate map = modelMapper.map(giftCertificateDto, GiftCertificate.class);
        GiftCertificate created = giftCertificateDao.create(map);

        if (created == null) {
            throw new UnknownDatabaseException("failed to create gift certificate");
        }

        createTags(giftCertificateDto);
        GiftCertificateDTO certificateDto = modelMapper.map(map, GiftCertificateDTO.class);
        return new BaseResponseDTO<>(HttpStatus.CREATED.value(), "success", certificateDto);
    }

    private void checkIfGiftCertificateValid(GiftCertificateDTO gc) {
        if (gc == null || gc.getName() == null || gc.getName().trim().length() == 0) {
            throw new BaseException(400, "invalid gift certificate name");
        }

        if (
                (gc.getDuration()!= null && gc.getDuration() < 0)
                        || (gc.getPrice() != null && gc.getPrice().compareTo(BigDecimal.ZERO) < 0)
        ) {
            throw new BaseException(400, "price or duration is not preferable");
        }
    }

    private void createTags(GiftCertificateDTO giftCertificateDto) {
        if (giftCertificateDto.getTags() != null && giftCertificateDto.getTags().size() != 0) {
            giftCertificateDao.createTagsWithGiftCertificate(giftCertificateDto.getId(), giftCertificateDto.getTags());
        }
    }

    @Override
    public BaseResponseDTO<GiftCertificateDTO> get(UUID id) {
        GiftCertificate giftCertificate = giftCertificateDao.get(id);
        if (giftCertificate == null) {
            throw new DataNotFoundException("gift certificate not found");
        }
        GiftCertificateDTO certificateDto = modelMapper.map(giftCertificate, GiftCertificateDTO.class);
        return new BaseResponseDTO<>(HttpStatus.OK.value(), "success", certificateDto);
    }

    @Override
    public BaseResponseDTO<GiftCertificateDTO> delete(UUID id) {
        int delete = giftCertificateDao.delete(id);

        if (delete != 1) {
            throw new UnknownDatabaseException("failed to delete gift certificate");
        }
        return new BaseResponseDTO<>(HttpStatus.OK.value(), "success");
    }


    @Override
    public BaseResponseDTO<List<GiftCertificateDTO>> getAll() {
        List<GiftCertificate> all = giftCertificateDao.getAll();
        return new BaseResponseDTO<>(HttpStatus.OK.value(), "success", convertToDto(all));
    }

    List<GiftCertificateDTO> convertToDto(List<GiftCertificate> certificates) {
        return certificates.stream()
                .map((certificate) ->
                        modelMapper.map(certificate, GiftCertificateDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public BaseResponseDTO<List<GiftCertificateDTO>> getFilteredGifts(
            String searchWord, String tagName, boolean doNameSort, boolean doDateSort, boolean isDescending
    ) {
        List<GiftCertificate> filteredGifts = giftCertificateDao.getFilteredGifts(
                searchWord, tagName, doNameSort, doDateSort, isDescending);

        if (filteredGifts.size() == 0)
            return new BaseResponseDTO<>(204, "no certificates found");


        return new BaseResponseDTO<>(HttpStatus.OK.value(), "success",
                convertToDto(addTagsToGiftCertificates(filteredGifts)));
    }

    private List<GiftCertificate> addTagsToGiftCertificates(List<GiftCertificate> giftCertificateDtos) {
        return giftCertificateDtos.stream().peek(certificate ->
                        certificate.setTags(tagDao.getGiftCertificateWithTag(certificate.getId())))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BaseResponseDTO<GiftCertificateDTO> update(GiftCertificateDTO update) {
        GiftCertificate old = giftCertificateDao.get(update.getId());

        update.setLastUpdateDate(getCurrentTimeInIso8601());

        checkIfGiftCertificateValid(update);

        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(update, old);

        int result = giftCertificateDao.update(old);

        if (result == 1) {
            createTags(update);
            return new BaseResponseDTO<>(HttpStatus.OK.value(), "success");
        }
        return new BaseResponseDTO<>(500, "failed to update");
    }

    public LocalDateTime getCurrentTimeInIso8601() {
        return ZonedDateTime.now( ZoneOffset.UTC ).toLocalDateTime();
    }
}