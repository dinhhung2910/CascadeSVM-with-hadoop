hadoop fs -rm -r -f /cascadesvm/output 
start=`date +%s`
hadoop jar ./CascadeC1Linear.jar TTPT.Cascade.CascadeSvm  /cascadesvm/input /cascadesvm/output 4
end=`date +%s`
runtime=$((end-start))
echo Runtime = $runtime s
rm files/_model_file.model
hadoop fs -get /cascadesvm/output/_model_file.model files/_model_file.model
cd files && java -cp SVMPredict.jar TTPT.BTL.SVMPredict data-d.txt _model_file.model out && cd ..
