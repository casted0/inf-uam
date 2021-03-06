; GWNPG8M4ZS
; Monoblos
(defun mi-f-ev (estado) 
  
  (if (juego-terminado-p estado)
      (if (< (get-pts (estado-lado-sgte-jugador estado)) (get-pts (lado-contrario (estado-lado-sgte-jugador estado))))
          -50
          50
          )
    (- (+(get-pts (estado-lado-sgte-jugador estado))
          (* 1 (get-fichas (estado-tablero estado) (estado-lado-sgte-jugador estado) 6) )
          (* (estado-lado-sgte-jugador estado)
           (f-ev-hu (posiciones-con-fichas-lado (estado-tablero estado) (estado-lado-sgte-jugador estado) 0) estado) )
          )
     (+(get-pts (lado-contrario (estado-lado-sgte-jugador estado))) 
        (* 1 (get-fichas (estado-tablero estado) (lado-contrario (estado-lado-sgte-jugador estado)) 6) )
        (* (estado-lado-sgte-jugador estado)
           (f-ev-hu (posiciones-con-fichas-lado (estado-tablero estado) (lado-contrario(estado-lado-sgte-jugador estado)) 0) estado))))))








(defun suma-lista (l)
  (if (endp l)
      0
    (+ (first l)
       (suma-lista (rest l)))))


(defun f-ev-hu (lst estado)
  
  (suma-lista (mapcar #'(lambda (x) 
                          (if (< (+ x ( get-fichas  (estado-tablero estado)   (estado-lado-sgte-jugador estado) x )) 6   )
                              (if(find (+ x ( get-fichas  (estado-tablero estado)   (estado-lado-sgte-jugador estado) x )) lst)
                                0
                                (get-fichas (estado-tablero estado)   (lado-contrario (estado-lado-sgte-jugador estado)) x )
                                )
                              0)
                          ) lst
              )))

    

