package com.epam.esm.dao.tag;

import com.epam.esm.domain.tag.Tag;
import com.epam.esm.domain.tag.TagMapper;
import com.epam.esm.exception.DataNotFoundException;
import com.epam.esm.exception.UnknownDatabaseException;
import com.epam.esm.exception.tags.TagAlreadyExistException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@Slf4j
@AllArgsConstructor
public class TagDAOImpl implements TagDAO {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Tag create(Tag tag) {
        String QUERY_CREATE_TAG = "insert into tag (id, name) values(?, ?);";
        try{
            jdbcTemplate.update(QUERY_CREATE_TAG, tag.getId(), tag.getName());
            return tag;
        }catch (DataIntegrityViolationException e){
            log.info(e.getLocalizedMessage());
            throw new TagAlreadyExistException("tag (name = " + tag.getName() + " ) already exists");
        }
    }



    @Override
    public Tag get(UUID tagId) {
        String QUERY_GET_TAG = "select * from tag where id = ?;";
        try{
            return jdbcTemplate.queryForObject(QUERY_GET_TAG, new TagMapper(), tagId);
        }catch (EmptyResultDataAccessException e){
            log.info(e.getLocalizedMessage());
            throw new DataNotFoundException("Tag (id = " + tagId +" ) is not found" );
        }
    }


    @Override
    public List<Tag> getAll() {
        String QUERY_GET_ALL = "select * from tag;";
        try {
            return jdbcTemplate.query(QUERY_GET_ALL, new TagMapper());
        } catch (Exception e){
            log.error(e.getLocalizedMessage());
            throw new UnknownDatabaseException("There is not any tag in the database");
        }
        }

    @Override
    public int delete(UUID tagId) {
        String QUERY_DELETE_TAG = "delete from tag where id = ?;";
        try {
        return jdbcTemplate.update(QUERY_DELETE_TAG, tagId);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw new DataNotFoundException("tag (id =" + tagId + " ) is not found");
        }
    }
    @Override
    public Tag getTagByName(String tagName) {
        String QUERY_GET_TAG_BY_NAME ="select id, name from tag where name = ?";
        try {
            return jdbcTemplate.queryForObject(QUERY_GET_TAG_BY_NAME, new TagMapper() ,tagName);
        } catch (EmptyResultDataAccessException e) {
            log.info(e.getLocalizedMessage());
            throw new DataNotFoundException("Tag ( name=" + tagName +" ) is not found");
        }
    }

    @Override
    public List<Tag> getGiftCertificateWithTag(UUID id) {
        String QUERY_GET_GIFT_CERTIFICATE_BY_TAG = "select * from get_tags_by_gift_certificate_id(?)";
        try {
            return jdbcTemplate.query(QUERY_GET_GIFT_CERTIFICATE_BY_TAG, new TagMapper(), id);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw new DataNotFoundException("tag (id =" + id + " ) is not found");
        }
    }


}