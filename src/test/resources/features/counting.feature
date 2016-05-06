# language: es
 Característica: ShowResults page
   Escenario: Mostrar votos en /showResults
     Cuando creamos unas elecciones y vota la gente
     Y vamos a la pagina de resultados /showResults
     Entonces comprobamos que existe el string "Resultados Elecciones"