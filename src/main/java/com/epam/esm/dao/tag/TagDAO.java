package com.epam.esm.dao.tag;

import com.epam.esm.dao.BaseDao;
import com.epam.esm.domain.tag.Tag;

import java.util.List;
import java.util.UUID;

public interface TagDAO extends BaseDao<Tag> {
    List<Tag> getGiftCertificateWithTag(UUID id);

    Tag getTagByName(String name);
}
