package com.uu.uni.chat.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.uu.uni.user.entity.UserEntity;
import com.uu.uni.user.service.UserService;

import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/mainchatting")
@Component
public class MainChatting extends TextWebSocketHandler{
	
	private static List<WebSocketSession> USER = Collections.synchronizedList(new ArrayList<>());
	
	@Autowired
	private UserService userService;
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println(session.toString());
		
		if(USER.contains(session)) {
			System.out.println("이미 접속중인 사용자입니다 : " + session);
		}
		else {
			USER.add(session);
			System.out.println("새로운 사용자가 접속했습니다 : " + session);
		}
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println("메세지를 입력했습니다 : " + session + " : " +  message.getPayload());
		String msg = message.getPayload();
		for(WebSocketSession user : USER) {
			String nn = null;
			String status = null;
			String identifier = " /:/ ";
			if(msg.split(identifier)[0].equals("OPEN")) {
				System.out.println("유저 입장");
				nn = msg.split(identifier)[1];
				status = "OPEN";
				
				user.sendMessage(new TextMessage(status + identifier + nn + "님이 입장하셨습니다."));
			}
			
			else if(msg.split(identifier)[0].equals("SENDMSG")) {
				nn = msg.split(identifier)[1];
				status = "SENDMSG";
				String textmsg = msg.split(identifier)[2];
				UserEntity userEntity = userService.getUserForMainChat(nn).get();
				String img = userEntity.getImg();
				user.sendMessage(new TextMessage(status + identifier + userEntity.getNn() + identifier + textmsg + identifier + img + identifier + userEntity.getIdx()));
			}
			
			else if(msg.split(identifier)[0].equals("CLOSE")) {
				System.out.println("유저 퇴장");
				nn = msg.split(identifier)[1];
				status = "CLOSE";
				
				user.sendMessage(new TextMessage(status + identifier + nn + "님이 퇴장하셨습니다."));
			}
		}
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		USER.remove(session);
		System.out.println("접속 종료 : " + session);
	}

}
