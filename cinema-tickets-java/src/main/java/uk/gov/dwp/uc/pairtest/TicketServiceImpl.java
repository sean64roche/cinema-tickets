package uk.gov.dwp.uc.pairtest;

import thirdparty.paymentgateway.TicketPaymentServiceImpl;
import thirdparty.seatbooking.SeatReservationServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidAccountIdException;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.exception.NoAdultTicketPurchasedException;

public class TicketServiceImpl implements TicketService {
    /**
     * Should only have private methods other than the one below.
     */

    @Override
    public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {
        // Ticket prices handled in pennies or £0.01
        final int adultTicketPrice = 2000, childTicketPrice = 1000, infantTicketPrice = 0;
        int ticketsTotal = 0, seatsTotal = 0, priceTotal = 0;
        boolean adultTicketPurchased = false;

        this.checkForValidAccountId(accountId);

        for (TicketTypeRequest ticketTypeRequest : ticketTypeRequests) {
            ticketsTotal += ticketTypeRequest.getNoOfTickets();
            switch (ticketTypeRequest.getTicketType()) {
                case ADULT:
                    adultTicketPurchased = true;
                    priceTotal += ticketTypeRequest.getNoOfTickets() * adultTicketPrice;
                    seatsTotal += ticketTypeRequest.getNoOfTickets();
                    break;
                case CHILD:
                    priceTotal += ticketTypeRequest.getNoOfTickets() * childTicketPrice;
                    seatsTotal += ticketTypeRequest.getNoOfTickets();
                    break;
                case INFANT:
                    priceTotal += ticketTypeRequest.getNoOfTickets() * infantTicketPrice;
                    break;
            }
        }

        this.checkForAdultTicket(adultTicketPurchased);
        this.checkForPurchaseExceptions(ticketsTotal);

        new TicketPaymentServiceImpl().makePayment(accountId, priceTotal);
        new SeatReservationServiceImpl().reserveSeat(accountId, seatsTotal);
    }

    private void checkForAdultTicket(boolean adultTicketPurchased) {
        if (!adultTicketPurchased) {
            throw new NoAdultTicketPurchasedException("In order to check out you are required to " +
                    "purchase at least one adult ticket. Please amend your order or contact support.");
        }
    }

    private void checkForValidAccountId(long accountId) {
        if (accountId <= 0) {
            throw new InvalidAccountIdException("Your account ID appears to be invalid. " +
                    "Please contact support.");
        }
    }
    private void checkForPurchaseExceptions(int ticketsTotal) {

        if (ticketsTotal > 20) {
            throw new InvalidPurchaseException("You have tried to purchase too many tickets. " +
                    "Please make sure you are purchasing no more than 20 tickets.");
        }
        if (ticketsTotal < 1) {
            throw new InvalidPurchaseException("You have not chosen any tickets for purchase. " +
                    "Please make sure you have selected the correct number of tickets.");
        }
    }
}
