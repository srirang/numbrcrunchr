package au.com.numbrcrunchr.domain;

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
