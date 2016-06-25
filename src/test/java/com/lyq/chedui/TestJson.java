package com.lyq.chedui;

import com.lyq.chedui.base.CdBase;
import com.lyq.chedui.base.Opinion;
import net.sf.json.JSONObject;
import org.junit.Test;

/**
 * Created by lyq on 2016/5/13.
 */
public class TestJson {

    public void opinion() {
        Opinion opinion = new Opinion();
        setBase(opinion, "saveFeedback");
        opinion.setContents("这是反馈内容测试");
        opinion.setMobilePhoneInfo("13510551303魅蓝Note2");
        opinion.setVersion("0.1.1");
        opinion.setUniqueKey("jkx20160513114205");
        opinion.setImageFormat("webp");
        String[] imageStreams = new String[2];
        imageStreams[0] = "5rWL6K+VMQ==";
        imageStreams[1] = "5rWL6K+VMg==";
        opinion.setImageStreams(imageStreams);
        System.out.println(JSONObject.fromObject(opinion).toString());
    }

    /**
     * 设置基础数据
     * @param cdBase 对象
     * @param methodName 方法名
     */
    private void setBase(CdBase cdBase, String methodName) {
        cdBase.setMethodname(methodName);
        cdBase.setAccreditId("nbdemo");
        cdBase.setUserId("jkx");
        cdBase.setPassword("jkx168");
    }

}
