#!/usr/bin/env Rscript
#DES application: M/M/1 queue,arrival rate 0.5,servicerate 1.0 


#if (TRUE) {
# dosim (mm1initglbls, mm1reactevnt, mm1prntrslts,10000.0, 
#     list(arrvrate=0.5,srvrate=1.0))
#}

# initializes global variables specific to this app

mm1initglbls <- function(apppars) {
  mm1glbls <<- list()
  
  #simulation parameters
  mm1glbls$arrvrate <<- apppars$arrvrate
  mm1glbls$srvrate  <<- apppars$srvrate

  # server queue, consisting of arrival times of queued job
  mm1glbls$srvq <<- vector(length=0)

  mm1glbls$srvstatus <<- c(0,0,0)
  # statistics 
  mm1glbls$njobsdone <<- 0   # jobs done so far
  mm1glbls$totwait   <<- 0.0 # total wait time so far
	mm1glbls$servicetime <<- 0.0 #tiempo total de servicio
  mm1glbls$avgwaittime <<- 0.0 #tiempo medio de espera
  mm1glbls$varwaittime <<- 0.0 #varianza del tiempo de espera
  mm1glbls$queuesize <<- 0     #vector con tamaño de cola

  mm1glbls$tencola <<- 0
  mm1glbls$tensistema <<- 0
  
  # set up first event, an arrival; the application-specifc data for each event 
  # will consist of its arrival time, which we need to record in order to 
  #  calculate the job's residence time in the system  

  arrvtime <- rpois(1, mm1glbls$arrvrate)
  schedevnt(arrvtime,"arrv",list(arrvtime=arrvtime))
}

mm1reactevnt <- function(head) {
 # print(mm1glbls$srvstatus)
  mm1glbls$queuesize <<- c(mm1glbls$queuesize, length(mm1glbls$srvq))
  if (head$evnttype == "arrv") {  #Arrival
    #Obtener servidor disponible
    srvindex <- which.min(mm1glbls$srvstatus + runif(3,0,1))

    if(mm1glbls$srvstatus[srvindex] < 1) {
      #If server free, start service (generar exponencial, schedule event con tiempo de la exponencial)
      #Indicar como ocupado
      mm1glbls$srvstatus[srvindex] <<- 1
      mm1glbls$srvq <<- head$arrvtime
      
      #Tiempo en cola 0
      mm1glbls$tencola <<- c(mm1glbls$tencola, 0)

      x <- rexp(1, mm1glbls$srvrate)
      mm1glbls$servicetime <<- mm1glbls$servicetime + x

      #Tiempo en el sistema del cliente 
      mm1glbls$tensistema <<- c(mm1glbls$tensistema, x)

      srvdonetime <- sim$currtime + x
      schedevnt(srvdonetime, "srvdone", list(arrvtime=head$arrvtime, srvindex=srvindex))

    } else mm1glbls$srvq <<- c(mm1glbls$srvq, head$arrvtime)

    arrvtime <- sim$currtime + rexp(1, mm1glbls$arrvrate)
  #  print(arrvtime)
    schedevnt(arrvtime, "arrv", list(arrvtime=arrvtime, srvindex=-1))

  } else {#Service done


    mm1glbls$njobsdone <<- mm1glbls$njobsdone + 1
    mm1glbls$totwait <<- mm1glbls$totwait + sim$currtime - head$arrvtime

    

    #Tiempo Medio espera
    mm1glbls$avgwaittime <<- mediaN1(mm1glbls$avgwaittime, sim$currtime - head$arrvtime, mm1glbls$njobsdone)
    mm1glbls$varwaittime <<- varN1(mm1glbls$varwaittime, mm1glbls$avgwaittime, sim$currtime - head$arrvtime, mm1glbls$njobsdone)
    
    #Remove from queue
    mm1glbls$srvq <<- mm1glbls$srvq[-1]
    #Liberar el servidor usado
    mm1glbls$srvstatus[head$srvindex] <<- 0
    #More still in queue?
    
    if (length(mm1glbls$srvq) > 0) {
      
      #Obtener servidor disponible
      srvindex <- which.min(mm1glbls$srvstatus + runif(3,0,1))
	  	if(mm1glbls$srvstatus[srvindex] < 1) {
        mm1glbls$srvstatus[srvindex] <<- 1
        x <- rexp(1, mm1glbls$srvrate)
  			mm1glbls$servicetime <<- mm1glbls$servicetime + x
        mm1glbls$tensistema <<- c(mm1glbls$tensistema, x)
        #Tiempo en cola
        mm1glbls$tencola <<- c(mm1glbls$tencola, sim$currtime - head$arrvtime)
        srvdonetime <- sim$currtime + x

        schedevnt(srvdonetime, "srvdone", list(arrvtime=mm1glbls$srvq[1], srvindex=srvindex))
      }
    }

  }
}
mm1prntrslts <- function() {
  #print(mm1glbls)
  print("Numero de clientes:")
  print(mm1glbls$njobsdone)
  print("Tiempo medio de espera:")
  print(mm1glbls$avgwaittime)
  print("Varianza del tiempo de espera:")
  print(mm1glbls$varwaittime)
	print("Tiempo total servidor inactivo:")
	print(timesim - mm1glbls$servicetime)
  print("Fraccion del tiempo inactivo frente al total:")
	print(1- mm1glbls$servicetime / timesim)
  print("Tiempo de servicio por cliente")
  print(mm1glbls$servicetime / mm1glbls$njobsdone)
	
}

#################### Ejemplo PROGRAMA PRINCIPAL  
#
# Parametros: arrvrate = Lambda = 1/4  (Poisson)
#             srvrate  = Mu     = 1/3     (Exp)
#             Ro = lambda / 3 * mu = 0.25
if (TRUE) {
  #timesim <<- 8*60*60
  timesim <<- 10000
  source("rurtinasRsimulacion.r")  # cargo el script de la libreria DES
  source("mediavarianza.r")
  # direct output to a file, output also send to terminal. 
  sink("kk.txt", append=FALSE, split=TRUE)

  # Run the simulation: 
  # DES application: 
     # M/M/1 queue, arrival rate 0.5, servicerate 1.0, maxsimtime=100  
  #dosim (mm1initglbls, mm1reactevnt, mm1prntrslts, maxsimtime=timesim, 
  #   list(arrvrate=15,srvrate=60/3), dbg=FALSE)
  dosim (mm1initglbls, mm1reactevnt, mm1prntrslts, maxsimtime=timesim, 
     list(arrvrate=1/4,srvrate=1/3), dbg=FALSE)
  
  #dosim (mm1initglbls, mm1reactevnt, mm1prntrslts, maxsimtime=timesim, 
  #   list(arrvrate=.5,srvrate=1), dbg=FALSE)
  
  #dosim (mm1initglbls, mm1reactevnt, mm1prntrslts, maxsimtime=100.0, 
  #   list(arrvrate=1,srvrate=0.5), dbg=TRUE)
  # return output to the terminal
  plot(mm1glbls$queuesize[0:2500], type='h')
  sink()
}



