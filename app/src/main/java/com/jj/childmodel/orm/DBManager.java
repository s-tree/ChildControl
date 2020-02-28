package com.jj.childmodel.orm;

import com.jj.childmodel.ChildApplication;
import com.jj.childmodel.bean.WhiteTimeBean;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBaseConfig;

import java.util.List;

public class DBManager {

    private static DataBaseConfig config = new DataBaseConfig(ChildApplication.instance,"app");
    private static LiteOrm liteOrm = LiteOrm.newSingleInstance(config);

    public static List<WhiteTimeBean> getWhitelistBeans(){
        return liteOrm.query(WhiteTimeBean.class);
    }

    public static void addWhiteBean(WhiteTimeBean whiteTimeBean){
        whiteTimeBean.id = liteOrm.save(whiteTimeBean);
    }

    public static void removeWhiteBean(WhiteTimeBean whiteTimeBean){
        liteOrm.delete(whiteTimeBean);
    }
}
