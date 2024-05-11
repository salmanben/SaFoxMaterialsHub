package com.example.demo.observer_reservations;

import org.springframework.stereotype.Component;

import com.example.demo.service.ReservationService;
import com.example.demo.service.SmsSettingsService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Component
public class NotifyViaSms implements Observer {

    private final SmsSettingsService smsSettingsService;
    private final ReservationService reservationService;

    public NotifyViaSms(SmsSettingsService smsSettingsService, ReservationService reservationService) {
        this.smsSettingsService = smsSettingsService;
        this.reservationService = reservationService;
    }

    @Override
    public void update(ReservationState obs) {
         int id = ((ReservationStateImpl)obs).getId();
        String status = ((ReservationStateImpl)obs).getState();

        String to = reservationService.getReservation(id).getClient().getPhone();  
        String text = "Your reservation has been : " + status;

        String ACCOUNT_SID = smsSettingsService.getSmsSettings().getAccountSid();
        String AUTH_TOKEN = smsSettingsService.getSmsSettings().getAuthToken();
        String FROM_PHONE_NUMBER = smsSettingsService.getSmsSettings().getFromNumber();

        sendSms(ACCOUNT_SID, AUTH_TOKEN, FROM_PHONE_NUMBER, to, text);
    }

    public void sendSms(String accountSid, String authToken, String fromPhoneNumber, String toPhoneNumber, String text) {
        Twilio.init(accountSid, authToken);
        Message message = Message.creator(
                new PhoneNumber(toPhoneNumber),
                new PhoneNumber(fromPhoneNumber),
                text)
                .create();
    }

}
