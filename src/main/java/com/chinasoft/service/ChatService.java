package com.chinasoft.service;


import com.chinasoft.repository.entity.ChatEntity;
import com.chinasoft.repository.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

public interface ChatService {

    ArrayList<ChatEntity> queryHistoryChat(Long userId);
    ArrayList<ChatEntity> querySingleChat(Long fromId, Long toId);




}
