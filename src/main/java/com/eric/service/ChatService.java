package com.eric.service;


import com.eric.repository.entity.ChatEntity;

import java.util.ArrayList;

public interface ChatService {

    ArrayList<ChatEntity> queryHistoryChat(Long userId);
    ArrayList<ChatEntity> querySingleChat(Long fromId, Long toId);




}
