package com.pizza73.service.impl;

import javax.xml.namespace.QName;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapHeaderElement;
import org.springframework.ws.soap.SoapMessage;

import com.pizza73.model.Address;
import com.pizza73.model.Municipality;
import com.pizza73.model.OnlineCustomer;
import com.pizza73.service.SubscriberService;
import com.pizza73.ws.industrymailout.subscriber.ConfirmSubscriber;
import com.pizza73.ws.industrymailout.subscriber.ConfirmSubscriberResponse;
import com.pizza73.ws.industrymailout.subscriber.Login;
import com.pizza73.ws.industrymailout.subscriber.LoginResponse;
import com.pizza73.ws.industrymailout.subscriber.SignupNewSubscriber;
import com.pizza73.ws.industrymailout.subscriber.SignupNewSubscriberResponse;
import com.pizza73.ws.industrymailout.subscriber.Subscriber;

@Service("SubscriberService")
public class SubscriberServiceImpl implements SubscriberService
{
    private static final int LIST_ID = 3883;
    private static final String USERNAME = "webadmin@pizza73.com";
    private static final String PASSWORD = "italianstallion123";
    private static final String SUBSCRIBER = "http://www.mymailout.net/WebService/SubscriberManager";

    Logger log = Logger.getLogger(SubscriberServiceImpl.class);
    @Autowired
    private WebServiceTemplate subscriberWSTemplate;

    public static void main(String[] args)
    {
        final String[] paths = { "META-INF/applicationContext.xml", "META-INF/test-applicationContext-blank.xml" };
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(paths);
        // ApplicationContext context = new
        // AnnotationConfigApplicationContext(ApplicationConfig.class);
        SubscriberServiceImpl subscriber = new SubscriberServiceImpl();
        subscriber.subscriberWSTemplate = (WebServiceTemplate) ctx.getBean("subscriberWSTemplate");
        OnlineCustomer oc = new OnlineCustomer();
        oc.setEmail("chris@breakpointcreative.com");
        oc.setPhone("7802354567");
        oc.setName("Karen-Ann");
        subscriber.signupNewSubscriber(oc);
    }

    public LoginResponse login()
    {
        Login login = new Login();
        login.setUsername(USERNAME);
        login.setPassword(PASSWORD);
        LoginResponse response = (LoginResponse) this.subscriberWSTemplate.marshalSendAndReceive(login,
            new WebServiceMessageCallback()
            {
                public void doWithMessage(WebServiceMessage message)
                {
                }
            });
        log.debug("THE RESPONSE From Web Service IS " + response.getLoginResult().getSessionID());
        return response;
    }

    public boolean signupNewSubscriber(final OnlineCustomer customer)
    {
        boolean signupSuccessfull = false;
        if (customer.isSubscribed())
        {
            final LoginResponse login = login();
            String email = customer.getEmail();
            Address address = customer.getAddress();

            Subscriber subscriber = new Subscriber();
            subscriber.setEmail(email);
            subscriber.setCountry("CA");
            if (null != address)
            {
                subscriber.setState(address.getProvince());
                Municipality city = address.getCity();
                if (null != city)
                {
                    subscriber.setCity(city.getCanadaPostName());
                }
            }
            // subscriber.setState("AB");
            // subscriber.setCity("Edmonton");

            subscriber.setPhoneNumber(customer.getPhone());
            subscriber.setGivenName(customer.getName());

            SignupNewSubscriber signupNewSubscriber = new SignupNewSubscriber();
            signupNewSubscriber.setMailinglistid(LIST_ID);
            signupNewSubscriber.setCheckrequired(false);
            signupNewSubscriber.setSubscriber(subscriber);

            SignupNewSubscriberResponse response = (SignupNewSubscriberResponse) this.subscriberWSTemplate
                .marshalSendAndReceive(signupNewSubscriber, new WebServiceMessageCallback()
                {
                    public void doWithMessage(WebServiceMessage message)
                    {
                        try
                        {
                            SoapMessage soapMessage = (SoapMessage) message;
                            SoapHeader header = soapMessage.getSoapHeader();

                            QName sessionHeaderName = new QName(SUBSCRIBER, "SessionHeader");
                            SoapHeaderElement sessionHeader = header.addHeaderElement(sessionHeaderName);

                            QName sessionIDName = new QName(SUBSCRIBER, "SessionID");
                            SoapHeaderElement sessionID = header.addHeaderElement(sessionIDName);
                            sessionID.setText(login.getLoginResult().getSessionID());

                            TransformerFactory transformerFactory = TransformerFactory.newInstance();
                            Transformer transformer = transformerFactory.newTransformer();

                            transformer.transform(sessionID.getSource(), sessionHeader.getResult());
                            header.removeHeaderElement(sessionIDName);

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            log.error(e.getMessage());
                        }
                    }
                });
            signupSuccessfull = response.getSignupNewSubscriberResult().isSuccess();
            if (signupSuccessfull)
            {
                ConfirmSubscriber confirmSubscriber = new ConfirmSubscriber();
                confirmSubscriber.setMailinglistid(LIST_ID);
                confirmSubscriber.setEmail(email);
                ConfirmSubscriberResponse confirmResponse = (ConfirmSubscriberResponse) this.subscriberWSTemplate
                    .marshalSendAndReceive(confirmSubscriber, new WebServiceMessageCallback()
                    {
                        public void doWithMessage(WebServiceMessage message)
                        {
                            try
                            {
                                SoapMessage soapMessage = (SoapMessage) message;
                                SoapHeader header = soapMessage.getSoapHeader();

                                QName sessionHeaderName = new QName(SUBSCRIBER, "SessionHeader");
                                SoapHeaderElement sessionHeader = header.addHeaderElement(sessionHeaderName);

                                QName sessionIDName = new QName(SUBSCRIBER, "SessionID");
                                SoapHeaderElement sessionID = header.addHeaderElement(sessionIDName);
                                sessionID.setText(login.getLoginResult().getSessionID());

                                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                                Transformer transformer = transformerFactory.newTransformer();

                                transformer.transform(sessionID.getSource(), sessionHeader.getResult());
                                header.removeHeaderElement(sessionIDName);

                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                                log.error(e.getMessage());
                            }
                        }
                    });
                signupSuccessfull = confirmResponse.getConfirmSubscriberResult().isSuccess();
                System.out.println("Subscriber: " + email + " confirmed: " + signupSuccessfull);
                log.warn("Subscriber: " + email + " confirmed: " + signupSuccessfull);
            }
        }
        return signupSuccessfull;
    }
}