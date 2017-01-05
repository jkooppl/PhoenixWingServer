package com.pizza73.service;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

/**
 * Class for sending e-mail messages based on Velocity templates or with
 * attachments.
 *
 * <p>
 * <a href="MailEngine.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Matt Raible
 */
public class MailEngine
{
    protected static final Logger log = Logger.getLogger(MailEngine.class);
    private MailSender mailSender;
    private VelocityEngine velocityEngine;

    public void setMailSender(MailSender mailSender)
    {
        this.mailSender = mailSender;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine)
    {
        this.velocityEngine = velocityEngine;
    }

    /**
     * Send a simple message based on a Velocity template.
     *
     * @param msg
     * @param templateName
     * @param model
     */
    public void sendMessage(SimpleMailMessage msg, String templateName, Map<String, Object> model)
    {
        String result = null;

        try
        {
            result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateName, model);
        }
        catch (VelocityException e)
        {
            log.error("Error sending mail message.", e);
            e.printStackTrace();
        }

        msg.setText(result);
        send(msg);
    }

    public void sendOrderMessage(SimpleMailMessage msg, String templateName, Map<String, Object> model)
    {
        String result = null;

        try
        {
            result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateName, model);
        }
        catch (VelocityException e)
        {
            log.error("Error sending mail message.", e);
            e.printStackTrace();
        }

        msg.setText(result);
        sendOrderConformation(msg);
    }

    /**
     * Send a simple message with pre-populated values.
     *
     * @param msg
     */
    public void send(SimpleMailMessage msg)
    {
        try
        {
            mailSender.send(msg);
        }
        catch (MailException ex)
        {
            // log it and go on
            log.error("Error sending mail message", ex);

        }
    }

    /**
     * Send a simple message with pre-populated values.
     *
     * @param msg
     */
    public void sendOrderConformation(SimpleMailMessage msg)
    {
        mailSender.send(msg);
    }

    /**
     * Convenience method for sending messages with attachments.
     *
     * @param emailAddresses
     * @param resource
     * @param bodyText
     * @param subject
     * @param attachmentName
     * @throws MessagingException
     * @author Ben Gill
     */
    public void sendMessage(String[] emailAddresses, ClassPathResource resource, String bodyText, String subject,
        String attachmentName) throws MessagingException
    {
        MimeMessage message = ((JavaMailSenderImpl) mailSender).createMimeMessage();

        // use the true flag to indicate you need a multipart message
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(emailAddresses);
        helper.setText(bodyText);
        helper.setSubject(subject);

        helper.addAttachment(attachmentName, resource);

        ((JavaMailSenderImpl) mailSender).send(message);
    }

    public void sendMessage(String[] emailAddresses, File attachmentFile, String bodyText, String subject, String from)
        throws MessagingException
    {
        MimeMessage message = ((JavaMailSenderImpl) mailSender).createMimeMessage();

        // use the true flag to indicate you need a multipart message
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(from);
        helper.setTo(emailAddresses);
        helper.setText(bodyText);
        helper.setSubject(subject);

        helper.addAttachment(attachmentFile.getName(), attachmentFile);

        ((JavaMailSenderImpl) mailSender).send(message);
    }

    public void sendMessage(final String template, final Map<String, Object> model, final String to, final String from,
        final String subject) throws MessagingException
    {
        MimeMessagePreparator preparator = new MimeMessagePreparator()
        {
            public void prepare(MimeMessage mimeMessage) throws Exception
            {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
                message.setTo(to);
                message.setFrom(from);
                message.setSubject(subject);
                String result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, template, model);
                message.setText(result, true);
            }
        };

        ((JavaMailSenderImpl) mailSender).send(preparator);
    }
}