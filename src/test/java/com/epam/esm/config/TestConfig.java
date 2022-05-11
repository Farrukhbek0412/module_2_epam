package com.epam.esm.config;

import com.epam.esm.dao.gift_certificate.GiftCertificateDAOImpl;
import com.epam.esm.dao.tag.TagDAOImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class TestConfig {

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:myDb;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public TagDAOImpl tagDAO(JdbcTemplate jdbcTemplate){
        return new TagDAOImpl(jdbcTemplate);
    }
    @Bean
    public GiftCertificateDAOImpl giftCertificateDao(JdbcTemplate jdbcTemplate) {
        return new GiftCertificateDAOImpl(jdbcTemplate);
    }
}
