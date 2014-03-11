package com.numbrcrunchr.domain;

import com.numbrcrunchr.domain.StampDutyRepository;
import com.numbrcrunchr.domain.StampDutyRepositoryMapImpl;

/**
 * 
 * @author AMIS005
 */
public class StampDutyRepositoryMapImplTest extends
        StampDutyRepositoryAbstractTestCase {
    @Override
    public StampDutyRepository getStampDutyRepository() {
        return new StampDutyRepositoryMapImpl();
    }
}
