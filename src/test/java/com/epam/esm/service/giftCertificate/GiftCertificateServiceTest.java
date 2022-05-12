package com.epam.esm.service.giftCertificate;

import com.epam.esm.dao.gift_certificate.GiftCertificateDAO;
import com.epam.esm.domain.gift_certificate.GiftCertificate;
import com.epam.esm.dto.BaseResponseDTO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exception.DataNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.InheritingConfiguration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateServiceTest {
    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    @Mock
    private GiftCertificateDAO giftCertificateDao;
    @Mock
    private ModelMapper modelMapper;

    private GiftCertificate giftCertificate;
    private GiftCertificateDTO giftCertificateDto;

    @BeforeEach
    public void setup() {
        giftCertificate = new GiftCertificate(
                UUID.randomUUID(),
                "gift-test",
                "description of gift-test",
                BigDecimal.valueOf(777.0),
                10,
                LocalDateTime.now(),
                LocalDateTime.now(),
                null);
        giftCertificateDto = new GiftCertificateDTO(
                giftCertificate.getId(),
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                giftCertificate.getCreateDate(),
                giftCertificate.getLastUpdateDate(),
                null
        );
    }

    @Test
    public void testCreateGiftCertificate() {
        given(giftCertificateDao.create(giftCertificate)).willReturn(giftCertificate);
        given(modelMapper.map(giftCertificateDto, GiftCertificate.class)).willReturn(giftCertificate);

        BaseResponseDTO<GiftCertificateDTO> test = giftCertificateService.create(giftCertificateDto);

        assertEquals(201, test.getStatus());
        assertEquals("success", test.getMessage());
    }

    @Test
    public void  testGetGiftCertificateById() {
        given(giftCertificateDao.get(giftCertificate.getId())).willReturn(giftCertificate);
        given(modelMapper.map(giftCertificate, GiftCertificateDTO.class))
                .willReturn(giftCertificateDto);
        BaseResponseDTO<GiftCertificateDTO> test = giftCertificateService.get(giftCertificate.getId());

        assertEquals(200, test.getStatus());
        assertEquals("success", test.getMessage());
        assertNotNull(test.getData());
    }

    @Test
    public void testUpdateGiftCertificate() {
        given(giftCertificateDao.get(giftCertificate.getId())).willReturn(giftCertificate);
        given(giftCertificateDao.update(giftCertificate)).willReturn(1);
        given(modelMapper.getConfiguration()).willReturn(new InheritingConfiguration());
        doNothing().when(modelMapper).map(giftCertificateDto, giftCertificate);
        giftCertificateDto.setId(giftCertificate.getId());

        BaseResponseDTO<GiftCertificateDTO> update = giftCertificateService.update(giftCertificateDto);

        assertEquals(200, update.getStatus());
        assertEquals("success", update.getMessage());
        verify(giftCertificateDao, times(1)).get(giftCertificate.getId());
        verify(giftCertificateDao, times(1)).update(giftCertificate);
    }

    @Test
    public void testUpdateGiftCertificateThrowsException() {
        given(giftCertificateDao.get(giftCertificate.getId())).willReturn(giftCertificate); //old
        given(modelMapper.getConfiguration()).willReturn(new InheritingConfiguration());
        doNothing().when(modelMapper).map(giftCertificateDto, giftCertificate);
        giftCertificateDto.setId(giftCertificate.getId());

        given(giftCertificateDao.update(giftCertificate)).willReturn(0);

        assertThrows(DataNotFoundException.class, () -> giftCertificateService.update(giftCertificateDto));
        verify(giftCertificateDao, times(1)).get(giftCertificate.getId());
        verify(giftCertificateDao, times(1)).update(giftCertificate);
    }

    @Test
    public void testGetAllGiftCertificates() {
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        GiftCertificate gc1 =
                new GiftCertificate(UUID.randomUUID(), "gift1", "aa", BigDecimal.valueOf(10.0), 1, LocalDateTime.now(), LocalDateTime.now(), null);
        GiftCertificate gc2 =
                new GiftCertificate(UUID.randomUUID(), "gift2", "bb", BigDecimal.valueOf(10.0), 1, LocalDateTime.now(), LocalDateTime.now(), null);
        GiftCertificate gc3 =
                new GiftCertificate(UUID.randomUUID(), "gift3", "cc", BigDecimal.valueOf(10.0), 1, LocalDateTime.now(), LocalDateTime.now(), null);

        giftCertificates.add(gc1);
        giftCertificates.add(gc2);
        giftCertificates.add(gc3);

        given(giftCertificateDao.getAll()).willReturn(giftCertificates);

        BaseResponseDTO<List<GiftCertificateDTO>> all = giftCertificateService.getAll();

        assertEquals(3, all.getData().size());
        assertEquals(200, all.getStatus());
        assertEquals("success", all.getMessage());
        verify(giftCertificateDao, times(1)).getAll();
    }

    @Test
    public void testDeleteGiftCertificate() {
        given(giftCertificateDao.delete(giftCertificate.getId())).willReturn(1);

        BaseResponseDTO<GiftCertificateDTO> delete = giftCertificateService.delete(giftCertificate.getId());

        assertEquals(200, delete.getStatus());
        assertEquals("success", delete.getMessage());
        verify(giftCertificateDao, times(1)).delete(giftCertificate.getId());
    }

    @Test
    public void testDeleteGiftCertificateThrowsException() {
        given(giftCertificateDao.delete(giftCertificate.getId())).willReturn(0);

        assertThrows(DataNotFoundException.class, () -> giftCertificateService.delete(giftCertificate.getId()));
        verify(giftCertificateDao, times(1)).delete(giftCertificate.getId());
    }
}