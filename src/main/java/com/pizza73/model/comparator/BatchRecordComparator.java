package com.pizza73.model.comparator;

import java.io.Serializable;
import java.util.Comparator;

import com.pizza73.model.BatchRecord;

public class BatchRecordComparator implements Serializable, Comparator<BatchRecord>
{
    private static final long serialVersionUID = -8789653053299605963L;

    public int compare(BatchRecord br1, BatchRecord br2)
    {
        if (br2.isWireless() == true && br1.isWireless() == false)
            return -1;
        else
            return br1.getId().compareTo(br2.getId());
    }

}
