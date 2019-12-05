package com.fixit.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.fixit.dao.UserDao;
import com.fixit.domain.vo.User;
import com.paypal.exception.ClientActionRequiredException;
import com.paypal.exception.HttpErrorException;
import com.paypal.exception.InvalidCredentialException;
import com.paypal.exception.InvalidResponseDataException;
import com.paypal.exception.MissingCredentialException;
import com.paypal.exception.SSLConfigurationException;
import com.paypal.sdk.exceptions.OAuthException;

import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentReq;
import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentRequestType;
import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentResponseType;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsReq;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsRequestType;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsResponseType;
import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutReq;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutRequestType;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutResponseType;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;
import urn.ebay.apis.eBLBaseComponents.DoExpressCheckoutPaymentRequestDetailsType;
import urn.ebay.apis.eBLBaseComponents.ErrorType;
import urn.ebay.apis.eBLBaseComponents.FundingSourceDetailsType;
import urn.ebay.apis.eBLBaseComponents.LandingPageType;
import urn.ebay.apis.eBLBaseComponents.PaymentActionCodeType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsItemType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsType;
import urn.ebay.apis.eBLBaseComponents.SetExpressCheckoutRequestDetailsType;
import urn.ebay.apis.eBLBaseComponents.SolutionTypeType;
import urn.ebay.apis.eBLBaseComponents.UserSelectedFundingSourceType;

@Service
public class PayPalSinglePaymentService {

	@Autowired
	private UserCreditService userCreditService;
	
	@Autowired
	private UsersAccountingService usersAccountingService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDao userDao;
	
	private String paypalAcessToken=null;
	private String anyMoney=null;
	private String credit="0";
	private static final String USERNAME = "admin_api1.erpfixers.com";
	private static final String PASSWORD = "Y96M3S54Y57Z4KEC";
	private static final String SIGNATURE = "AFcWxV21C7fd0v3bYYYRCpSSRl31Abs6C55Q9cO8fviIKixOmtIaIIP8";
	private static final String MODE="live";
	
//	private static final String USERNAME = "anil.kumar-facilitator_api1.techbirds.in";
//	private static final String PASSWORD = "TZL9EEHFMTJSHN8S";
//	private static final String SIGNATURE = "AFcWxV21C7fd0v3bYYYRCpSSRl31Az1YTzWb9.nJh9GmZIJkMHrJz-Nx";
//	private static final String MODE="sandbox";
	
	 
	
