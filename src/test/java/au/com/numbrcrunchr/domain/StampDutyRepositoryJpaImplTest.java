package au.com.numbrcrunchr.domain;

import org.springframework.beans.factory.annotation.Autowired;

public class StampDutyRepositoryJpaImplTest extends
		StampDutyRepositoryAbstractTestCase {
	@Autowired
	private StampDutyRepository stampDutyRepository;

	@Override
	public StampDutyRepository getStampDutyRepository() {
		return stampDutyRepository;
	}
}
