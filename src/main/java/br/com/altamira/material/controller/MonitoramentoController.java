package br.com.altamira.material.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import br.com.altamira.material.model.MaquinaLog;
import br.com.altamira.material.repository.MaquinaLogRepository;
import br.com.altamira.material.repository.MaquinaRepository;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class MonitoramentoController {

	@Autowired
	private MaquinaRepository maquinaRepository;
	
	@Autowired
	private MaquinaLogRepository maquinaLogRepository;
	
	@Autowired
	@Qualifier("WebSocketHandler") 
	private SendingTextWebSocketHandler sendingTextWebSocketHandler;
	
	@Transactional
	@JmsListener(destination = "IHM-STATUS")
	public void monitoramentoStatus(String msg) throws JsonParseException, JsonMappingException, IOException {
		System.out.println(String.format("CHEGOU MENSAGEM DE IHM-STATUS: %s", msg));
		
		ObjectMapper mapper = new ObjectMapper();
		
		@SuppressWarnings("unchecked")
		Map<String, Object> mensagem = mapper.readValue(msg, Map.class);
		
		@SuppressWarnings("unchecked")
		Map<String, Object> parametros = mapper.convertValue(mensagem.get("parametros"), Map.class);
		
		@SuppressWarnings("unchecked")
		Map<String, Object> status = mapper.convertValue(mensagem.get("status"), Map.class);
		
		String maquina = parametros.get("maquina").toString().toUpperCase();
		
		int modo = Integer.parseInt(mensagem.get("modo").toString());
		
		String usuario = mensagem.get("usuario") == null ? "" : mensagem.get("usuario").toString();
		
		Integer[] torque = mapper.convertValue(status.get("torque"), Integer[].class);
		
		Integer[] corrente = mapper.convertValue(status.get("corrente"), Integer[].class);
		
		Integer[] temperatura = mapper.convertValue(status.get("temperatura"), Integer[].class);
		
		int uptime = Integer.parseInt(status.get("uptime").toString());
		
		MaquinaLog log = new MaquinaLog(
				maquina,
				modo,
				usuario,
				torque[0],
				torque[0],
				corrente[0],
				corrente[1],
				temperatura[0],
				temperatura[1],
				uptime);
		
		maquinaLogRepository.saveAndFlush(log);
		
		String approximateFirstReceiveTimestamp = String.valueOf(new Date().getTime());
		
        try {
            this.sendingTextWebSocketHandler.broadcastToSessions(new DataWithTimestamp<MaquinaLog>(log, approximateFirstReceiveTimestamp));
        } catch (IOException e) {
        	System.out.println(String.format("Was not able to push the message to the client. %s", e));
        }
		
	}
	
}
