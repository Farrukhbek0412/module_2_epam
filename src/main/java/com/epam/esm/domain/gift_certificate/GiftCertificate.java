package com.epam.esm.domain.gift_certificate;

import com.epam.esm.domain.tag.Tag;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GiftCertificate {

    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss:SSS")
    private LocalDateTime createDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss:SSS")
    private LocalDateTime lastUpdateDate;
    //@JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Tag> tags;

}