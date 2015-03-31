package com.app.application.weix.service;

import com.app.application.weix.entity.WXinRes;
import com.weixin.utils.Dom4jXmlParse;
import org.dom4j.DocumentException;
import org.dom4j.Node;

/**
 * 获取微信消息
 *
 * @author fuq
 */
public class GetWXinMsg {

    /**
     * 解析微信消息
     * @param reqContent
     * @return
     * @throws DocumentException 
     */
    public static WXinRes psMsg(String reqContent) throws DocumentException {

        Dom4jXmlParse dm4j = new Dom4jXmlParse() {

            @Override
            public Object processNode(Node node) {
                return node;
            }
        };

        WXinRes wxres = new WXinRes();
        Node toUserNameNode = dm4j.readStringXmlReturnNode(reqContent, "/xml/ToUserName");
        Node fromUserNameNode = dm4j.readStringXmlReturnNode(reqContent, "/xml/FromUserName");
        Node createTimeNode = dm4j.readStringXmlReturnNode(reqContent, "/xml/CreateTime");
        Node msgTypeNode = dm4j.readStringXmlReturnNode(reqContent, "/xml/MsgType");
        Node contentNode = dm4j.readStringXmlReturnNode(reqContent, "/xml/Content");
        Node msgIdNode = dm4j.readStringXmlReturnNode(reqContent, "/xml/MsgId");

        String toUserName = toUserNameNode == null ? "" : toUserNameNode.getText();
        String fromUserName = fromUserNameNode == null ? "" : fromUserNameNode.getText();
        String createTime = createTimeNode == null ? "" : createTimeNode.getText();
        String msgType = msgTypeNode == null ? "" : msgTypeNode.getText();
        String content = contentNode == null ? "" : contentNode.getText();
        content = content.replace("＃", "#");
        String msgId = msgIdNode == null ? "" : msgIdNode.getText();
        wxres.setFromUserName(fromUserName);
        wxres.setToUserName(toUserName);
        wxres.setCreateTime(createTime);
        wxres.setContent(content);
        wxres.setMsgType(msgType);
        wxres.setMsgId(msgId);
        if (content.indexOf("#") != -1) {

        }
        return wxres;
    }
}
