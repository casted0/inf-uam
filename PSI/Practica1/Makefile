APPLICATION=cuatroenrayaServer
GRUPO=1311
PAREJA=05
# 1, 2 o 3
MIEMBRO=1
#In order to fill resource edit file.  WEB-INF/classes/META-INF/persistence.xml
#search for line <jta-data-source>XXXXXXX</jta-data-source>
#XXXXX is the value of RESOURCE
RESOURCE=connectFourServer
#path to glassfish server home
#ASPATH=/home/roberto/GlassFish_Server
ASPATH=/usr/local/glassfish-4.0
#Normalmente no se requieren cambios despues de esta linea
BASE=$(GRUPO)_$(PAREJA)
BASEFILE=$(BASE).sql
#postgres commands
export PGUSER=alumnodb
export PGHOST=localhost
export PGPASSWORD=alumnodb
export PGBASE=$(BASE)
#glassfish commands
DOMAIN=domain_$(GRUPO)_$(PAREJA)_$(MIEMBRO)
WARFILE=$(APPLICATION).war
ASADMINPATH=$(ASPATH)/glassfish/bin/asadmin
ASERROR=$(ASPATH)/glassfish/domains/$(DOMAIN)/logs/server.log
ASADMIN=$(ASADMINPATH)
help:
	@echo "----------Glassfish-----------"
	@echo "status         : application status"
	@echo "list           : list application in glassfish"
	@echo "stop           : stop glassfish"
	@echo "start          : start glassfish"
	@echo "restart        : restart glassfish"
	@echo "deploy         : deploy application"
	@echo "error          : cat log file"
	@echo "resourcecreate : create pool and jdbc resources"
	@echo "resourcedel    : del pool and jdbc resources"
	@echo "redeploy       : run: undeploy and deploy"
	@echo "undeploy       : undeploy application"
	@echo "stopall        : clean, delete and undeploy everything  (except database)"
	@echo "startall       : start, create and deploy everything (except database)"
	@echo "createdomain   : create a new domain"
	@echo "deletedomain   : create a new domain"
	@echo "----------Postgres-----------"
	@echo "createdb:  create database"
	@echo "dropdb:    delete database"
	@echo "alldb:     execute: dropdb, createdn and restoredb"
	@echo "restoredb: restore data base from file $(BASEFILE). Database must exists"
	@echo "backupdb:  backup databse to file $(BASEFILE)"
	@echo "psql:      execute line client psql for database $(BASEFILE)"
	@echo "----------Project info-----------"	
	@echo "name:     $(APPLICATION)"
	@echo "database: $(BASE)"
	@echo "war file: "
	@ls -lrt  $(WARFILE)
backupdb:
	@pg_dump $(BASE) > $(BASEFILE)
dropdb:
	-dropdb $(BASE)
createdb:
	createdb $(BASE)
restoredb: 
	cat $(BASEFILE) | psql $(BASE)
alldb: dropdb createdb restoredb
psql:
	psql $(BASE)

#Which is the status of the application 
status:
	$(ASADMIN) show-component-status $(APPLICATION)
#List aplication
list:
	$(ASADMIN) list-applications
stop:
	-$(ASADMIN) stop-domain $(DOMAIN)
start:
	$(ASADMIN) start-domain $(DOMAIN) 
	sleep 3
restart: undeploy stop start deploy
deploy:
	@ls -lrt  $(WARFILE)
	$(ASADMIN) deploy --contextroot $(APPLICATION) $(WARFILE) 
	@echo "Application accesible at URL: http://localhost:8080/$(APPLICATION)"
undeploy:
	-$(ASADMIN) undeploy $(APPLICATION) 
redeploy: undeploy deploy
error:
	@cat $(ASERROR)
#delete resources: jdbc and pool
resourcedel:
	-$(ASADMIN) delete-jdbc-resource --user=admin  $(RESOURCE)
	-$(ASADMIN) delete-jdbc-connection-pool --cascade=true $(BASE)2
#create resource poll and jdbc
resourcecreate:
	$(ASADMIN) create-jdbc-connection-pool --datasourceclassname=org.postgresql.ds.PGSimpleDataSource --restype=javax.sql.DataSource --property="User=alumnodb:Password=alumnodb:DatabaseName=$(BASE):Ssl=false:ServerName=localhost" $(BASE)2
	$(ASADMIN) create-jdbc-resource --user=admin  --connectionpoolid $(BASE)2 $(RESOURCE)
#clean stop all
stopall: resourcedel undeploy stop

#clean stop all
startall: start resourcecreate deploy

#create domain
createdomain:
	@echo "WARNING: ACCEPT DEFAULT VALUES (JUST PRESS ENTER)"
	$(ASADMIN)  create-domain $(DOMAIN)
#delete domain
deletedomain:
	$(ASADMIN)  delete-domain $(DOMAIN)
