package com.numbrcrunchr.domain;

import org.springframework.beans.factory.annotation.Autowired;

import com.numbrcrunchr.domain.StampDutyRepository;

public class StampDutyRepositoryJpaImplTest extends
        StampDutyRepositoryAbstractTestCase {
    @Autowired
    private StampDutyRepository stampDutyRepository;

    @Override
    public StampDutyRepository getStampDutyRepository() {
        return stampDutyRepository;
    }
}
