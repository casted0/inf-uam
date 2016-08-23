#script.sh
datos="datos.dat"
minsz=1
maxsz=2049
incr=16
for i in $(seq $minsz $incr $maxsz)
do
        printf "$i\t">>datos
        ./slow $i >> datos
        ./fast $i >> datos
	printf "\n" >> datos
 done
