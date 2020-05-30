hadoop fs -mkdir /cascadesvm \
&& hadoop fs -mkdir /cascadesvm/input \
&& hadoop fs -mkdir /user \
&& hadoop fs -mkdir /user/hpds \
&& hadoop fs -put ./files/a1a.t /cascadesvm/input \
&& hadoop fs -put ./files/libsvm.jar /user/hpds
