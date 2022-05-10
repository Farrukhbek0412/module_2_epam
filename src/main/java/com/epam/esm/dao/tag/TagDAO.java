package com.epam.esm.dao.tag;

import com.epam.esm.dao.BaseDao;
import com.epam.esm.domain.tag.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

public interface TagDAO extends BaseDao<Tag> {
    Tag getTagByName(String tagName);

    List<Tag> getGiftCertificateWithTag(UUID id);
}
