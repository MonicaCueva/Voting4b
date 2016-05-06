package es.uniovi.asw.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_colegio {

	
	public void rellenaFormulario(WebDriver driver, String codigo, String circunscripcion, String comunidad)
    {
 		WebElement codigoWeb = driver.findElement(By.id("codigoColegio"));
 		codigoWeb.click();
 		codigoWeb.clear();
 		codigoWeb.sendKeys(codigo);
 		
 		WebElement circunscripcionWeb = driver.findElement(By.id("circunscripcion"));
 		circunscripcionWeb.click();
 		circunscripcionWeb.clear();
 		circunscripcionWeb.sendKeys(circunscripcion);
 		
 		WebElement comunidadWeb = driver.findElement(By.id("comunidadAutonoma"));
 		comunidadWeb.click();
 		comunidadWeb.clear();
 		comunidadWeb.sendKeys(comunidad);
 		

 		//Pulsar el boton de Añadir.
 		By boton = By.className("boton_colegio");
 		driver.findElement(boton).click();	   
    }
}
