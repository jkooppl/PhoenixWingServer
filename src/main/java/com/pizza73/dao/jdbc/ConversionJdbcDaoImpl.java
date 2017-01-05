package com.pizza73.dao.jdbc;

import java.io.Serializable;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pizza73.dao.ConversionDao;
import com.pizza73.model.conversion.Iqq;

/**
 * ConversionJdbcDaoImpl.java TODO comment me
 * 
 * @author chris 27-Mar-07
 * 
 * @Copyright Flying Pizza 73
 */
@Repository("conversionDao")
public class ConversionJdbcDaoImpl implements ConversionDao, Serializable
{
    private static final long serialVersionUID = -669817704571112992L;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource)
    {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // public void saveDetail(SalesDetail detail)
    // {
    // String info = addQuotes(detail.getInfo());
    // String addOns = addQuotes(detail.getAddOns());
    //
    // String insert =
    // "INSERT INTO sales_detail " +
    // "(order_id, product_id, product_modifier_1, product_modifier_2, " +
    // "product_modifier_3, product_modifier_4, product_addons, quantity, " +
    // "product_info, price) " +
    // "VALUES (" + detail.getOrderId() + "," + detail.getProductId() + ","
    // + detail.getModifierOne() + "," + detail.getModifierTwo() +
    // "," + detail.getModifierThree() + "," + detail.getModifierFour()
    // + "," + addOns + "," + + detail.getQuantity() + "," +
    // info + "," + detail.getPrice() + ")";
    // this.jdbcTemplate.update(insert);
    //
    // }

    private String addQuotes(String field)
    {
        field = field.replace("'", "\\'");
        return "'" + field + "'";
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.pizza73.dao.ConversionDao#saveIqq(com.pizza73.model.conversion.Iqq)
     */
    public void saveIqq(Iqq iqq)
    {
        int sequence = this.shopOrderSequence(iqq.getShopId());
        String insert = "INSERT INTO iqq " + "(order_id, rec_type_id, sequence_id, shop_id, "
            + "customer_id, iq_state_id, iq_status)" + "VALUES (" + iqq.getOrderId() + "," + iqq.getRecordTypeId() + ","
            + sequence + "," + iqq.getShopId() + "," + addQuotes(iqq.getCustomerId()) + "," + addQuotes(iqq.getIqStateId())
            + "," + addQuotes(iqq.getIqStatus()) + ")";
        this.jdbcTemplate.update(insert);
    }

    public int shopOrderSequence(Integer shopId)
    {
        String query = "select order_cnt_sequence(" + shopId + ")";
        return this.jdbcTemplate.queryForInt(query);
    }
}