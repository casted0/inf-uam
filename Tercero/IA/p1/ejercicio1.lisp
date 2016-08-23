;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; bfs-improved (end queue net)
;;;	Realiza una busqueda en anchura mejorada para no entrar en recursion infinita.
;;;	
;;;	INPUT:
;;;		end: Nodo objetivo
;;;		queue: Cola con los caminos
;;;		net: Grafo en el que operamos
;;;	OUTPUT:
;;;		Camino al nodo objetivo
;;; 
(defun bfs-improved (end queue net)
	(if (null queue)					;Caso base
			nil
		;;;Se vinculan los valores de path y de node
		(let ((path (car queue)))
			(let ((node (car path)))
				(if (eql node end)		;Caso base
						(reverse path)
					;;;Recursion, se exploran los caminos con 
					;;;origen en el nodo con el que estamos trabajando
					(bfs-improved end
						(append (cdr queue)
							(new-paths-improved path node net))
								net))))))
								
;;;	EJEMPLOS:
;;;		(setf grafo '((a d) (b d f) (c e) (d f) (e b f) (f)))
;;;		(setf grafo2'((a b c d e) (b a d e f) (d a g b h) (e a b g h) (g c d e h) (h f e d g) (c a g) (f b h)))
;;;		(setf grafo3 '((a d) (b d f) (c e) (d f) (e b f) (f d))
;;;
;;;		(bfs-improved 'f (list (list 'a)) grafo) ;-> (A D F)	; caso tipico
;;;		(bfs-improved 'c (list (list 'a)) grafo) ;-> nil	; caso especial
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; new-paths-improved (path node net)
;;;	Realiza una busqueda en anchura mejorada para no entrar en recursion infinita.
;;;	
;;;	INPUT:
;;;		path: Lista actual
;;;		node: Nodo del que sacar caminos
;;;		net: Grafo en el que operamos
;;;	OUTPUT:
;;;		Lista con caminos que salen de node
;;;
(defun new-paths-improved (path node net)
	(if (null (unique-p path))
		nil
	(mapcar #'(lambda(n)
							(cons n path))
		(cdr (assoc node net)))))
		
;;;	EJEMPLOS:
;;;		(new-paths-improved '(A D) 'D grafo) ;-> ((F A D))	; caso tipico
;;;		(new-paths-improved '(A D F D) 'D grafo) ;-> NIL	; caso especial
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; shortest-path-improved (end queue net)
;;;	Realiza una llamada a la funcion de busqueda mejorada.
;;;	
;;;	INPUT:
;;;		end: Nodo objetivo
;;;		queue: Cola con los caminos
;;;		net: Grafo en el que operamos
;;;	OUTPUT:
;;;		Camino al nodo objetivo
;;;
(defun shortest-path-improved (end queue net)
	(bfs-improved end queue net))
	
;;;	EJEMPLOS:
;;;		(shortest-path-improved 'f (list (list 'a)) grafo) ;-> (A D F)	; caso tipico
;;;		(shortest-path-improved 'c (list (list 'a)) grafo) ;-> nil	; caso especial
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; unique-p (list)
;;;	Comprueba si hay elementos repetidos en una lista dada.
;;;	
;;;	INPUT:
;;;		list: Lista sobre la que mirar
;;;	OUTPUT:
;;;		True si hay elementos repetidos o nil si no
;;;
(defun unique-p (list)
	(or (null list)
		(and (not (member (first list) (rest list)))
			(unique-p (rest list))
		)
	)
)

;;;	EJEMPLOS:
;;;		(unique-p '(A D F D)) ;-> T	; caso tipico
;;;		(unique-p '(A B C)) ;-> nil	; caso especial
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
