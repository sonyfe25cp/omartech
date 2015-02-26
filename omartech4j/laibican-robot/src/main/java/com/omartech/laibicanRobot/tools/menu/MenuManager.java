package com.omartech.laibicanRobot.tools.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by OmarTech on 15-2-13.
 */
public class MenuManager {
    static Logger log = LoggerFactory.getLogger(MenuManager.class);


    public static void main(String[] args) {
        // 第三方用户唯一凭证
//        String appId = "000000000000000000";
        String appId = "wxa2a41fadbfbd2a6b";//暴雪英雄榜
        // 第三方用户唯一凭证密钥
//        String appSecret = "00000000000000000000000000000000";
        String appSecret = "ce4e132c3182d32bcae5247235586370";//暴雪英雄榜

        // 调用接口获取access_token
        AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);

        if (null != at) {
            // 调用接口创建菜单
            int result = WeixinUtil.createMenu(getMenu(), at.getToken());

            // 判断菜单创建结果
            if (0 == result)
                log.info("菜单创建成功！");
            else
                log.info("菜单创建失败，错误码：" + result);
        }
    }

    /**
     * 组装菜单数据
     *
     * @return
     */
    private static Menu getMenu() {
        CommonButton btn11 = new CommonButton();
        btn11.setName("来比惨");
        btn11.setType("click");
        btn11.setKey("bican");

        CommonButton btn21 = new CommonButton();
        btn21.setName("看笑话");
        btn21.setType("click");
        btn21.setKey("jok");

        CommonButton btn31 = new CommonButton();
        btn31.setName("看个嫩模");
        btn31.setType("click");
        btn31.setKey("beauty");

        CommonButton btn32 = new CommonButton();
        btn32.setName("抽个幸运签");
        btn32.setType("click");
        btn32.setKey("lucky");

        CommonButton btn33 = new CommonButton();
        btn32.setName("相亲宝典");
        btn32.setType("click");
        btn32.setKey("love");

        CommonButton btn34 = new CommonButton();
        btn34.setName("聊聊天");
        btn34.setType("click");
        btn34.setKey("advice");


        CommonButton btn35 = new CommonButton();
        btn34.setName("发红包");
        btn34.setType("click");
        btn34.setKey("money");

        ComplexButton mainBtn3 = new ComplexButton();
        mainBtn3.setName("百宝箱");
        mainBtn3.setSub_button(new CommonButton[]{btn31, btn32, btn33, btn34, btn35});

        /**
         * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br>
         *
         * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
         * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br>
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
         */
        Menu menu = new Menu();
        menu.setButton(new Button[]{btn11, btn21, mainBtn3});

        return menu;
    }
}
