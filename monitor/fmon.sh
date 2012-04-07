#! /bin/sh

tail -f *summ* | awk '
{
printf("\n");
printf("Falkon_Summary_Log_Summary\n");
printf("Time_ms =           " $1 "\n");
printf("num_users =         " $2 "\n");
printf("num_resources =     " $3 "\n");
printf("num_threads =       " $4 "\n");
printf("num_all_workers =   " $5 "\n");
printf("num_free_workers =  " $6 "\n");
printf("num_pend_workers =  " $7 "\n");
printf("num_busy_workers =  " $8 "\n");
printf("waitQ_length =      " $9 "\n");
printf("waitNotQ_length =   " $10 "\n");
printf("activeQ_length =    " $11 "\n");
printf("doneQ_length =      " $12 "\n");
printf("delivered_tasks =   " $13 "\n");
printf("throughput_tasks/s  " $14 "\n");
printf("success_tasks =     " $15 "\n");
printf("failed_tasks =      " $16 "\n");
printf("retried_tasks =     " $17 "\n");
printf("resourceAllocated = " $18 "\n");
printf("submittedTasks =    " $19 "\n");
printf("cacheSize =         " $20 "\n");
printf("cacheLocalHits =    " $21 "\n");
printf("cacheGlobalHits =   " $22 "\n");
printf("cacheMisses =       " $23 "\n");
printf("cacheLocalHitRatio  " $24 "\n");
printf("cacheGlobalHitRatio " $25 "\n");
printf("systemCPUuser =     " $26 "\n");
printf("systemCPUsystem =   " $27 "\n");
printf("systemCPUidle =     " $28 "\n");
printf("systemHeapSize =    " $29 "\n");
printf("systemHeapFree =    " $30 "\n");
printf("systemHeapMax =     " $31 "\n");
}' 
