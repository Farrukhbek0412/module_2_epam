package com.epam.esm.service.tag;

import com.epam.esm.dao.tag.TagDAO;
import com.epam.esm.dto.BaseResponseDTO;
import com.epam.esm.domain.tag.Tag;
import com.epam.esm.exception.BaseException;
import com.epam.esm.exception.DataNotFoundException;
import com.epam.esm.exception.UnknownDatabaseException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService{

    private final TagDAO tagDao;

    @Override
    public BaseResponseDTO<Tag> create(Tag tag) {

        checkIfTagValid(tag);

        tag.setId(UUID.randomUUID());
        Tag create = tagDao.create(tag);

        if (create == null) {
            throw new UnknownDatabaseException("failed to create tag");
        }

        return new BaseResponseDTO<>(HttpStatus.CREATED.value(), "tag created successfully", create);
    }

    private void checkIfTagValid(Tag tag) {
        if (tag == null || tag.getName() == null || tag.getName().trim().length() <= 0) {
            throw new BaseException(400, "invalid tag name");
        }
    }

    @Override
    public BaseResponseDTO<Tag> get(UUID id) {
        Tag tag = tagDao.get(id);

        if (tag == null) {
            throw new DataNotFoundException("tag not found");
        }
        return new BaseResponseDTO<>(HttpStatus.OK.value(), "success", tag);
    }

    @Override
    public BaseResponseDTO<Tag> delete(UUID id) {
        int deleted = tagDao.delete(id);

        if (deleted != 1) {
            throw new DataNotFoundException("there is no tag with id: " + id + " to delete");
        }
        return new BaseResponseDTO<>(HttpStatus.OK.value(), "tag deleted successfully");
    }

    @Override
    public BaseResponseDTO<List<Tag>> getAll() {
        return new BaseResponseDTO<>(HttpStatus.OK.value(), "success", tagDao.getAll());
    }
}