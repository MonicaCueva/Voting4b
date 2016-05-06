package es.uniovi.asw.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_login {

	
	public void rellenaFormulario(WebDriver driver, String email, String clave)
    {
 		WebElement emailWeb = driver.findElement(By.id("field1"));
 		emailWeb.click();
 		emailWeb.clear();
 		emailWeb.sendKeys(email);
 		
 		WebElement claveWeb = driver.findElement(By.id("field2"));
 		claveWeb.click();
 		claveWeb.clear();
 		claveWeb.sendKeys(clave);
 	

 		//Pulsar el boton de login.
 		By boton = By.className("boton_login");
 		driver.findElement(boton).click();	   
    }
}
