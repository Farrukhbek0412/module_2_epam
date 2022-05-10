package com.epam.esm.dao.gift_certificate;

import com.epam.esm.domain.gift_certificate.GiftCertificate;
import com.epam.esm.domain.gift_certificate.GiftCertificateMapper;
import com.epam.esm.domain.tag.Tag;
import com.epam.esm.domain.tag.TagMapper;
import com.epam.esm.exception.DataNotFoundException;
import com.epam.esm.exception.UnknownDatabaseException;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class GiftCertificateDAOImpl implements GiftCertificateDAO {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public GiftCertificate create(GiftCertificate certificate) {
        String QUERY_CREATE_CERTIFICATE = """
                insert into gift_certificate(id, name, description, price, duration, create_date, last_update_date)
                            values(?, ?, ?, ?, ?, ?, ?);""";

        jdbcTemplate.update(QUERY_CREATE_CERTIFICATE,
                certificate.getId(), certificate.getName(), certificate.getDescription(), certificate.getPrice(),
                certificate.getDuration(), certificate.getCreateDate(), certificate.getLastUpdateDate());
        return certificate;
    }

    @Override
    public GiftCertificate get(UUID id) {
        String QUERY_GET_CERTIFICATE = "select * from gift_certificate where id = ?;";
        try {
            return jdbcTemplate.queryForObject(
                    QUERY_GET_CERTIFICATE,
                    new GiftCertificateMapper(), id
            );
        } catch (EmptyResultDataAccessException e) {
            throw new DataNotFoundException("no certificate found with id: " + id);
        }
    }

    @Override
    public List<GiftCertificate> getAll() {
        String QUERY_GET_ALL = "select * from gift_certificate;";
        return jdbcTemplate.query(QUERY_GET_ALL, new GiftCertificateMapper());
    }

    @Override
    @Transactional
    public int delete(UUID id) {
        String QUERY_DELETE_CERTIFICATE = "delete from gift_certificate where id = ?";
        String QUERY_DELETE_CONNECTIONS = "delete from gift_certificate_tag where gift_certificate_id = ?";
        try {
            jdbcTemplate.update(QUERY_DELETE_CONNECTIONS, id);
            return jdbcTemplate.update(QUERY_DELETE_CERTIFICATE, id);
        } catch (Exception e) {
            throw new DataNotFoundException("gift certificate with id " + id + " is not found");
        }
    }

    @Override
    public int update(GiftCertificate update) {
        String QUERY_UPDATE_CERTIFICATE = """
                update gift_certificate
                set name = ?,
                description = ?,
                price = ?,
                duration = ?,
                last_update_date = ?
                where id = ?;
                """;
        int updateResult = jdbcTemplate.update(
                QUERY_UPDATE_CERTIFICATE,
                update.getName(),
                update.getDescription(),
                update.getPrice(),
                update.getDuration(),
                update.getLastUpdateDate(),
                update.getId());
        if(updateResult == 1)
            return 1;
        throw new DataNotFoundException("gift certificate with id " + update.getId() + " is not found");
    }


    @Override
    public List<GiftCertificate> getFilteredGifts(
            String searchWord, String tagName, boolean doNameSort, boolean doDateSort, boolean isDescending
    ) {
        String QUERY_SEARCH_AND_GET_BY_TAG_NAME = getAllQuery(doNameSort, doDateSort, isDescending);
        try {
            return jdbcTemplate.query(
                    QUERY_SEARCH_AND_GET_BY_TAG_NAME,
                    new GiftCertificateMapper(),
                    searchWord,
                    tagName);
        } catch (EmptyResultDataAccessException e){
            throw new DataNotFoundException("no matching gift certificate found");
        }
    }

    @Override
    @Transactional
    public void createTagsWithGiftCertificate(UUID certificateId, List<Tag> tags) {
        String QUERY_CREATE_TAG = "insert into tag (id, name) values(?, ?);";
        String QUERY_CREATE_CONNECTION
                = "insert into gift_certificate_tag (tag_id, gift_certificate_id) values (?, ?);";

        tags.forEach(tag -> {
            Tag byName;
            try {
                byName = jdbcTemplate.queryForObject("select * from tag where name = ?", new TagMapper(), tag.getName());
            } catch (Exception e) {
                byName = null;
            }

            if(byName == null){
                tag.setId(UUID.randomUUID());
                jdbcTemplate.update(QUERY_CREATE_TAG, tag.getId(), tag.getName());
            } else
                tag.setId(byName.getId());
            jdbcTemplate.update(QUERY_CREATE_CONNECTION, tag.getId(), certificateId);
        });
    }

    private String getAllQuery(
            boolean doNameSort, boolean doDateSort, boolean isDescending
    ) {
        if (doNameSort) {
            if (doDateSort) {
                if (isDescending)
                    return "select *from get_certificates(?, ?) order by name, create_date desc;";
                return "select *from get_certificates(?, ?) order by name, create_date;";
            }
            if (isDescending)
                return "select *from get_certificates(?, ?) order by name desc;";

            return "select *from get_certificates(?, ?) order by name;";
        } else if (doDateSort) {
            if (isDescending)
                return "select *from get_certificates(?, ?) order by create_date desc;";
            return "select *from get_certificates(?, ?) order by create_date;";
        }
        return "select *from get_certificates(?, ?)";
    }
}
