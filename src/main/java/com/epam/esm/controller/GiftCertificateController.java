package com.epam.esm.controller;

import com.epam.esm.dto.BaseResponseDTO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exception.InvalidFormatException;
import com.epam.esm.service.giftCertificate.GiftCertificateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/gift_certificate")
@AllArgsConstructor
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    @RequestMapping(value = "/create", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<?> create(@RequestBody GiftCertificateDTO giftCertificateDto) throws JsonProcessingException {
        BaseResponseDTO<GiftCertificateDTO> dto = giftCertificateService.create(giftCertificateDto);
        return ResponseEntity
                .status(dto.getStatus())
                .body(dto);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<?> get(@RequestParam("id") UUID id) {
        BaseResponseDTO<GiftCertificateDTO> dto = giftCertificateService.get(id);
        return ResponseEntity
                .status(dto.getStatus())
                .body(dto);
    }

    @RequestMapping(value = "/get_all", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<?> getFilteredGifts(
            @RequestParam(required = false) String searchWord,
            @RequestParam(required = false) String byTagName,
            @RequestParam(required = false) boolean doNameSort,
            @RequestParam(required = false) boolean doDateSort,
            @RequestParam(required = false) boolean isDescending
    ){
        BaseResponseDTO<List<GiftCertificateDTO>> filteredGifts =
                giftCertificateService.getFilteredGifts(searchWord, byTagName, doNameSort, doDateSort, isDescending);
        return ResponseEntity
                .status(filteredGifts.getStatus())
                .body(filteredGifts);

    }

    @RequestMapping(value = "/get_list", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<?> getAll() {
        BaseResponseDTO<List<GiftCertificateDTO>> all = giftCertificateService.getAll();
        return ResponseEntity
                .status(all.getStatus())
                .body(all);
    }
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<?> delete(@RequestParam UUID id) {
        BaseResponseDTO<GiftCertificateDTO> delete = giftCertificateService.delete(id);
        return ResponseEntity
                .status(delete.getStatus())
                .body(delete);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<?> update(@RequestBody GiftCertificateDTO giftCertificateDto) {
        BaseResponseDTO<GiftCertificateDTO> update = giftCertificateService.update(giftCertificateDto);
        return ResponseEntity
                .status(update.getStatus())
                .body(update);
    }

}
