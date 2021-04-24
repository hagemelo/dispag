package br.com.jhage.dispag.pagardebito;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Alexsander Melo
 * @since 06/02/2021
 *
 */

@Component
public class PropertySourceResolver {

	@Value("${example.firstProperty}") private String firstProperty;
    @Value("${example.secondProperty}") private String secondProperty;

    public String getFirstProperty() {
        return firstProperty;
    }

    public String getSecondProperty() {
        return secondProperty;
    }
}
