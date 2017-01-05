package com.pizza73.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "iq2_sales_order_comment", schema = "public", uniqueConstraints = {})
@SequenceGenerator(name = "order_comment_sequence", sequenceName = "iq2_sales_order_comment_id_seq")
public class OrderComment implements Serializable
{
    private static final long serialVersionUID = -1504737262282403995L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_comment_sequence")
    @Column(name = "id")
    private Integer id = null;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "comment_type")
    private Integer commentType;

    @Column(name = "content", columnDefinition = "char(32)")
    private String content = "";

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getOrderId()
    {
        return orderId;
    }

    public void setOrderId(Integer orderId)
    {
        this.orderId = orderId;
    }

    public Integer getCommentType()
    {
        return commentType;
    }

    public void setCommentType(Integer commentType)
    {
        this.commentType = commentType;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

}
