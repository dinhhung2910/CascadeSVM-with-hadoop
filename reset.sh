stop-all.sh \
&& rm -rf ~/hadoopdata \
&& hadoop namenode -format \
&& hadoop datanode -format \
&& start-all.sh
