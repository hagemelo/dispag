package br.com.jhage.dispag.debito;

import br.com.jhage.dispag.debito.service.PusherService;

/**
 * 
 * @author Alexsander Melo
 * @since 10/10/2021
 *
 */
public class kafkaPusherImp implements PusherService{

	
	private Boolean executouPosh;
	
	public static PusherService newInstance() {
		
		return new kafkaPusherImp();
	}
	
	public kafkaPusherImp() {
		
		
		this.executouPosh = false;
	}
	
	public Boolean executouPosh() {
		
		return this.executouPosh;
	}
	@Override
	public Boolean push(String topic, String payload){

		System.out.println("==> Execução kafkaPusherImp.");
		this.executouPosh = true;
		return this.executouPosh;
	}

}
