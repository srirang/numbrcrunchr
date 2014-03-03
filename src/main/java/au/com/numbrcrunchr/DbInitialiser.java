package au.com.numbrcrunchr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManagerFactory;

import org.apache.commons.lang.StringUtils;
import org.hibernate.impl.SessionImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import au.com.numbrcrunchr.domain.DataException;
import au.com.numbrcrunchr.domain.StampDutyRepository;
import au.com.numbrcrunchr.domain.TaxRateRepository;

public class DbInitialiser implements BeanFactoryPostProcessor {
    private static final Logger LOGGER = Logger.getLogger(DbInitialiser.class
            .getName());

    private EntityManagerFactory entityManagerFactory;
    private StampDutyRepository stampDutyRepository;
    private TaxRateRepository taxRateRepository;
    private String sqlFilepath;

    public void initialiseDb(String sqlFilename) {
        final List<String> sqls = getSql(sqlFilename);
        Connection connection = ((SessionImpl) entityManagerFactory
                .createEntityManager().getDelegate()).getJDBCContext()
                .getConnectionManager().getConnection();
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(
                    new SingleConnectionDataSource(connection, true));
            jdbcTemplate.execute(new StatementCallback<Object>() {
                @Override
                public Object doInStatement(Statement statement)
                        throws SQLException, DataAccessException {
                    for (String sql : sqls) {
                        statement.execute(sql);
                        LOGGER.info("Executed :" + sql);
                    }
                    return null;
                }
            });
        } catch (DataAccessException e) {
            try {
                connection.close();
            } catch (SQLException e1) {
                LOGGER.severe("Error initialising database!" + e1 + e);
            }
        }
    }

    public boolean needsUpdating() {
        return !stampDutyRepository.hasAllData()
                || !taxRateRepository.hasAllData();
    }

    public List<String> getSql(String sqlFilename) {
        File file;
        BufferedReader reader = null;
        FileReader fileReader = null;
        try {
            file = new ClassPathResource(sqlFilename).getFile();
            fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);
        } catch (IOException e) {
            LOGGER.severe("Error initialising database: " + e.getMessage());
            throw new DataException(e);
        }
        List<String> sqls = new ArrayList<String>();
        String text = null;
        try {
            while ((text = reader.readLine()) != null) {
                if (StringUtils.isEmpty(text)) {
                    continue;
                }
                sqls.add(text);
            }
        } catch (IOException e) {
            LOGGER.severe("Error initialising database: " + e.getMessage());
            throw new DataException(e);
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    LOGGER.severe("Error initialising database: "
                            + e.getMessage());
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    LOGGER.severe("Error initialising database: "
                            + e.getMessage());
                }
            }
        }
        return sqls;
    }

    public void setEntityManagerFactory(
            EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void postProcessBeanFactory(
            ConfigurableListableBeanFactory beanFactory) throws BeansException {
        shutdownDatabase();
        LOGGER.info("Checking if db is up-to-date...");
        if (!needsUpdating()) {
            LOGGER.info("db up-to-date, no updates required");
            return;
        }
        LOGGER.info("do not-to-date, bringing up-to-date...");
        this.initialiseDb(this.sqlFilepath);
        LOGGER.info("Update Completed");
    }

    public void setSqlFilepath(String sqlFilepath) {
        this.sqlFilepath = sqlFilepath;
    }

    public void setStampDutyRepository(StampDutyRepository stampDutyRepository) {
        this.stampDutyRepository = stampDutyRepository;
    }

    public void setTaxRateRepository(TaxRateRepository taxRateRepository) {
        this.taxRateRepository = taxRateRepository;
    }

    public void shutdownDatabase() {
        Connection connection = ((SessionImpl) entityManagerFactory
                .createEntityManager().getDelegate()).getJDBCContext()
                .getConnectionManager().getConnection();
        try {
            new JdbcTemplate(new SingleConnectionDataSource(connection, false))
                    .update("SHUTDOWN IMMEDIATELY");
        } catch (DataAccessException e) {
            try {
                connection.close();
            } catch (SQLException e1) {
                LOGGER.severe("Error initialising database!" + e1 + e);
            }
        }
    }
}
