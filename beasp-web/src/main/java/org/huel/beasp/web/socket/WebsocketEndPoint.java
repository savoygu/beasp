package org.huel.beasp.web.socket;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@RequestMapping("websocket")
public class WebsocketEndPoint extends TextWebSocketHandler{

	@Override
	protected void handleTextMessage(WebSocketSession session,
			TextMessage message) throws Exception {
		super.handleTextMessage(session, message);
		TextMessage textMessage = new TextMessage(message.getPayload()+" received at server");
		session.sendMessage(textMessage);
	}
	
}
