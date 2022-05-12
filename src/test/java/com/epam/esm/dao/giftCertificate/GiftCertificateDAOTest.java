package com.epam.esm.dao.giftCertificate;

import com.epam.esm.config.TestConfig;
import com.epam.esm.dao.gift_certificate.GiftCertificateDAO;
import com.epam.esm.domain.gift_certificate.GiftCertificate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = { TestConfig.class },
        loader = AnnotationConfigContextLoader.class)
public class GiftCertificateDAOTest {

    @Autowired
    private GiftCertificateDAO dao;

    @Resource
    private JdbcTemplate jdbcTemplate;


    @BeforeEach
    void setUp() {

    }


    @Test
    public void testCreateGiftCertificate() {
        createTestObjects();
        GiftCertificate gc =
                new GiftCertificate(UUID.randomUUID(), "test", "test desc", BigDecimal.valueOf(123.0), 12, LocalDateTime.now(), LocalDateTime.now(), null);
        GiftCertificate giftCertificate = dao.create(gc);

        assertEquals("test", giftCertificate.getName());
    }
    @Test
    public void canGetGiftCertificateById() {
        GiftCertificate gift = dao.get(UUID.fromString("64eeb184-f873-491a-b2cc-aaee00c26f77"));

        assertNotNull(gift);
    }

    private void createTestObjects(){
        jdbcTemplate.update(
                "create table gift_certificate (" +
                        "id uuid not null primary key, " +
                        "name character varying," +
                        "description character varying," +
                        "price numeric," +
                        "duration int," +
                        "create_date timestamp," +
                        "last_update_date timestamp )");

        jdbcTemplate.update(
                "insert into gift_certificate values('badc0e82-972c-4bef-9879-c003d7352bd0', 'gift1', 'desc1', 1.0, 10, '2022-05-09 13:54:04.985000', '2022-05-09 13:54:04.985000');\n" +
                        "insert into gift_certificate values('64eeb184-f873-491a-b2cc-aaee00c26f77', 'gift2', 'desc2', 1.0, 11, '2022-05-11 11:34:06.985000', '2022-05-11 11:34:06.985000');\n" +
                        "insert into gift_certificate values('fce5b289-6ae4-4aee-b0d1-5ba6580ac71f', 'gift3', 'desc3', 1.0, 12, '2022-05-10 17:51:09.985000', '2022-05-10 17:51:09.985000');\n" +
                        "insert into gift_certificate values('c57e4db1-6029-44cf-a870-5a0c30fd6d83', 'gift4', 'desc4', 1.0, 13, '2022-05-09 10:14:01.985000', '2022-05-09 10:14:01.985000');");
    }
}