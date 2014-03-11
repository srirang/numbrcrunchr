package com.numbrcrunchr.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.numbrcrunchr.web.FeedbackController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/domainApplicationContext.xml" })
@WebAppConfiguration
public class FeedbackControllerTest {
    @Autowired
    private FeedbackController controller;

    @Test
    public void checkDefaults() {
        assertNotNull(controller.getVersionDetails());
        assertNotNull(controller.getVersionDetails().getContactEmail());
    }

    @Test
    public void checkBasicProperties() {
        controller.setName("test");
        assertEquals("test", controller.getName());
        controller.setFeedbackComment("test content");
        assertEquals("test content", controller.getFeedbackComment());
    }
}
