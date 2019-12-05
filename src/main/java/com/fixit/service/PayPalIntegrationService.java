package com.fixit.service;
import java.io.File
;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import urn.ebay.api.PayPalAPI.CreateRecurringPaymentsProfileReq;
import urn.ebay.api.PayPalAPI.CreateRecurringPaymentsProfileRequestType;
import urn.ebay.api.PayPalAPI.CreateRecurringPaymentsProfileResponseType;
import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.BillingPeriodDetailsType;
import urn.ebay.apis.eBLBaseComponents.BillingPeriodType;
import urn.ebay.apis.eBLBaseComponents.CreateRecurringPaymentsProfileRequestDetailsType;
import urn.ebay.apis.eBLBaseComponents.CreditCardDetailsType;
import urn.ebay.apis.eBLBaseComponents.CreditCardTypeType;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;
import urn.ebay.apis.eBLBaseComponents.RecurringPaymentsProfileDetailsType;
import urn.ebay.apis.eBLBaseComponents.ScheduleDetailsType;


import com.paypal.exception.ClientActionRequiredException;
import com.paypal.exception.HttpErrorException;
import com.paypal.exception.InvalidCredentialException;
import com.paypal.exception.InvalidResponseDataException;
import com.paypal.exception.MissingCredentialException;
import com.paypal.exception.SSLConfigurationException;
import com.paypal.sdk.exceptions.OAuthException;

@Service
public class PayPalIntegrationService {
public static String token=null;
	

   public void paymentRecurringUsingCreditCard(String howmuch){
	   RecurringPaymentsProfileDetailsType profileDetails = new RecurringPaymentsProfileDetailsType("2015-010-05T00:00:00:000Z");

	   BasicAmountType paymentAmount = new BasicAmountType(CurrencyCodeType.USD, "1.0");
	   BillingPeriodType period = BillingPeriodType.fromValue("Day");
	   int frequency = 10;
	   BillingPeriodDetailsType paymentPeriod = new BillingPeriodDetailsType(period, frequency, paymentAmount);

	   ScheduleDetailsType scheduleDetails = new ScheduleDetailsType();
	   scheduleDetails.setDescription("recurring billing");
	   scheduleDetails.setPaymentPeriod(paymentPeriod);

	   CreditCardDetailsType creditCard = new CreditCardDetailsType();
	   creditCard.setCreditCardNumber("4005550000000001");
	   creditCard.setCVV2("962");
	   creditCard.setExpMonth(05);
	   creditCard.setExpYear(2017); 
	   creditCard.setCreditCardType(CreditCardTypeType.fromValue("Visa"));

	   CreateRecurringPaymentsProfileRequestDetailsType createRPProfileRequestDetails = new CreateRecurringPaymentsProfileRequestDetailsType(profileDetails, scheduleDetails);
	   createRPProfileRequestDetails.setCreditCard(creditCard);

	   CreateRecurringPaymentsProfileRequestType createRPProfileRequest = new CreateRecurringPaymentsProfileRequestType();
	   createRPProfileRequest.setCreateRecurringPaymentsProfileRequestDetails(createRPProfileRequestDetails);

	   CreateRecurringPaymentsProfileReq createRPPProfileReq = new CreateRecurringPaymentsProfileReq();
	   createRPPProfileReq.setCreateRecurringPaymentsProfileRequest(createRPProfileRequest);

	   Map<String, String> sdkConfig = new HashMap<String, String>();
	   sdkConfig.put("mode", "sandbox");
	   sdkConfig.put("acct1.UserName", "parshant.verma-facilitator_api1.techbirds.in");
	   sdkConfig.put("acct1.Password", "YHF4ZF56XW4M4C8B");
	   sdkConfig.put("acct1.Signature","AO5U7yJDQvUSH27ek6l2bM7rT8v8A2h9xHwNHdlNBapWXMs1XEsG6seN");
	   PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(sdkConfig);
	   try {
		CreateRecurringPaymentsProfileResponseType createRPProfileResponse = service.createRecurringPaymentsProfile(createRPPProfileReq);
	
	
	   } catch (SSLConfigurationException e) {
	
		e.printStackTrace();
	} catch (InvalidCredentialException e) {
		
		e.printStackTrace();
	} catch (HttpErrorException e) {
		
		e.printStackTrace();
	} catch (InvalidResponseDataException e) {
		
		e.printStackTrace();
	} catch (ClientActionRequiredException e) {
		
		e.printStackTrace();
	} catch (MissingCredentialException e) {
		
		e.printStackTrace();
	} catch (OAuthException e) {
		
		e.printStackTrace();
	} catch (IOException e) {
		
		e.printStackTrace();
	} catch (InterruptedException e) {
		
		e.printStackTrace();
	} catch (ParserConfigurationException e) {
		
		e.printStackTrace();
	} catch (SAXException e) {
		
		e.printStackTrace();
	}
   }

}
