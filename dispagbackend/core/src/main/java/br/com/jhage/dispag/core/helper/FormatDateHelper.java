package br.com.jhage.dispag.core.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.context.annotation.Configuration;

import br.com.jhage.dispag.core.exception.FormatDateHelperException;

/***
 * 
 * @author Alexsander Melo
 * @since 09/01/2013
 * @version 1.0 Classe responsavel por fomata��o e valida��o de datas
 */
@Configuration
final public class FormatDateHelper {
	
	private FormatDateHelper() {}

	private static final String FORMAT_DEFAULT = "dd/MM/yyyy";
	private static final String FORMAT_DATAHORA = "dd/MM/yyyy hh:mm:ss";
	private static final Locale LOCALE_PT_BR = new Locale("pt", "BR");
	private static final String ERRO_FORMATAR_DATA_PARA_PADRAO = "Erro ao formatar data para o padrão";
	
	private SimpleDateFormat formatoPadraoDaData;
	
	FormatDateHelper(SimpleDateFormat simpleDateFormat) {
		
		formatoPadraoDaData = new SimpleDateFormat(FORMAT_DEFAULT, LOCALE_PT_BR);
	}
	
	public static FormatDateHelper getInstance() {
		
		return new FormatDateHelper();
	}
	
	
	public String converterDataParaCaracter(final Date date) throws FormatDateHelperException {
			
			return converterDataParaCaracter(date, FORMAT_DEFAULT);
	}

	public String converterDataParaCaracter(final Date date, final String value) throws FormatDateHelperException {

		try {
			inserirFormatoPadraoDaData(value);
			return formatoPadraoDaData.format(date).toString();
		} catch (final Exception e) {
			throw new FormatDateHelperException();
		}
	}

	public boolean isMesmadata(final Date date1, final Date date2) throws FormatDateHelperException {

		final Date primeiraData = formatarDataParaPadrao(date1, FORMAT_DATAHORA);
		final Date segundaData = formatarDataParaPadrao(date2, FORMAT_DATAHORA);
		return primeiraData.compareTo(segundaData) == 0;
	}
	
	public boolean isData1MenorQueData2(final Date date1, final Date date2)
			throws FormatDateHelperException {

		final Date primeiraData = formatarDataParaPadrao(date1, FORMAT_DATAHORA);
		final Date segundaData = formatarDataParaPadrao(date2, FORMAT_DATAHORA);
		return primeiraData.compareTo(segundaData) <= 0;
	}

	public Date formatarDataParaPadrao(final String date, final String value) throws FormatDateHelperException {

		try {
			inserirFormatoPadraoDaData(value);
			return formatoPadraoDaData.parse(date);
		} catch (final Exception e) {
			throw new FormatDateHelperException(ERRO_FORMATAR_DATA_PARA_PADRAO);
		}
	}
	
	public Date formatarDataParaPadrao(final Date date, final String value) throws FormatDateHelperException {

		try {
			inserirFormatoPadraoDaData(value);
			return formatoPadraoDaData.parse(formatoPadraoDaData.format(date));
		} catch (final Exception e) {
			throw new FormatDateHelperException(ERRO_FORMATAR_DATA_PARA_PADRAO);
		}
	}

	private void inserirFormatoPadraoDaData(final String formato) {
		
		formatoPadraoDaData = new SimpleDateFormat(formato, LOCALE_PT_BR);
	}
	
}
