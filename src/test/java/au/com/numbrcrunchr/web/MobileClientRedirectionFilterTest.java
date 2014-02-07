package au.com.numbrcrunchr.web;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MobileClientRedirectionFilterTest {
	@Test
	public void checkMobileUrl() {
		assertEquals("http://192.168.1.1:9012/test/mobile/page.jsf",
				MobileClientRedirectionFilter
						.getMobileUrl("http://192.168.1.1:9012/test/page.jsf"));
		assertEquals("http://192.168.1.1:9012/test/mobile/",
				MobileClientRedirectionFilter
						.getMobileUrl("http://192.168.1.1:9012/test/"));
		assertEquals("http://192.168.1.1:9012/mobile/testd",
				MobileClientRedirectionFilter
						.getMobileUrl("http://192.168.1.1:9012/testd"));
	}
}
