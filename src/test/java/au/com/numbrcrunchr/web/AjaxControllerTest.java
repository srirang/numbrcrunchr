package au.com.numbrcrunchr.web;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AjaxControllerTest {

	@Test
	public void checkIE9() {
		assertFalse(new AjaxController()
				.isIE9("Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0; Trident/5.0)"));
		assertTrue(new AjaxController()
				.isIE9("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)"));
	}
}