	public String getAccessTokenForPayment(String money,String contextPath , User user, String userUrl){
		 
		  PaymentDetailsType paymentDetails = new PaymentDetailsType();
		    paymentDetails.setPaymentAction(PaymentActionCodeType.fromValue("Sale"));
		    PaymentDetailsItemType item = new PaymentDetailsItemType();
		    BasicAmountType amt = new BasicAmountType();
		    amt.setCurrencyID(CurrencyCodeType.fromValue("USD"));
		    amt.setValue(money);
		    int itemQuantity = 1;
		    item.setQuantity(itemQuantity);
		    String orderDescription ="";
		    if(money!=null && money!=""){
		           if(money.equals("575")){
		        	   orderDescription = "You are creating payment of "+amt.getValue()+" "+amt.getCurrencyID()+" to admin@erpfixers.com for 5 credits";
		               if("1".equals(user.getSource()) && "N".equals(user.getTrackCredit())){
		                credit="6";
		               }else{
		                credit="5";
		               }
		              
		           }else if(money.equals("1125")){
		            orderDescription = "You are creating payment of "+amt.getValue()+" "+amt.getCurrencyID()+" to admin@erpfixers.com for 10 credits";
		              if("1".equals(user.getSource()) && "N".equals(user.getTrackCredit())){
		                  credit="11";
		                 }else{
		                  credit="10";
		                 }
		           }else if(money.equals("2200")){
		            orderDescription = "You are creating payment of "+amt.getValue()+" "+amt.getCurrencyID()+" to admin@erpfixers.com for 20 credits";
		              if("1".equals(user.getSource()) && "N".equals(user.getTrackCredit())){
		                  credit="21";
		                 }else{
		                  credit="20";
		                 }
		           }else if(money.equals("4300")){
		            orderDescription = "You are creating payment of "+amt.getValue()+" "+amt.getCurrencyID()+" to admin@erpfixers.com for 20 credits";
		              if("1".equals(user.getSource()) && "N".equals(user.getTrackCredit())){
		                  credit="41";
		                 }else{
		                  credit="40";
		                 }
		         }
		           else{
		            double myMoney=Double.parseDouble(money);
		            myMoney=myMoney/5;
		            credit=""+myMoney;
		           }
		    }
		    
		    
		    item.setName("Buying credits:" + credit);
		    item.setAmount(amt);
		    item.setDescription(orderDescription);
		    List<PaymentDetailsItemType> lineItems = new ArrayList<PaymentDetailsItemType>();
		    lineItems.add(item);
		    paymentDetails.setPaymentDetailsItem(lineItems);
		    BasicAmountType orderTotal = new BasicAmountType();
		    orderTotal.setCurrencyID(CurrencyCodeType.fromValue("USD"));
		    orderTotal.setValue(String.valueOf(Integer.valueOf(money)* itemQuantity)); 
		    paymentDetails.setOrderTotal(orderTotal);
		    List<PaymentDetailsType> paymentDetailsList = new ArrayList<PaymentDetailsType>();
		    paymentDetailsList.add(paymentDetails);
		    SetExpressCheckoutRequestDetailsType setExpressCheckoutRequestDetails = new SetExpressCheckoutRequestDetailsType();
		    
		    if(null != userUrl && !userUrl.trim().isEmpty()){


		    if(userUrl.equals("dashBoard") || userUrl.equals("newRequest")){
			    setExpressCheckoutRequestDetails.setCancelURL(contextPath+"member/"+userUrl+ "?msgKey=PNS");
				  userUrl = userUrl.replace("?", "&");
				  userUrl = "\""+ userUrl + "\"";
			    setExpressCheckoutRequestDetails.setReturnURL(contextPath+"member/finalPayment?userUrl=" + userUrl);

		    }
		    else if(userUrl.contains("reassignRequest")){
			   userUrl = userUrl.replaceAll(",", "&");
			  userUrl = userUrl.replaceFirst("&", "?");
			  userUrl = userUrl.replaceFirst("\"", "");
			    setExpressCheckoutRequestDetails.setCancelURL(contextPath+"member/"+userUrl+ "&msgKey=PNS");
				  userUrl = userUrl.replace("?", "&");
				  userUrl = "\""+ userUrl + "\"";
				  userUrl = userUrl.replaceAll("&", ",");
     		    setExpressCheckoutRequestDetails.setReturnURL(contextPath+"member/finalPayment?userUrl=" + userUrl);

			  
		   }
		    else if(userUrl.contains("\"request,")){
			   userUrl = userUrl.replaceAll(",", "&");
				  userUrl = userUrl.replaceFirst("&", "?");
				  userUrl = userUrl.replaceAll("\"", "");
				  setExpressCheckoutRequestDetails.setCancelURL(contextPath+"member/"+userUrl+ "&msgKey=PNS");
				  userUrl = userUrl.replace("?", ",");
				  userUrl = userUrl.replaceAll("&", ",");
				  userUrl = "\""+ userUrl + "\"";
	     		    setExpressCheckoutRequestDetails.setReturnURL(contextPath+"member/finalPayment?userUrl=" + userUrl);

		   }
		    else{
		    setExpressCheckoutRequestDetails.setCancelURL(contextPath+"member/"+userUrl+ "&msgKey=PNS");
 		    setExpressCheckoutRequestDetails.setReturnURL(contextPath+"member/finalPayment?userUrl=" + userUrl);

		    }
		    }
		    else{
		    	
		    	setExpressCheckoutRequestDetails.setReturnURL(contextPath+"member/finalPayment");
			    
			    setExpressCheckoutRequestDetails.setCancelURL(contextPath+"member/dashBoard?msgKey=PNS");
		    }
		    setExpressCheckoutRequestDetails.setLandingPage(LandingPageType.BILLING);
		    setExpressCheckoutRequestDetails.setPaymentDetails(paymentDetailsList);
		    setExpressCheckoutRequestDetails.setSolutionType(SolutionTypeType.SOLE);
		    FundingSourceDetailsType fundingSourceType =  new FundingSourceDetailsType();
		    fundingSourceType.setUserSelectedFundingSource(UserSelectedFundingSourceType.CREDITCARD);
		    setExpressCheckoutRequestDetails.setFundingSourceDetails(fundingSourceType);
		    setExpressCheckoutRequestDetails.setLocaleCode("us");
		   
		   
		    setExpressCheckoutRequestDetails.setOrderDescription(orderDescription);
		    SetExpressCheckoutRequestType setExpressCheckoutRequest = new SetExpressCheckoutRequestType(setExpressCheckoutRequestDetails);
		    setExpressCheckoutRequest.setVersion("104.0");
		    SetExpressCheckoutReq setExpressCheckoutReq = new SetExpressCheckoutReq();
		    setExpressCheckoutReq.setSetExpressCheckoutRequest(setExpressCheckoutRequest);

		    Map<String, String> sdkConfig = new HashMap<String, String>();
		    sdkConfig.put("mode", MODE);
		    sdkConfig.put("acct1.UserName", USERNAME);
		    sdkConfig.put("acct1.Password", PASSWORD);
		    sdkConfig.put("acct1.Signature",SIGNATURE);
		    PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(sdkConfig);
		    SetExpressCheckoutResponseType setExpressCheckoutResponse;
		    try {
		     setExpressCheckoutResponse = service.setExpressCheckout(setExpressCheckoutReq);
		     if (setExpressCheckoutResponse.getAck().getValue()
		       .equalsIgnoreCase("success")) {
		      return setExpressCheckoutResponse.getToken();
		     }
		     else {
		      List<ErrorType> errorList = setExpressCheckoutResponse.getErrors();
		     }
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
		    
		    return "/member/dashBoard?msgKey=PNS";
		 }
	

	public void finalPaymentStepExecuteAdminToFixer(String paymenttId,String token,String payerId){
	
		
	  
	
	}
	
	public String DoExpressCheckoutPayment(String token , String payerId , String amount){
		PaymentDetailsType paymentDetail = new PaymentDetailsType();
		paymentDetail.setNotifyURL("http://replaceIpnUrl.com");
		BasicAmountType orderTotal = new BasicAmountType();
		orderTotal.setValue(amount);
		orderTotal.setCurrencyID(CurrencyCodeType.fromValue("USD"));
		paymentDetail.setOrderTotal(orderTotal);
		paymentDetail.setPaymentAction(PaymentActionCodeType.fromValue("Sale"));
		List<PaymentDetailsType> paymentDetails = new ArrayList<PaymentDetailsType>();
		paymentDetails.add(paymentDetail);
						
		DoExpressCheckoutPaymentRequestDetailsType doExpressCheckoutPaymentRequestDetails = new DoExpressCheckoutPaymentRequestDetailsType();
		doExpressCheckoutPaymentRequestDetails.setToken(token);
		doExpressCheckoutPaymentRequestDetails.setPayerID(payerId);
		doExpressCheckoutPaymentRequestDetails.setPaymentDetails(paymentDetails);
		DoExpressCheckoutPaymentRequestType doExpressCheckoutPaymentRequest = new DoExpressCheckoutPaymentRequestType(doExpressCheckoutPaymentRequestDetails);
		doExpressCheckoutPaymentRequest.setVersion("104.0");
		DoExpressCheckoutPaymentReq doExpressCheckoutPaymentReq = new DoExpressCheckoutPaymentReq();
		doExpressCheckoutPaymentReq.setDoExpressCheckoutPaymentRequest(doExpressCheckoutPaymentRequest);
		Map<String, String> sdkConfig = new HashMap<String, String>();
		 sdkConfig.put("mode", MODE);
		 sdkConfig.put("acct1.UserName", USERNAME);
		 sdkConfig.put("acct1.Password", PASSWORD);
		 sdkConfig.put("acct1.Signature",SIGNATURE);
		PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(sdkConfig);
		try {
			DoExpressCheckoutPaymentResponseType doExpressCheckoutPaymentResponse = service.doExpressCheckoutPayment(doExpressCheckoutPaymentReq);
		    return doExpressCheckoutPaymentResponse.getAck().getValue();
		} catch (SSLConfigurationException | InvalidCredentialException | HttpErrorException
				| InvalidResponseDataException | ClientActionRequiredException | MissingCredentialException
				| OAuthException | IOException | InterruptedException | ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	public String getExpressCheckoutDetails(String token){
		GetExpressCheckoutDetailsRequestType getExpressCheckoutDetailsRequest = new GetExpressCheckoutDetailsRequestType(token);
		getExpressCheckoutDetailsRequest.setVersion("104.0");
		GetExpressCheckoutDetailsReq getExpressCheckoutDetailsReq = new GetExpressCheckoutDetailsReq();
		getExpressCheckoutDetailsReq.setGetExpressCheckoutDetailsRequest(getExpressCheckoutDetailsRequest);
		Map<String, String> sdkConfig = new HashMap<String, String>();
		 sdkConfig.put("mode", MODE);
		 sdkConfig.put("acct1.UserName", USERNAME);
		 sdkConfig.put("acct1.Password", PASSWORD);
		 sdkConfig.put("acct1.Signature",SIGNATURE);
		PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(sdkConfig);
		try {
			GetExpressCheckoutDetailsResponseType getExpressCheckoutDetailsResponse = service.getExpressCheckoutDetails(getExpressCheckoutDetailsReq);
		    if(getExpressCheckoutDetailsResponse.getAck().getValue().equals("Success"))
			return getExpressCheckoutDetailsResponse.getGetExpressCheckoutDetailsResponseDetails().getPaymentDetails().get(0).getOrderTotal().getValue();
		} catch (SSLConfigurationException | InvalidCredentialException | HttpErrorException
				| InvalidResponseDataException | ClientActionRequiredException | MissingCredentialException
				| OAuthException | IOException | InterruptedException | ParserConfigurationException | SAXException e) {
			
			e.printStackTrace();
			return null;
		} 
		return null;
	}

	
}
