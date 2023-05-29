package com.uu.uni.user.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.uu.uni.user.entity.FriendReqResEntity;
import com.uu.uni.user.entity.UserEntity;
import com.uu.uni.user.service.FriendReqResService;
import com.uu.uni.user.service.UserService;

import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/reqres")
@Component
public class FriendReqRes extends TextWebSocketHandler{
	
	private FriendReqResService friendReqResService;
	private UserService userService;
	
	@Autowired
	public FriendReqRes(FriendReqResService friendReqResService, UserService userService) {
		this.friendReqResService = friendReqResService;
		this.userService = userService;
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("[FriendReqRes] 입장 : " + session);
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {		
		String msg = message.getPayload();
		System.out.println(msg);
		String identifier = " /:/ ";
		if(msg.split(identifier)[0].equals("OPEN")) {
			int idx = Integer.parseInt(msg.split(identifier)[1]);
			List<Optional<FriendReqResEntity>> req = friendReqResService.getReq(idx);
			
			if(req.size() == 1) {
				UserEntity res = userService.getUser(req.get(0).get().getFrom()).get();
				String nn = res.getNn();
				String img = res.getImg();				
				session.sendMessage(new TextMessage("REQUEST" + identifier + nn + identifier + img));
			}
			
			if(req.size() > 1) {				
				String send_req_list = "";
				for(int i=0; i<req.size(); i++) {
					UserEntity res = userService.getUser(req.get(i).get().getFrom()).get();
					String nn = res.getNn();
					String img = res.getImg();
					send_req_list += "REQUEST" + identifier + nn + identifier + img;
					if(i < req.size()-1) send_req_list += " ///// ";
				}
				session.sendMessage(new TextMessage(send_req_list));;
			}
			else session.close();
		}
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("[FriendReqRes] 종료 : " + session);
	}

}
