package es.uniovi.asw.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_voto {
	
	public void rellenaFormulario(WebDriver driver, String voto)
    {
 		WebElement votoWeb = driver.findElement(By.id("opcion"));
 		votoWeb.click();
 		votoWeb.clear();
 		votoWeb.sendKeys(voto);
 		

 		//Pulsar el boton de Votar.
 		By boton = By.className("boton_votar");
 		driver.findElement(boton).click();	   
    }

}
