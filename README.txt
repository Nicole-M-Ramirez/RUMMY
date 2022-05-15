Nombre: Nicole M. Ramirez Mulero
Clase: CCOM 4029
Profesora: Patricia Ordones
Grupo: 4

***PARA CORRER USE COMANDO 'java Main'***

FUNSION STACK:
-Implementcion de stack para que funcione acorde a stack and Table.
-Funciones en stack:
    *addCart = Añade una carta al stack
    *removeCart = remueve una carta del stack
    *peek = deja ver la carta en la posision top
    *isEmpty = retorna true si el stack esta vacio, false de lo contrario
    *isFull = retorna true si el stack esta lleno, false de lo contrario
    *lenght = retorna el tamaño actual del stack

CAMBIOS HECHOS EN TRABLE:
-Se incluyo una funcion ComputerPlay() y virtualPlayer() para que el jugo sea capaz de ser jugado por la computadora

-p1Hand y p2Hand se cambiaron de 'DefaultLisTModel' a 'Hand' para poder trabajar la organizacion de las cartas.

-Se implemento el stack en Table, cambiando stackDeck para que refrejara mi implementacion de stack.

-Se crearon las funciones FinishTurn, deckButton, stackButton, layButton y layOnStackButton para organizar los 'if' en la funcion
'actionLisener'

-Funsion FinishTurn:
    *Primero se añadio un boton al GUI llamado 'FinishTurn' para cada jugador.
    *se creo la variable 'cardAddedToHand' de tipo 'Object' para guardar la carta que  el jugador añadio a su mano en la jugada
    *Se creo un arreglo 'DiscartedCards' y una varible 'DiscardCounter' para guardar una copia de las cartas que el jugador descarto
     de su mano en la jugada.
    *Si se añadio una carta a la mano, una copia de esta se guarda en la varible 'cardAddedToHand'.
    *Si se descarto una carta de la mano, una copia de esta su guarda en el arreglo 'DiscartedCards' y se aumenta +1 a la variable 
     'DiscardCounter' para cuando se despliegue la jugada.
    *Cuando se hace click en el boton en el GUI, se imprime en la pantalla y se reinician las variables 'cardAddedToHand', 'DiscartedCards'
     y 'DiscardCounter'.

-Cuando alguna de las manos de los jugadores se quedo sin cartas, o el deck fue acabado, al imprimir el boton de 'FinishTurn' se activa
la funcion 'endGame', que a su vez activa la funcion 'countPoint'

-Funsion endGame:
    *Primero calcula los puntos de las cartas de cada jugador usando la funsion 'countPoint'.
    *Luego calcula quien gano y despliega el mensaje


CAMBIOS HECHOS EN DECK:
-Removi el for loop en constructor Deck porque sino se crea el doble de cartas para un deck en 'Table'

CAMBIOS EN HAND:
-En cuento al sort de las cartas devo de darle el credito a Francisco Melendez quien me ayudo a comprender como hacer el sort i dio la idea de
usar el algoritmo de selection sort para lograrlo.