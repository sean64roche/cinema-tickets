package uk.gov.dwp.uc.pairtest;

import org.junit.Test;
import org.mockito.Mock;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import static junit.framework.TestCase.assertEquals;

class TicketServiceImplTest {
    long testAccountId = 1;
    TicketTypeRequest testAdultRequest = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2);
    TicketTypeRequest testChildRequest = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1);
    TicketTypeRequest testInfantRequest = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 3);

    // test prices are calculated correctly
    // test seat bookings line up with infant exception
    // test buying a mixture of ticket types
    // test exception thrown when buying zero tickets
    // test exception thrown when purchase amount is greater than 20
    // test exception thrown when account IDs are not greater than 0
    // test exception thrown against adult ticket necessity
}