package com.numbrcrunchr.web;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.numbrcrunchr.web.VersionDetails;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/domainApplicationContext.xml" })
public class VersionDetailsTest {

    @Autowired
    private VersionDetails versionDetails;

    @Test
    public void check() {
        assertNotNull(versionDetails.getContactEmail());
        assertNotNull(versionDetails.getVersionNumber());
    }
}
