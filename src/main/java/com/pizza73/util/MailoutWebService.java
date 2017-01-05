package com.pizza73.util;


public class MailoutWebService {

//	protected ClassPathXmlApplicationContext ctx;
//	private WebServiceTemplate webServiceTemplate = null;
//	public MailoutWebService(){}
//
//	private static final String MESSAGE =
//        "<Login xmlns=\"http://www.mymailout.net/WebService/SubscriberManager\"><username>webadmin@pizza73.com</username><password>pizza73</password></Login>";
//	private static final String SUBSCRIBER_MESSAGE = 
//		"<SignupNewSubscriber xmlns=\"http://www.mymailout.net/WebService/SubscriberManager\">" +
//		    "<mailinglistid>3883</mailinglistid>" +
//		    "<subscriber>" +
//		      "<Email>chris.huisman@gmail.com</Email>" +
//		      "<GivenName>Chris</GivenName>" +
//		      "<FamilyName>HUSS</FamilyName>" +
//		      "<PhoneNumber>7802352706</PhoneNumber>" +
//		      "<PostalCode>T5B4C8</PostalCode>" +
//		      "<City>Edmonton</City>" +
//		      "<State>AB</State>" +
//		      "<Country>CAN</Country>" +
//		      "<Active>true</Active>" +
//		    "</subscriber>" +
//		    "<checkrequired>false</checkrequired>" +
//		  "</SignupNewSubscriber>";
//	
//	public void setUp() {
//		String[] paths = { "util-applicationContext-resources.xml",
//				"applicationContext.xml" };
//		ctx = new ClassPathXmlApplicationContext(paths);
//		webServiceTemplate = (WebServiceTemplate) ctx.getBean("webServiceTemplate");
//	}
	
	public static void main(String[] args) {
//		MailoutWebService mws = new MailoutWebService();
//		mws.setUp();
//		try {
//			
//			StreamSource source = new StreamSource(new StringReader(MESSAGE));
//	        StreamResult result = new StreamResult(System.out);
//	        mws.webServiceTemplate.sendSourceAndReceiveToResult(source, result);
//
//	        System.out.println();
//	        StreamSource subscribe = new StreamSource(new StringReader(SUBSCRIBER_MESSAGE));
//	        mws.webServiceTemplate.sendSourceAndReceiveToResult(subscribe, result);
//	        
//	        
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
	}
}
