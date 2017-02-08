

#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <time.h>
#include <stdint.h>
#include <sys/time.h>

#define OK 1
#define NOK 0
#define NELM 100		/* default 100 elements */
#define N 1048576		/* 2^30 or 1 meg elements  */
//#define N 2097152
//#define N 4194304
//#define N 8388608
//#define N 16777216
//#define N 33554432

void selection_sort();
void swap();

void merge_sort();
void msort_recursive();

void radix_sort();
void float_radix_sort();
void swap();
void self_check();

int rand(void);
void srand();
int rand_r();
void init_lst();
void print_lst();

int n, lst[N],tmp[N];

int main(int argc,char **argv) {

  long int del_sec,del_msec;
  struct timeval tv_s,tv_e;

  if (argc>1) n = atoi(argv[1]);
  else n = NELM;
  printf("n=%d\n",n);
  init_lst(lst,n);
   // print_lst(lst,n);

  gettimeofday(&tv_s, NULL); 
  //selection_sort(lst,n);
  //  merge_sort(lst,tmp,n);

  radix_sort(lst,tmp,n);
  //  float_radix_sort(lst,tmp,n);
  gettimeofday(&tv_e, NULL); 

  /****
    PRINT elapsed time in sec and milli secs
  ****/

    //print_lst(lst,n);
  self_check(lst, n);
  return 0;
}

void selection_sort(int list[],int n){
  // fill here
}

void merge_sort(int list[], int temp[], int n){
  msort_recursive(list, temp, 0, n-1);
}

//use recursion
void msort_recursive(int list[], int temp[], int left, int right){
  // fill here
}

//fix the bucket size to 256. run 4 passes where each pass processes 8 bits
//use lst and tmp only. do not use any more memory of size n.
void radix_sort(int * lst, int * tmp, int len) {
  printf("%s\n", "radix sort");
  int group=8;			/* 8 bits per pass (or round) */
  int bucket = 1 << group;
  
  int cnt[bucket];
  int map[bucket];
  int t, sum=0;
  int flag=0;
  for(int pass=0; pass < 32; pass+=8){
    int mask=0xff;
    mask=mask<<pass;

  for(int i =0; i < bucket; i++){
    cnt[i]=0;
    map[i]=0;
  }

  for(int i=0; i < len; i++){
    t=(lst[i] & mask) >> pass;
    cnt[t]++;
  }
  for(int i =1; i < bucket; i++){
    map[i]=sum;
    sum=cnt[i]+sum;
  }
  for(int i=0; i < len; i++){
    tmp[map[((lst[i]&mask)>>pass)]++]= lst[i];

  }
  
  for(int i=0; i < len; i++){
      lst[i]=tmp[i];
      tmp[i]=0;
  }

}
  /* number of buckets = 256 */
  // fill here
}

void print_lst(int *l,int n){
  int i;
  for (i=0; i<n; i++) {
    printf("%d ",l[i]);
  }
  printf("\n");
}

void init_lst(int *l,int n){
  int i;
  //  int seed = time(0) % 100;	/* seconds since 1/1/1970 */
  //  printf ("seed=%d\n", seed);
  srand(1234);			/* SEED */
  for (i=0; i<n; i++) {
    l[i] = rand();
  }
}

void self_check(int *list,int n) {
  int i,j,flag=OK,*listp;

  listp = list;
  for (i=0;i<n-1;i++)
     if (listp[i] > listp[i+1]) { flag = NOK; break; }

  if (flag == OK) printf("sorted\n");
  else printf("NOT sorted at %d\n",i);
}