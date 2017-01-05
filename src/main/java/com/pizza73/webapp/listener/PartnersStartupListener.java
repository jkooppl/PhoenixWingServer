package com.pizza73.webapp.listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.pizza73.Constants;
import com.pizza73.model.Location;
import com.pizza73.model.Municipality;
import com.pizza73.model.Role;
import com.pizza73.model.Shop;
import com.pizza73.model.StreetTypeCodeMap;
import com.pizza73.model.comparator.ShopIdComparator;
import com.pizza73.service.LookupManager;

/**
 * <p>
 * StartupListener class used to initialize and database settings and populate
 * any application-wide drop-downs.
 * 
 * <p>
 * Keep in mind that this listener is executed outside of
 * OpenSessionInViewFilter, so if you're using Hibernate you'll have to
 * explicitly initialize all loaded data at the Dao or service level to avoid
 * LazyInitializationException. Hibernate.initialize() works well for doing
 * this.
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 * 
 * @web.listener
 */
public class PartnersStartupListener extends ContextLoaderListener implements
        ServletContextListener {
    private static final String ROLE_SHOP_EMP = "ROLE_SHOP_EMP";
    private static final String ROLE_SHOP_MGR = "ROLE_SHOP_MGR";
    private static final String ROLE_SHOP_POS = "ROLE_SHOP_POS";
    private static final String ROLE_SHOP_OWNER = "ROLE_SHOP_OWNER";
    private static final String ROLE_TIMESHEET = "ROLE_TIMESHEET";
    private static final String ROLE_SHOP_CRUD = "ROLE_SHOP_CRUD";
    private static final Logger log = Logger.getLogger(PartnersStartupListener.class);

    @Override
    @SuppressWarnings("unchecked")
    public void contextInitialized(ServletContextEvent event)
    {
        // call Spring's context ContextLoaderListener to initialize
        // all the context files specified in web.xml
        super.contextInitialized(event);
        log.info("initializing context...");

        //
        ServletContext context = event.getServletContext();
        //
        // // Orion starts Servlets before Listeners, so check if the config
        // // object already exists
        Map config = (HashMap) context.getAttribute(Constants.CONFIG);
        if (config == null)
        {
            config = new HashMap();
        }

        ApplicationContext ctx = WebApplicationContextUtils
                .getRequiredWebApplicationContext(context);

        try
        {
            ProviderManager provider = (ProviderManager) ctx
                    .getBean("authenticationManager");
            for (Iterator it = provider.getProviders().iterator(); it.hasNext();)
            {
                AuthenticationProvider p = (AuthenticationProvider) it.next();
                if (p instanceof RememberMeAuthenticationProvider)
                {
                    config.put("rememberMeEnabled", Boolean.TRUE);
                }
            }

        }
        catch (NoSuchBeanDefinitionException n)
        {
            // ignore, should only happen when testing
        }

        setupContext(context);
    }

    @SuppressWarnings("unchecked")
    public static void setupContext(ServletContext context)
    {
        ApplicationContext ctx = WebApplicationContextUtils
                .getRequiredWebApplicationContext(context);

        LookupManager mgr = (LookupManager) ctx.getBean("lookupManager");

        List<Municipality> cities = mgr.municipalities();
        context.setAttribute("MUNICIPALITIES", cities);

        List<Location> location = (List<Location>) mgr.getAll(Location.class);
        context.setAttribute("LOCATIONS", location);

        List<String> provinces = mgr.provinces();
        context.setAttribute("PROVINCES", provinces);

        // ALTERNATE STREET TYPE CODES
        List<StreetTypeCodeMap> codes = (List<StreetTypeCodeMap>) mgr
                .getAll(StreetTypeCodeMap.class);
        Map<String, String> codeMap = new TreeMap<String, String>();

        Iterator<StreetTypeCodeMap> codeIter = codes.iterator();
        while (codeIter.hasNext())
        {
            StreetTypeCodeMap code = codeIter.next();
            codeMap.put(code.getAlternateTypeCode().trim(), code.getTypeCode().trim());
        }
        context.setAttribute("ALT_STREET_CODES", codeMap);

        // STREET TYPE CODES
        List<StreetTypeCodeMap> streetTypeCodes = (List<StreetTypeCodeMap>) mgr
                .getAll(StreetTypeCodeMap.class);
        Iterator<StreetTypeCodeMap> typeCodeIter = streetTypeCodes.iterator();
        List<StreetTypeCodeMap> streetCodes = new ArrayList<StreetTypeCodeMap>();
        StreetTypeCodeMap prev = null;
        while (typeCodeIter.hasNext())
        {
            StreetTypeCodeMap typeCode = typeCodeIter.next();
            if (prev != null && prev.getTypeCode().equals(typeCode.getTypeCode()))
            {
                streetCodes.add(typeCode);
            }
            prev = typeCode;
        }
        streetTypeCodes.removeAll(streetCodes);
        Collections.sort(streetTypeCodes);
        context.setAttribute("STREET_TYPE_CODES", streetTypeCodes);

        List<Shop> shops = mgr.activeShops();
        context.setAttribute("SHOPS", shops);

        List<Shop> sortedShops = mgr.activeShopsWithCSC();
        Collections.sort(sortedShops, new ShopIdComparator());
        context.setAttribute("SHOPS_BY_ID", sortedShops);

        Role role = mgr.roleByName(ROLE_SHOP_EMP);
        context.setAttribute(ROLE_SHOP_EMP, role);
        role = mgr.roleByName(ROLE_SHOP_POS);
        context.setAttribute(ROLE_SHOP_POS, role);
        role = mgr.roleByName(ROLE_TIMESHEET);
        context.setAttribute(ROLE_TIMESHEET, role);
        role = mgr.roleByName(ROLE_SHOP_CRUD);
        context.setAttribute(ROLE_SHOP_CRUD, role);
        role = mgr.roleByName(ROLE_SHOP_MGR);
        context.setAttribute(ROLE_SHOP_MGR, role);
        role = mgr.roleByName(ROLE_SHOP_OWNER);
        context.setAttribute(ROLE_SHOP_OWNER, role);

        log.debug("Drop-down initialization complete for ADMIN [OK]");
    }
}
