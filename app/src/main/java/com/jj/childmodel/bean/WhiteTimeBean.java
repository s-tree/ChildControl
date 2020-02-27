package com.jj.childmodel.bean;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

public class WhiteTimeBean {

    @PrimaryKey(AssignType.AUTO_INCREMENT)
    public long id;

    public String startTime;

    public String endTime;
}
