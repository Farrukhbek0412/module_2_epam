package com.epam.esm.service.tag;

import com.epam.esm.dao.tag.TagDAO;
import com.epam.esm.dto.BaseResponseDTO;
import com.epam.esm.domain.tag.Tag;
import com.epam.esm.exception.BaseException;
import com.epam.esm.exception.DataNotFoundException;
import com.epam.esm.exception.UnknownDatabaseException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService{

    private final TagDAO tagDao;

    @Override
    public BaseResponseDTO<Tag> create(Tag tag) {

        isValidTag(tag);

        tag.setId(UUID.randomUUID());
        Tag create = tagDao.create(tag);

        if (create == null) {
            log.info("tag(name = "+tag.getName()+" ) is not created in the database");
            throw new UnknownDatabaseException("failed to create tag");
        }

        log.info("tag(name = "+tag.getName()+" ) is created in the database");
        return new BaseResponseDTO<>(HttpStatus.CREATED.value(), "tag created successfully", create);
    }

    private void isValidTag(Tag tag) {
        if (tag == null || tag.getName() == null || tag.getName().trim().length() <= 0) {
            log.info("tag(name = "+tag.getName()+" ) is invalid name");
            throw new BaseException(400, "invalid tag name");
        }
    }

    @Override
    public BaseResponseDTO<Tag> get(UUID id) {
        Tag tag = tagDao.get(id);

        if (tag == null) {
            log.info("tag(id = "+id+" ) does not exist in the database");
            throw new DataNotFoundException("tag not found");
        }
        log.info("tag(id = "+id+" ) is visible to the user");
        return new BaseResponseDTO<>(HttpStatus.OK.value(), "success", tag);
    }

    @Override
    public BaseResponseDTO<Tag> delete(UUID id) {
        int deleted = tagDao.delete(id);

        if (deleted != 1) {
            log.info("tag(id = "+id+" ) does not exist in the database");
            throw new DataNotFoundException("there is no tag with id: " + id + " to delete");
        }
        log.info("tag(id = "+id+" ) is removed from database");
        return new BaseResponseDTO<>(HttpStatus.OK.value(), "tag deleted successfully");
    }

    @Override
    public BaseResponseDTO<List<Tag>> getAll() {
        log.info("all tags are presented to the user");
        return new BaseResponseDTO<>(HttpStatus.OK.value(), "success", tagDao.getAll());
    }
}