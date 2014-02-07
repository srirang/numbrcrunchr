package au.com.numbrcrunchr.domain;

import java.util.HashMap;
import java.util.Map;

import org.springframework.orm.jpa.support.JpaDaoSupport;

/**
 * 
 * @author AMIS005
 */
public class AbstractJpaRepository extends JpaDaoSupport {
	protected Map<String, ?> parameters(String key, Object value) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put(key, value);
		return params;
	}
}
