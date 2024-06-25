package com.eric.service.impl;

import com.eric.repository.IChatDao;
import com.eric.repository.entity.ChatEntity;
import com.eric.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Primary // 解决 expected single matching bean but found 2: accountServiceImpl,accountService
@Service
public class ChatServiceImpl implements ChatService {
    private static final Logger logger = LoggerFactory.getLogger(ChatServiceImpl.class);
    @Resource
    private IChatDao mCharDao;


    /**
     * http://localhost:8089/chat/query/history?userId=123
     * @param userId
     * @return
     */
    @Override
    public ArrayList<ChatEntity> queryHistoryChat(Long userId) {
        ArrayList<ChatEntity> res = new ArrayList<>();
        // 发送的消息
        String curUserTable = userId + "_chat";
        List<Map> maps = mCharDao.listTable();
        for (Map map : maps) {
            String tableName = (String) map.get("TABLE_NAME");
            if (tableName.endsWith("_chat") && !tableName.equals(curUserTable)) {
                res.addAll(mCharDao.queryChatByToId(tableName, userId));
            } else if (curUserTable.equals(tableName)) {
                res.addAll(mCharDao.queryChatByTableName(curUserTable));
            }
        }
        return res;
    }

    /**
     * http://localhost:8089/chat/query/history?userId=123
     */
    @Override
    public ArrayList<ChatEntity> querySingleChat(Long fromId, Long toId) {
        ArrayList<ChatEntity> res = new ArrayList<>();
        // 发送的消息
        List<Map> maps = mCharDao.listTable();
        for (Map map : maps) {
            String tableName = (String) map.get("TABLE_NAME");
            if (tableName.equals(fromId + "_chat")) {
                res.addAll(mCharDao.queryChatByToId(tableName,toId));
            } else if (tableName.equals(toId + "_chat")) {
                res.addAll(mCharDao.queryChatByToId(tableName, fromId));
            }
        }
        return res;
    }
}
