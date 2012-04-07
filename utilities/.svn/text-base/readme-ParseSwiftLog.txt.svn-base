cat dc-4000.log | grep "JOB_SUBMISSION" | grep "TaskImpl" | grep "Active" > dc-4000-active-completed.txt
cat dc-4000.log | grep "JOB_SUBMISSION" | grep "TaskImpl" | grep "Completed" >> dc-4000-active-completed.txt
java ParseSwiftLog2 dc-4000-active-completed.txt > falkon_task_perf.txt
java NormalizeTaskPerf falkon_task_perf.txt > falkon_task_perf_normalized.txt
java ConvertPerTaskToSummary falkon_task_perf_normalized.txt 1 > falkon_summary.txt
java CompareRuns falkon_task_perf.txt falkon_task_perf.txt 131077

cat dc-6000.log | grep "JOB_SUBMISSION" | grep "TaskImpl" | grep "Active" > dc-6000-active-completed.txt
cat dc-6000.log | grep "JOB_SUBMISSION" | grep "TaskImpl" | grep "Completed" >> dc-6000-active-completed.txt
java ParseSwiftLog2 dc-6000-active-completed.txt > falkon_task_perf.txt
java NormalizeTaskPerf falkon_task_perf.txt > falkon_task_perf_normalized.txt
java ConvertPerTaskToSummary falkon_task_perf_normalized.txt 1 > falkon_summary.txt
java CompareRuns falkon_task_perf.txt falkon_task_perf.txt 131077


