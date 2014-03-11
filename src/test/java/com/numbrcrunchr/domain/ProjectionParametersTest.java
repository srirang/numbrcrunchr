package com.numbrcrunchr.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.numbrcrunchr.domain.ProjectionParameters;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/domainApplicationContext.xml" })
public class ProjectionParametersTest {
    @Autowired
    private ProjectionParameters projectionParameters;

    @Test
    public void checkNotNull() {
        assertNotNull(projectionParameters);
    }

    @Test
    public void checkDefaultValues() {
        assertEquals(3, projectionParameters.getCpi(), 0);
        assertEquals(3.5, projectionParameters.getSalaryIncreaseRate(), 0);
        assertEquals(4, projectionParameters.getRentIncreaseRate(), 0);
        assertEquals(5.5, projectionParameters.getCapitalGrowthRate(), 0);
    }
}
