package br.ufc.venda.server.server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import br.ufc.venda.model.Mensagem;

public class Despachante {
	
	public String invoke(Mensagem mensagem){
		Class<?> objRef = null;
		Method method = null;
		String resposta = null;
		
		try{
			objRef = Class.forName("br.ufc.venda.server.esqueleto.Esqueleto"+mensagem.getObjectReference());
			String methodName = mensagem.getMethod();
			method = objRef.getMethod(methodName, String.class);
			resposta = (String) (method.invoke(objRef.newInstance(),mensagem.getArguments()));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		
		return resposta;
	}
	
}
