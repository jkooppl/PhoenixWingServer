package com.pizza73.util.validator;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class ValidatorUtils
{
    private static final String postalCodeRegex = "^[abceghjklmnprstvxyABCEGHJKLMNPRSTVXY][0-9]"
        + "[abceghjklmnprstvwxyzABCEGHJKLMNPRSTVWXYZ] {0,1}[0-9]" + "[abceghjklmnprstvwxyzABCEGHJKLMNPRSTVWXYZ][0-9]$";
    private static final Pattern postalCodePattern = Pattern.compile(postalCodeRegex);

    private static final String sp = "\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`\\{\\|\\}\\~";
    private static final String atext = "[a-zA-Z0-9" + sp + "]";
    private static final String atom = atext + "+";
    // one or more atext chars
    private static final String dotAtom = "\\." + atom;
    private static final String localPart = atom + "(" + dotAtom + ")*";
    // one atom followed by 0 or more dotAtoms.
    // RFC 1035 tokens for domain names:
    private static final String letter = "[a-zA-Z]";
    private static final String letDig = "[a-zA-Z0-9]";
    private static final String letDigHyp = "[a-zA-Z0-9-]";
    public static final String rfcLabel = letDig + letDigHyp + "{0,61}" + letDig;
    private static final String domain = rfcLabel + "(\\." + rfcLabel + ")*\\." + letter + "{2,6}";
    // Combined together, these form the allowed email regexp allowed by RFC
    // 2822:
    private static final String addrSpec = "^" + localPart + "@" + domain + "$";
    // now compile it:
    public static final Pattern VALID_PATTERN = Pattern.compile(addrSpec);

    public static boolean validPostalCode(String postalCode)
    {
        if (postalCodePattern.matcher(postalCode).matches())
        {
            return true;
        }
        return false;
    }

    public static boolean validPostalCodeRequired(String postalCode)
    {
        if (StringUtils.isNotBlank(postalCode))
        {
            return validPostalCode(postalCode);
        }

        return false;
    }

    public static boolean validPostalCodeNotRequired(String postalCode)
    {
        if (StringUtils.isNotBlank(postalCode))
        {
            return validPostalCode(postalCode);
        }

        return true;
    }

    public static boolean validEmail(String email)
    {
        if (!ValidatorUtils.VALID_PATTERN.matcher(email).matches())
        {
            return false;
        }
        if (email.length() > 64)
        {
            return false;
        }

        return true;
    }

    public static boolean validEmailRequired(String email)
    {
        if (StringUtils.isNotBlank(email))
            return validEmail(email);

        return false;
    }

}
