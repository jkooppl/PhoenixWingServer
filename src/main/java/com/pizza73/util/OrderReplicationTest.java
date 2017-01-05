package com.pizza73.util;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.pizza73.model.Order;
import com.pizza73.model.OrderItem;
import com.pizza73.model.Product;
import com.pizza73.model.StreetTypeCodeMap;
import com.pizza73.service.LookupManager;
import com.pizza73.service.ReplicationManager;

/**
 * StreetAddressMappingTest.java TODO comment me
 * 
 * @author chris 9-Mar-07
 * 
 * @Copyright Flying Pizza 73
 */
public class OrderReplicationTest
{
    protected FileSystemXmlApplicationContext ctx;
    private SessionFactory sf = null;
    private LookupManager lookupMgr;
    private ReplicationManager replicationMgr;
    private Map<String, String> codeMap;
    
    public void setLookupManager(LookupManager mgr)
    {
        this.lookupMgr = mgr;
    }
    
    public void setReplicationManager(ReplicationManager mgr)
    {
        this.replicationMgr = mgr;
    }
    
    @SuppressWarnings("unchecked")
   public void setUp()
    {
        String[] paths = {
            "C:\\Documents and Settings\\chris\\My Documents\\dev\\eddOnline\\test\\service\\com\\pizza73\\service\\applicationContext-resources.xml",
            "C:\\Documents and Settings\\chris\\My Documents\\dev\\eddOnline\\test\\service\\com\\pizza73\\service\\applicationContext-hibernate.xml",
            "C:\\Documents and Settings\\chris\\My Documents\\dev\\eddOnline\\test\\service\\com\\pizza73\\service\\applicationContext-service.xml" };
        ctx = new FileSystemXmlApplicationContext(paths);
        sf = (SessionFactory) ctx.getBean("sessionFactory");
        Session s = sf.openSession();
        TransactionSynchronizationManager
                .bindResource(sf, new SessionHolder(s));
        lookupMgr = (LookupManager) ctx.getBean("lookupManager");
        replicationMgr = (ReplicationManager) ctx.getBean("replicationManager");

        List<StreetTypeCodeMap> codes = 
            (List<StreetTypeCodeMap>) lookupMgr.getAll(StreetTypeCodeMap.class);
        codeMap = new TreeMap<String, String>();

        Iterator<StreetTypeCodeMap> codeIter = codes.iterator();
        while (codeIter.hasNext())
        {
            StreetTypeCodeMap code = codeIter.next();
            codeMap.put(code.getAlternateTypeCode().trim(), code.getTypeCode()
                    .trim());
        }
    }

    public static void main(String[] args) throws Exception
    {
        OrderReplicationTest sam = new OrderReplicationTest();
        sam.setUp();
        
        List<Integer> notReplicated = new ArrayList<Integer>();
        for (int count = 27; count < 28; count++)
        {
            List<Order> orders = null;
            
            Iterator<Order> iter = orders.iterator();
            while (iter.hasNext())
            {
                Order order = iter.next();
                try
                {
                    System.out.println("replicating: " + order.getId());
                    
                    List<OrderItem> orderItems = 
                        sam.lookupMgr.parentOrderItemsForOrder(order.getId());
                    orderItems =  sam.refreshItem(order.getId(), orderItems, sam.lookupMgr);
                    
                    sam.replicationMgr.replicateOrder(order, orderItems, null);
                }
                catch(Exception e)
                {
                    System.out.println("Order: " + order.getId() + " not replicated");
                    System.out.println(e.getMessage());
                    notReplicated.add(order.getId());
                }
            }
        }
        
        File f = new File("replicationErrors");
        if (!f.exists())
        {
            f.mkdirs();
        }
        File reportFile = 
            new File(f, "notReplicated-" + System.currentTimeMillis());
        PrintWriter writer = null;
        try
        {
            writer = new PrintWriter(reportFile);
            Iterator<Integer> iter2 = notReplicated.iterator();
            while (iter2.hasNext())
            {
                Integer id = iter2.next();
                writer.println("online_id: " + id + "not replicated");
            }
        }
        finally
        {
            writer.close();
        }
    }
    /*
     * (non-Javadoc)
     * see
     * @see com.pizza73.service.dwr.CartManager#setSalt(com.pizza73.model.Cart,
     *      com.pizza73.model.Order)
     */
    public List<OrderItem> refreshItem(Integer orderId, List<OrderItem> items, LookupManager mgr)
    {
        List<OrderItem> orderItems = new ArrayList<OrderItem>();
        Iterator<OrderItem> iter = items.iterator();
        OrderItem item = null;
        while (iter.hasNext())
        {
            item = iter.next();
            populateChildren(item, orderId, mgr);
            
            Integer id = item.getProduct().getId();
            if (id.equals(Integer.valueOf(223)) 
                || id.equals(Integer.valueOf(224))
                || id.equals(Integer.valueOf(225))
                || id.equals(Integer.valueOf(226)))
            {
                item.setBundle(true);
                item.setCompleteBundle(true);
                if (id.equals(Integer.valueOf(223)))
                {
                    item.setDrawTitle(false);
                }
            }
            orderItems.add(item);
        }
        
        return orderItems;
    }

    /**
     * @param orderId
     * @param item
     * @return
     */
    private static void populateChildren(OrderItem item, Integer orderId, LookupManager mgr)
    {
        List<OrderItem> children = mgr.orderItemsForParent(orderId, item.getItemId());
        if(!children.isEmpty())
        {
            item.setChildren(children);
            Iterator<OrderItem> childIter = children.iterator();
            while(childIter.hasNext())
            {
                OrderItem child = childIter.next();
                Product p = child.getProduct();
                child.setParent(item);
                
                if(p.getCategory().isTopLevel())
                {
                    populateChildren(child, orderId, mgr);                
                }
            }
        }
    }
}
