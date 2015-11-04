package br.com.altamira.material.controller;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import br.com.altamira.material.model.MaquinaLog;
import br.com.altamira.material.msg.MonitoramentoMsg;
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
		System.out.println(String.format(
				"\n--------------------------------------------------------------------------------\nCHEGOU MENSAGEM DE IHM-STATUS\n--------------------------------------------------------------------------------\n%s\n--------------------------------------------------------------------------------\n", msg));
		
		ObjectMapper mapper = new ObjectMapper();
		MonitoramentoMsg monitoramentoMsg = mapper.readValue(msg, MonitoramentoMsg.class);
		
		MaquinaLog log = new MaquinaLog(
				monitoramentoMsg.getMaquina().toUpperCase(),
				monitoramentoMsg.getDatahora(),
				monitoramentoMsg.getModo(),
				monitoramentoMsg.getTempo(),
				monitoramentoMsg.getOperador()
				);
		
		maquinaLogRepository.saveAndFlush(log);
		
		String approximateFirstReceiveTimestamp = String.valueOf(new Date().getTime());
		
        try {
            this.sendingTextWebSocketHandler.broadcastToSessions(new DataWithTimestamp<MaquinaLog>(log, approximateFirstReceiveTimestamp));
        } catch (IOException e) {
        	System.out.println(String.format("\n********************************************************************************\nWas not able to push the message to the client.\n********************************************************************************\n%s\n********************************************************************************\n", e.getMessage()));
        }
		
	}
	
}