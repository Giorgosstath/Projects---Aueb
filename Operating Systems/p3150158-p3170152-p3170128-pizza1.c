#include <pthread.h>
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include "p3150158-p3170152-p3170128-pizza1.h"

int seed, Ncust;
int *all_times;
int avCooks = Ncook;
int avOvens = Noven;
pthread_mutex_t lock,lock1,lock2;
pthread_cond_t condition1,condition2;

void *makeOrder(void *thread_id){
	int *t_id = (int *) thread_id;
	int rc;
	double overall;	 // the overall time for the order's preparation
	struct timespec start, stop; 	// the start/ stop of the order's preparation

	int Npizza = rand() % Norderhigh + Norderlow; //the numbers of pizzas
	
	printf("We took order %d.\n",*t_id);
	
	if( clock_gettime( CLOCK_REALTIME, &start) == -1 ) {
		perror( "clock gettime" );
		exit( EXIT_FAILURE );
	} //the time of the order's arrival
	
	rc = pthread_mutex_lock(&lock);
			
	while(avCooks == 0){
		printf("The order %d is waiting for an available cook!\n", *t_id);
		rc= pthread_cond_wait(&condition1, &lock);
	}// while there are no cooks available => wait

	avCooks--; 	//occupy cook
	rc = pthread_mutex_unlock(&lock);

	sleep(Tprep*Npizza);		//time required for the pizzas' preparation

	rc = pthread_mutex_lock(&lock1);
	while(avOvens == 0){
		printf("The order %d waiting for an available oven!\n", *t_id);
		rc= pthread_cond_wait(&condition2, &lock1);
	}// while there are no ovens available => wait
				
	printf("The pizzas of order %d are being prepared!\n", *t_id);
	avOvens--;	 //occupy oven
	rc = pthread_mutex_unlock(&lock1);

	sleep(Tbake);	 //time required for the pizzas' baking

	if( clock_gettime( CLOCK_REALTIME, &stop) == -1 ) {
		perror( "clock gettime" );
		exit( EXIT_FAILURE );
	}//the time of the order's finish

	rc= pthread_mutex_lock(&lock2);
				
	overall = (stop.tv_sec-start.tv_sec)+(stop.tv_nsec-start.tv_nsec)/1000000000L;
	all_times[*t_id] = overall;
	printf("The order %d is ready! It took %lf minutes!\n", *t_id, overall);
	avCooks++;
	rc= pthread_cond_signal(&condition1);
	avOvens++;
	rc= pthread_cond_signal(&condition2);
	rc = pthread_mutex_unlock(&lock2);
	return NULL;
}

void compareOverall(){
	double average=0, sum=0,max_time=0;
	int max_order;
	
	for(int i=1; i<Ncust+1; i++){								
		if(all_times[i] > max_time){
			max_time = all_times[i];
			max_order = i;
		}
		sum += all_times[i];
		//printf("The order %d took %d minutes!\n", i, all_times[i]);
	}
	average = (double)sum / Ncust;
	printf("The average time of the orders' preparation is %lf.\nOrder %d took the longest with %lf minutes to prepare.\n", average, max_order, max_time);
}

int main(int argc, char *argv[]) {

	//arguement check
	if(argc != 3){
		printf("ERROR: the program should take two arguments, the number of customers (threads) to create and the seed for rand method!\n");
		exit(-1);
	}
	
	Ncust = atoi(argv[1]);
	seed = atoi(argv[2]);
	srand(seed);

	if(Ncust <= 0){	
		printf("ERROR: the program should take numbers for customers greater than zero!\n");
		exit(-1);
	}//end arg_check

	int rc;
	pthread_t *threads; //thread array
	threads = malloc(Ncust * sizeof(pthread_t));
	if (threads == NULL) {
		printf("NOT ENOUGH MEMORY!\n");
		return -1;
	}//memory check

	all_times = malloc(Ncust * sizeof(int)); //orders' overall time array
	if (all_times == NULL) {
		printf("NOT ENOUGH MEMORY!\n");
		return -1;
	}//memory check
	
	rc = pthread_mutex_init(&lock, NULL);
	if (rc != 0) {
		printf("ERROR: return code from pthread_mutex_init() is %d\n", rc);
		exit(-1);
       	}

	rc = pthread_mutex_init(&lock1, NULL);
	if (rc != 0) {
		printf("ERROR: return code from pthread_mutex_init() is %d\n", rc);
		exit(-1);
       	}

	rc = pthread_mutex_init(&lock2, NULL);
	if (rc != 0) {
		printf("ERROR: return code from pthread_mutex_init() is %d\n", rc);
		exit(-1);
       	}

	rc = pthread_cond_init(&condition1, NULL);
	if (rc != 0) {
		printf("ERROR: return code from pthread_cond_init() is %d\n", rc);
		exit(-1);
       	}

	rc = pthread_cond_init(&condition2, NULL);
	if (rc != 0) {
		printf("ERROR: return code from pthread_cond_init() is %d\n", rc);
		exit(-1);
       	}

	int t_id,wait_time;
	int countArray[Ncust];
   	for(t_id = 0; t_id < Ncust; t_id++) {
		wait_time = rand() % Torderhigh + Torderlow;
		countArray[t_id] = t_id + 1;
    	rc = pthread_create(&threads[t_id], NULL, makeOrder, &countArray[t_id]);
		sleep(wait_time);
    		if (rc != 0) {
    			printf("ERROR: return code from pthread_create() is %d\n", rc);
       			exit(-1);
       		}
    	}//thread creation

	for (t_id = 0; t_id < Ncust; t_id++) {
		rc = pthread_join(threads[t_id], NULL);
		
		if (rc != 0) {
			printf("ERROR: return code from pthread_join() is %d\n", rc);
			exit(-1);		
		}
	}//thread join

	rc = pthread_mutex_destroy(&lock);
	if (rc != 0) {
		printf("ERROR: return code from pthread_mutex_destroy() is %d\n", rc);
		exit(-1);
       	}

	rc = pthread_mutex_destroy(&lock1);
	if (rc != 0) {
		printf("ERROR: return code from pthread_mutex_destroy() is %d\n", rc);
		exit(-1);
       	}

	rc = pthread_mutex_destroy(&lock2);
	if (rc != 0) {
		printf("ERROR: return code from pthread_mutex_destroy() is %d\n", rc);
		exit(-1);
       	}

	rc = pthread_cond_destroy(&condition1); 
	if (rc != 0) {
		printf("ERROR: return code from pthread_cond_destroy() is %d\n", rc);
		exit(-1);
       	}

	rc = pthread_cond_destroy(&condition2); 
	if (rc != 0) {
		printf("ERROR: return code from pthread_cond_destroy() is %d\n", rc);
		exit(-1);
       	}
	
	compareOverall();
	free(threads);
	free(all_times);
	return 1;
}//telos
